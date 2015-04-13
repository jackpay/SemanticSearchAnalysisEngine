package ac.uk.susx.tag.update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.update.AddUpdateCommand;
import org.apache.solr.update.processor.UpdateRequestProcessor;
import org.apache.solr.update.processor.UpdateRequestProcessorFactory;

public class PartialDeleteFieldProcessorFactory extends UpdateRequestProcessorFactory {

	@Override
	public UpdateRequestProcessor getInstance(SolrQueryRequest req, SolrQueryResponse rsp, UpdateRequestProcessor next) {
		PartialDeleteFieldProcessor pdfp = new PartialDeleteFieldProcessor(next);
		pdfp.setQueryResponse(rsp);
		return pdfp;
	}
	
	public class PartialDeleteFieldProcessor extends UpdateRequestProcessor {
		
		  private String solrURL = "http://localhost:10000/solr";
		  private final HashSet<String> allowedFields = new HashSet<String>() {}; // Used as a security measure to prevent unwanted changes.
		  private String unique_key = "id";
		  private SolrQueryResponse rsp;

		public PartialDeleteFieldProcessor(UpdateRequestProcessor next) {
			super(next);
		}
		
	  public PartialDeleteFieldProcessor( UpdateRequestProcessor next, String solrUrl, String keyField ) {
		  super( next );
		  this.unique_key = keyField;
		  this.solrURL = solrUrl;
	  }
		
		@Override
		public void processAdd(AddUpdateCommand cmd) throws IOException {
		
			SolrInputDocument doc = cmd.getSolrInputDocument();
			String id = (String) doc.getFieldValue(unique_key);
			
			SolrInputDocument newDoc = new SolrInputDocument();
			newDoc.addField(unique_key, id);
		      
			HttpSolrServer solr = new HttpSolrServer(solrURL); // Get the document that requires updating.
			SolrQuery query = new SolrQuery(unique_key + ":\"" + id + "\"").setFields("*");
			SolrDocument sd = null;
			try {
				sd = solr.query(query).getResults().get(0);
			} catch (SolrServerException e1) {
				e1.printStackTrace();
			}
			
			for(String field : doc.getFieldNames()) {
				if(!field.equals(unique_key)){
						ArrayList<Object> currField = (ArrayList<Object>) sd.getFieldValue(field);
						currField.remove(doc.getFieldValue(field));
						Map<String,Object> partialupdate = new HashMap<String,Object>();
						if(currField.isEmpty()) {
							partialupdate.put("set", null);
							newDoc.addField(field, partialupdate);
						}
						else {
							partialupdate.put("set", currField);
							newDoc.addField(field, partialupdate);
						}
				}
			}
			
			try {
				solr.add(newDoc);
				solr.commit();
				SolrDocumentList sdl = solr.query(query).getResults();
				rsp.add("response", sdl);
				solr.shutdown();
				rsp.add("global-resp", "The item was removed from the database.");
			} catch (SolrServerException e) {
				rsp.add("global-resp", "The database update failed!");
				e.printStackTrace();
			}
			
		}
		
		public void setQueryResponse(SolrQueryResponse rsp) {
			this.rsp = rsp;
		}
		
	}

}
