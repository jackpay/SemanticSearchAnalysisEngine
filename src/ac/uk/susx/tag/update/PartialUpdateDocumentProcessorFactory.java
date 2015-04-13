package ac.uk.susx.tag.update;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
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
	  
	  private String solrURL = "http://localhost:10000/solr";
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
	      newDoc.addField(unique_key, id);

	      for(String field : doc.getFieldNames()) {
	    	  if(!field.equals(unique_key)) {
	    		  String val = (String) doc.getFieldValue(field);
	    		  if(!val.contains("-- Select --") && !val.equals("") && val != null){
			    	  Map<String,Object> partialupdate = new HashMap<String,Object>();
			    	  if(field.equals("elec-vot-type-country")){
			    		  Date date = new Date();
			    		  String strDate = new SimpleDateFormat("dd/MM/yyyy").format(date); // Add the date to keep track of when details where added.
			    		  StringBuilder sb = new StringBuilder();
			    		  sb.append(doc.getFieldValue(field)).append("_").append(strDate);
			    		  partialupdate.put("add", sb.toString());
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
			SolrQuery query = new SolrQuery(unique_key + ":\"" + id + "\"").setFields("*");
			SolrDocumentList sd = solr.query(query).getResults();
			rsp.add("response", sd);
			solr.shutdown();
			rsp.add("global-resp", "The database has been updated.");
		} catch (SolrServerException e) {
			rsp.add("global-resp", "The database update failed!");
			e.printStackTrace();
		}
    }
	  
	private void addResponse(SolrQueryResponse rsp) {
		this.rsp = rsp;
	}
  }
  
}
