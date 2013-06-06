package ac.uk.susx.tag.query;

import org.apache.lucene.search.Query;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.QParser;
import org.apache.solr.search.SyntaxError;

public class PoSTagParser extends QParser{

	public PoSTagParser(String arg0, SolrParams arg1, SolrParams arg2,
			SolrQueryRequest arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Query parse() throws SyntaxError {
		// TODO Auto-generated method stub
		return null;
	}

}
