package ac.uk.susx.tag.crawl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.handler.RequestHandlerBase;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.response.SolrQueryResponse;

import ac.uk.susx.tag.configuration.Configuration;
import ac.uk.susx.tag.runner.Runner;
import ac.uk.susx.tag.utils.PollingServiceException;

/**
 * Uses the GlobalStudiesPollingService project to crawl the web, classify the resulting web pages and update the Solr index accordingly.
 * @author jp242
 *
 */
public class CrawlerHandler extends RequestHandlerBase {
	// Polling details
	private String crawlConfig;
	
	// Crawling details
	private String nutchLoc;
	private static final String COMMAND = "/crawl";
	private String seed;
	private String crawldb;
	private int depth = 1;
	
	private ExecutorService es;
	private SolrParams params;
	private Configuration config;
	private Runner runner;
	
	public CrawlerHandler(){}
	
	public CrawlerHandler(String crawlConfig) {
		this.crawlConfig = crawlConfig;
	}
	
	public void setParams(SolrParams params) {
		this.params = params;
	}
	
	public void config(String con) {
		this.crawlConfig = con;
	}
	
	public void nutch(String location) {
		nutchLoc = location;
	}
	
	public void setDepth(int d) {
		depth = d;
	}
	
	public void setCrawldb(String db) {
		crawldb = db;
	}
	
	public void setSeed(String seed) {
		this.seed = seed;
	}

	@Override
	/**
	 * The main method which calls the crawling process.
	 */
	public void handleRequestBody(SolrQueryRequest req, SolrQueryResponse resp) throws Exception {
		
		es = Executors.newSingleThreadExecutor();
		params = req.getParams();
		
		if(crawlConfig == null) {
			crawlConfig = req.getParams().get("crawl-config");
		}
		
		if(crawlConfig == null) {
			throw new PollingServiceException("No configuration file for the polling service was provided.");
		}
		
		if(nutchLoc == null) {
			nutchLoc = req.getParams().get("nutch-dir");
		}
		
		if(nutchLoc == null) {
			throw new PollingServiceException("No configuration file for the polling service was provided.");
		}
		
		if(req.getParams().get("depth") == null) {
			depth = 3;
		}
		
		if(crawldb == null) {
			crawldb = req.getParams().get("crawl-db");
		}
		
		if(crawldb == null) {
			throw new PollingServiceException("No crawl database provided.");
		}
		
		if(seed == null) {
			seed = req.getParams().get("seed-dir");
		}
		
		if(seed == null) {
			throw new PollingServiceException("No crawl database provided.");
		}
		
		
		
		runner = new Runner(crawlConfig);
		config = runner.configuration();
		System.out.println(config.mysqlKeyField());
		
		addnewSeedList();		// Add any additional seed urls to the seedlist (if provided by the user).
		
		crawl();		// Crawl the web using Nutch if requested by the user.
		
		method51();		// Classify the crawled urls and update the Solr index.
		
		blacklist();	// Add urls which were classified as irrelevant to the crawler blacklist.
		
		email();		// Email the listed recipients 
		
		cleanup(); 		// Empty the MySQL tables when done.
		
		// Run the shutdown on a seperate thread so that the method returns straight away.
		new Thread(new Runnable() {

			@Override
			public void run() {
				es.shutdown();
				try {
					es.awaitTermination(100, TimeUnit.HOURS);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}).start();
		
		resp.add("global-resp", "The web crawl has been initialised. If the option was selected, you will be emailed when any new documents are ready for analysis.");
		
	}
	
	private void cleanup() {
		if(params.getBool("cleanup-option", false)){
			call(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					runner.clearMySQL();
					return true;
				}
				
			});
			
		}
	}

	private void email() {
		if(params.getBool("email-option", false)){
			call(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					runner.email();
					return true;
				}
				
			});
		}
	}

	private void blacklist() {
		if(params.getBool("blacklist-check", false)){
			call(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					runner.solrtoNutch();
					return true;
				}
				
			});
		}
	}

	private void method51() {
		if(params.getBool("meth51-check", false)){
			call(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					runner.solrtoMySQL();
					runner.mySQLtoSolr();
					return true;
				}
				
			});
		}
	}

	private void crawl() {
		if(params.getBool("crawl-option", false)){
			call(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					ProcessBuilder pb = new ProcessBuilder(nutchLoc+COMMAND,seed,crawldb,config.newSolr().getBaseURL(),String.valueOf(depth)); // Build the command line call.
					pb.redirectErrorStream(true);
					Process process = pb.start();
					InputStreamReader isr = new  InputStreamReader(process.getInputStream());
				    BufferedReader br = new BufferedReader(isr); // Print the output of the Nutch crawler.
					String lineRead;
				    while ((lineRead = br.readLine()) != null) {
				        System.out.println(lineRead);
				    }
					int rc = process.waitFor();
					br.close();
					isr.close();
					return true;
				}
				
			});
		}
	}

	private void addnewSeedList() {
		if(params.get("datafile") != null && !params.get("datafile").equals("")) {
			config.addNewSeedlist(params.get("datafile"));
			call(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					runner.appendSeedList();
					return true;
				}
				
			});
		}
	}

	/**
	 * Submits job.
	 * @param callable
	 */
	private void call(Callable<?> callable) {
		Future<?> out = es.submit(callable);
	}
	
	@Override
	public String getSource() {
		return null;
	}
	
	@Override
	public String getDescription() {
		return "Uses the GlobalStudiesPollingService project to crawl the web, classify the resulting web pages and update the Solr index accordingly.";
	}
	
	public static void main(String[] args) {
		ModifiableSolrParams sp = new ModifiableSolrParams();
		sp.add("cleanup-option", "true");
		sp.add("crawl-option","true");
		sp.add("meth51-check","true");
		sp.add("blacklist-check","true");
		sp.add("email-option","true");
		CrawlerHandler ch = new CrawlerHandler();
		ch.setParams(sp);
		ch.config("conf/globalstudies-config.xml");
			try {
				ch.handleRequestBody(null, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
