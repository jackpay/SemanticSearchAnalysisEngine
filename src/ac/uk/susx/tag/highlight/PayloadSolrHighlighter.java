package ac.uk.susx.tag.highlight;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Terms;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.DocIterator;
import org.apache.solr.search.DocList;
import org.apache.solr.search.SolrIndexSearcher;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.core.SolrCore;
import org.apache.solr.highlight.DefaultSolrHighlighter;

public class PayloadSolrHighlighter extends DefaultSolrHighlighter {
	
	public PayloadSolrHighlighter(SolrCore solrCore){
		super(solrCore);
	}
	
	public PayloadSolrHighlighter(){}

	@Override
	public NamedList<Object> doHighlighting(DocList docs, Query query, SolrQueryRequest req, String[] defaultFields) throws IOException {
	    SolrParams params = req.getParams();
	    
		SolrIndexSearcher searcher = req.getSearcher();
		AtomicReader ar = req.getSearcher().getAtomicReader();

		DocIterator di = docs.iterator();
		while(di.hasNext()){
			int i = di.nextDoc();
			Terms r = ar.getTermVector(i, "");
			Document doc = searcher.doc(i);
			IndexableField ifs = doc.getField("");
			Terms t = 
			TopDocs td = searcher.search(query, Integer.MAX_VALUE);
		}
		
		return null;
	}
	
}
