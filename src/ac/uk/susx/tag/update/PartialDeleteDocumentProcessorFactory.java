package ac.uk.susx.tag.update;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.update.AddUpdateCommand;
import org.apache.solr.update.processor.UpdateRequestProcessor;
import org.apache.solr.update.processor.UpdateRequestProcessorFactory;

public class PartialDeleteDocumentProcessorFactory extends UpdateRequestProcessorFactory {

	@Override
	public UpdateRequestProcessor getInstance(SolrQueryRequest req, SolrQueryResponse rsp, UpdateRequestProcessor next) {
		PartialDeleteDocumentProcessor pddp = new PartialDeleteDocumentProcessor(next);
		pddp.addResponse(rsp);
		return pddp;
	}
	
	public class PartialDeleteDocumentProcessor extends UpdateRequestProcessor {
		
		private String unique_key = "id";
		private String solrURL = "http://localhost:8983/solr";
		private SolrQueryResponse rsp;

		public PartialDeleteDocumentProcessor(UpdateRequestProcessor next) {
			super(next);
		}
		
		public PartialDeleteDocumentProcessor(UpdateRequestProcessor next, String key, String solrURL) {
			super(next);
			this.unique_key = key;
			this.solrURL = solrURL;
		}
		
		@Override
		  public void processAdd(AddUpdateCommand cmd) throws IOException {
			
			SolrInputDocument doc = cmd.getSolrInputDocument();
		    String id = (String) doc.getFieldValue(unique_key);
		      
		    SolrInputDocument newDoc = new SolrInputDocument();
		    newDoc.addField("id", id);
			newDoc.addField("url", id);
			newDoc.addField("deleted", true);
			
			  HttpSolrServer solr = new HttpSolrServer(solrURL); // Get the document that requires updating.
			  try {
				solr.add(newDoc);
				solr.commit();
				SolrQuery query = new SolrQuery("id:\"" + id + "\"").setFields("*");
				SolrDocumentList sd = solr.query(query).getResults();
				rsp.add("response", sd);
				solr.shutdown();
				rsp.add("updated", "The document has been deleted.");
			} catch (SolrServerException e) {
				rsp.add("updated", "The document could not be deleted.");
				e.printStackTrace();
			}
			
		}
		
		private void addResponse(SolrQueryResponse rsp) {
			this.rsp = rsp;
		}
		
	}

}
