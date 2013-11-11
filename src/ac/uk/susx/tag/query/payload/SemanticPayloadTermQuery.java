package ac.uk.susx.tag.query.payload;

import java.io.IOException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.payloads.PayloadFunction;
import org.apache.lucene.search.payloads.PayloadTermQuery;
import org.apache.lucene.util.BytesRef;

public class SemanticPayloadTermQuery extends PayloadTermQuery {
	
	private final BytesRef compTag;

	public SemanticPayloadTermQuery(Term term, PayloadFunction function, boolean includeSpanScore,BytesRef compTag) {
		super(term, function, includeSpanScore);
		this.compTag = compTag;
	}
	
	  @Override
	  public Weight createWeight(IndexSearcher searcher) throws IOException {
		  if(searcher.getSimilarity() instanceof SemanticPayloadTermSimilarity){
			  SemanticPayloadTermSimilarity ss = (SemanticPayloadTermSimilarity) searcher.getSimilarity();
			  ss.setCompPayload(compTag);
		  }
		  return new PayloadTermWeight(this, searcher);
	  }
	  
	  @Override
	  public int hashCode() {
		  int prime = 31;
		  int result = super.hashCode();
		  result = prime * result + compTag.hashCode();
	    return result;
	  }
	  
	  public BytesRef getCompTag(){
		  return compTag;
	  }
	
}
