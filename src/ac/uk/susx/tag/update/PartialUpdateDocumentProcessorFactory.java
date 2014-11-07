package ac.uk.susx.tag.update;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.search.SolrReturnFields;
import org.apache.solr.update.AddUpdateCommand;
import org.apache.solr.update.processor.UpdateRequestProcessor;
import org.apache.solr.update.processor.UpdateRequestProcessorFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;

// Retrieves the document from the index and creates a new document with the old fields and updated new fields.
public class PartialUpdateDocumentProcessorFactory extends UpdateRequestProcessorFactory
{
  @Override
  public UpdateRequestProcessor getInstance(SolrQueryRequest req, SolrQueryResponse rsp, UpdateRequestProcessor next)
  {
	PartialUpdateDocumentProcessor pudp = new PartialUpdateDocumentProcessor(next);
	pudp.addResponse(rsp);
    return pudp;
  }
  
  public class PartialUpdateDocumentProcessor extends UpdateRequestProcessor {
	  
	  private String solrURL = "http://localhost:8983/solr";
	  private final HashSet<String> allowedFields = new HashSet<String>() {}; // Used as a security measure to prevent unwanted changes.
	  private String unique_key = "id";
	  private SolrQueryResponse rsp;
	  
	  public PartialUpdateDocumentProcessor( UpdateRequestProcessor next) {
		  super( next );
	  }
	  
	  public PartialUpdateDocumentProcessor( UpdateRequestProcessor next, String solrUrl, String keyField) {
		  super( next );
		  this.unique_key = keyField;
		  this.solrURL = solrUrl;
	  }

	  @Override
	  public void processAdd(AddUpdateCommand cmd) throws IOException {
		  
	      SolrInputDocument doc = cmd.getSolrInputDocument();
	      String id = (String) doc.getFieldValue(unique_key);
	      
	      SolrInputDocument newDoc = new SolrInputDocument();
	      newDoc.addField("id", id);

	      for(String field : doc.getFieldNames()) {
	    	  if(!field.equals("id")) {
	    		  String val = (String) doc.getFieldValue(field);
	    		  if(!val.contains("-- Select --") && !val.equals("") && val != null){
			    	  Map<String,Object> partialupdate = new HashMap<String,Object>();
			    	  if(field.equals("elec-vot-type-country")){
			    		  partialupdate.put("add", doc.getFieldValue(field));
			    	  }
			    	  else{
				    	  partialupdate.put("set", doc.getFieldValue(field));
			    	  }
			    	  newDoc.addField(field, partialupdate);
	    		  }
	    	  }
	      }
	      
		  HttpSolrServer solr = new HttpSolrServer(solrURL); // Get the document that requires updating.
		  try {
			solr.add(newDoc);
			solr.commit();
			SolrQuery query = new SolrQuery("id:\"" + id + "\"").setFields("*");
			SolrDocumentList sd = solr.query(query).getResults();
			rsp.add("response", sd);
			solr.shutdown();
			rsp.add("updated", "The database has been updated.");
		} catch (SolrServerException e) {
			rsp.add("updated", "The database update failed!");
			e.printStackTrace();
		}
    }
	  
	private void addResponse(SolrQueryResponse rsp) {
		this.rsp = rsp;
	}
  }
  
}
