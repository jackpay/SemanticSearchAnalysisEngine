package ac.uk.susx.tag.query;

import java.util.HashMap;

import org.apache.lucene.search.Query;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.LuceneQParserPlugin;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;
import org.apache.solr.search.QueryParsing;
import org.apache.solr.search.SolrQueryParser;
import org.apache.solr.search.SyntaxError;
import org.apache.uima.cas.CAS;

public class SemanticQParserPlugin extends QParserPlugin{
	
	private static final String NAME = "semantic";
	
	public SemanticQParserPlugin(){}

	@Override
	public void init(NamedList arg0) {}

	@Override
	public QParser createParser(String qString, SolrParams lpar, SolrParams par,
			SolrQueryRequest sReq) {
		return new SemanticQParser(qString, lpar, par, sReq);
	}
	
	
	public class SemanticQParser extends QParser{

		public SemanticQParser(String qString, SolrParams localParams, SolrParams params,
				SolrQueryRequest req) {
			super(qString, localParams, params, req);	
		}

		@Override
		public Query parse() throws SyntaxError {
			
			String qstr = getString();
		    if (qstr == null || qstr.length() == 0) return null;

		    String defaultField = getParam(CommonParams.DF);
		    if (defaultField == null) {
		      defaultField = getReq().getSchema().getDefaultSearchFieldName();
		    }
		    SemanticQueryAnalyser sqa = new SemanticQueryAnalyser(qstr);
		    if(sqa.isSemanticQuery()){
		    	
		    }
		    
			return new LuceneQParserPlugin().createParser(qstr, localParams, params, req).parse();
		}
	}
}
