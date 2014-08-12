package ac.uk.susx.tag.update;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.ContentStream;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.update.AddUpdateCommand;
import org.apache.solr.update.processor.UpdateRequestProcessor;
import org.apache.solr.update.processor.UpdateRequestProcessorFactory;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

// Retrieves the document from the index and creates a new document with the old fields and updated new fields.
public class PartialUpdateDocumentProcessorFactory extends UpdateRequestProcessorFactory
{
  @Override
  public UpdateRequestProcessor getInstance(SolrQueryRequest req, SolrQueryResponse rsp, UpdateRequestProcessor next)
  {
    return new PartialUpdateDocumentProcessor(next);
  }
  
  public class PartialUpdateDocumentProcessor extends UpdateRequestProcessor {
	  
	  public static final String solrURL = "http://localhost:8983/solr";
	  public final HashSet<String> allowedFields = new HashSet<String>() {}; // Used as a security measure to prevent unwanted changes.
	  
	  public PartialUpdateDocumentProcessor( UpdateRequestProcessor next) {
		  super( next );
	  }

	  @Override
	  public void processAdd(AddUpdateCommand cmd) throws IOException {
		  Iterator<ContentStream> iter = cmd.getReq().getContentStreams().iterator();
		  while(iter.hasNext()){
			  ContentStream cs = iter.next();
			  System.out.println("CONTENT STREAM: " + cs.getStream().toString());
		  }
		  System.out.println("PARAMS: " + cmd.getReq().getParams());
		  
	      SolrInputDocument doc = cmd.getSolrInputDocument();
	      String id = (String) doc.getFieldValue("id");
	      
		  HttpSolrServer solr = new HttpSolrServer(solrURL); // Get the document that requires updating.
		  
		  SolrQuery query = new SolrQuery();
		  query.setQuery("id:" + "\"" + id + "\"");
		  query.setStart(0);								// Query the id to return ONLY the one document which requires updating.
		  try {
			QueryResponse response = solr.query(query);
			SolrDocument result = response.getResults().get(0);
			for(String fieldName : result.getFieldNames()) {
				if(doc.getFieldValue(fieldName) == null || doc.getFieldValue(fieldName) == ""){
					doc.setField(fieldName, result.getFieldValue(fieldName));		// Add all fields from original document that are not in the new document.
				}
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}

		// pass it up the chain
    	super.processAdd(cmd);
    }
  }
  
}
