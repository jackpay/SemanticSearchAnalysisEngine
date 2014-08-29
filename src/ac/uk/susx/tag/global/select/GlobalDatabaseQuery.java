package ac.uk.susx.tag.global.select;

import java.io.IOException;
import java.io.Writer;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;

public class GlobalDatabaseQuery {
	
	private HttpSolrServer hss;
	private final String outputDir;
	private String outputFileName;
	private static final String WT = "&wt=csv";
	private static final String DEFAULT_SOLR = "http://localhost:8983/solr";
	
	public GlobalDatabaseQuery(String solrURL,String outputDir) {
		hss = new HttpSolrServer(solrURL);
		this.outputDir = outputDir;
	}
	
	public GlobalDatabaseQuery(String outputDir){
		hss = new HttpSolrServer(DEFAULT_SOLR);
		this.outputDir = outputDir;
	}
	
	
	
	public void performQuery(String query) {
		
	}
	
	
	public static void main(String[] args) {
		
		GlobalDatabaseQuery dbq = new GlobalDatabaseQuery();
		
	}

}
