package ac.uk.susx.tag.global.select;

import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.internal.csv.writer.CSVConfig;
import org.apache.solr.internal.csv.writer.CSVField;
import org.apache.solr.internal.csv.writer.CSVWriter;

public class QuerytoCSV {
	
	private HttpSolrServer hss;
	private final String outputDir;
	private static final String DEFAULT_SOLR = "http://localhost:8983/solr";
	private static final String DEFAULT_QUERY = "analyst-relevant:false"; //"parsed:false";
	private static final int rows = 200;
	private static final String[] fields = {"uuid","url","content","lang","location","tstamp"};
	private CSVWriter writer;
	private FileWriter fwriter;
	
	public QuerytoCSV(String solrURL,String outputDir) {
		this.outputDir = outputDir;
		init(solrURL);
	}
	
	public QuerytoCSV(String outputDir){
		this.outputDir = outputDir;
		init(DEFAULT_SOLR);
	}
	
	public void query(String query) {
		SolrQuery q = (new SolrQuery(query)).setRows(rows).setSortField("uuid", SolrQuery.ORDER.asc).setStart(0).setFields(fields);
		long i = 0;
		long numFound;
		try {
			numFound = hss.query(q).getResults().getNumFound();
			while(i < numFound){
				QueryResponse rsp = hss.query(q);
				for(SolrDocument doc : rsp.getResults()) {
					writer.writeRecord(doc.getFieldValueMap());
				}
				i += rows;
				q.setStart((int) i);
			}
			fwriter.close();
		} catch (SolrServerException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public boolean writeToFile(String line) {
		return false;
	}
	
	
	
	public void init(String solrURL) {
		try {
			// Set up the writer to perform the csv writing.
			CSVConfig config = new CSVConfig();
			config.setDelimiter(',');
			config.setEndTrimmed(true);
			config.setFill(CSVConfig.FILLLEFT);
			CSVField[] csvFields = new CSVField[fields.length];
			for(int i = 0; i < fields.length; i++){
				csvFields[i] = new CSVField(fields[i]);
			}
			config.setFields(csvFields);
			config.setFieldHeader(true);
			
			//Create output file name.
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
			String filename = new StringBuilder().append(outputDir).append("/").append("output.").append(dateFormat.format(Calendar.getInstance().getTime())).append(".csv").toString();
			
			// Set filewriter.
			writer = new CSVWriter(config);
			fwriter = new FileWriter(filename);
			writer.setWriter(fwriter);

			// Create the connection to the Solr server.
			hss = new HttpSolrServer(solrURL);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		
		String query = DEFAULT_QUERY;
		if(args != null && args.length > 0) {
			query = args[0];
		}
		QuerytoCSV dbq = new QuerytoCSV("/Volumes/LocalDataHD/jp242/Documents/Projects/test");
		dbq.query(query);
	}

}
