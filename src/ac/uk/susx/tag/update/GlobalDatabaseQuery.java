package ac.uk.susx.tag.update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.handler.RequestHandlerBase;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;
import org.apache.solr.update.AddUpdateCommand;
import org.apache.solr.update.processor.UpdateRequestProcessor;

public class GlobalDatabaseQuery extends RequestHandlerBase {
	
	private String query = "elec-vot-type-country:*";
	private String solrURL = "http://localhost:8983/solr";
	private String unique_key = "id";
	private String query_field = "elec-vot-type-country";
	private static final int batch = 200;
	private String DELIM = "_";

//	@Override
//	public UpdateRequestProcessor getInstance(SolrQueryRequest req, SolrQueryResponse rsp, UpdateRequestProcessor next) {
//		GlobalDatabaseQueryUpdateRequestProcessor gdq = new GlobalDatabaseQueryUpdateRequestProcessor(next);
//		gdq.setQueryResponse(rsp);
//		return gdq;
//	}
	

	@Override
	public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse rsp) throws Exception {
		HttpSolrServer solr = new HttpSolrServer(solrURL); // Get the document that requires updating.
		HashMap<String,ArrayList<ArrayList<String>>> mappings = new HashMap<String,ArrayList<ArrayList<String>>>();
		SolrQuery query = new SolrQuery(query_field + ":*").setFields(new String[]{unique_key,query_field}).setRows(batch).setStart(0).setSortField(unique_key,SolrQuery.ORDER.asc);
		
		try {
			int i = 0;
			long numFound = solr.query(query).getResults().getNumFound();
			while(i < numFound) {
				QueryResponse res = solr.query(query);
				for(SolrDocument doc : res.getResults()) {
					ArrayList<String> votes = (ArrayList<String>) doc.getFieldValue(query_field);
					for(String vote : votes) {
						ArrayList<String> voteSplit = new ArrayList<String>(Arrays.asList(vote.split(DELIM)));
						if(mappings.get(voteSplit.get(2)) == null) {
							mappings.put(voteSplit.get(2), new ArrayList<ArrayList<String>>());
						}
						voteSplit.add((String) doc.getFieldValue(unique_key));
						mappings.get(voteSplit.get(2)).add(voteSplit);
					}
				}
				i += batch;
				query.setStart(i);
			}
			
			rsp.add("glob-mapping", mappings);
			solr.shutdown();
			
		} catch (SolrServerException e) {
			rsp.add("updated", "The database update failed!");
			e.printStackTrace();
		}
		
	}
	
//	public class GlobalDatabaseQueryUpdateRequestProcessor extends UpdateRequestProcessor {
//
//		public GlobalDatabaseQueryUpdateRequestProcessor(UpdateRequestProcessor next) {
//			super(next);
//		}
//		
//		public GlobalDatabaseQueryUpdateRequestProcessor(UpdateRequestProcessor next, String query, String solrURL, String unique_key, String query_field, String delim) {
//			super(next);
//			this.query = query;
//			this.unique_key = unique_key;
//			this.query_field = query_field;
//			this.solrURL = solrURL;
//		}
//		
//		@Override
//		public void processAdd(AddUpdateCommand cmd) throws IOException {
//			
//		}
//		
//		public void setQueryResponse(SolrQueryResponse rsp) {
//			this.rsp = rsp;
//		}
//		
//	}

	@Override
	public String getDescription() {
		return "A RequestHandler to deal with a specific query response required to populate a databasein in the Global Studies project.";
	}

	@Override
	public String getSource() {
		return null;
	}

}
