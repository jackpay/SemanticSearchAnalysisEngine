package ac.uk.susx.tag.query.payload;

import java.io.IOException;

import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Weight;
import org.apache.lucene.search.payloads.PayloadNearQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanWeight;
import org.apache.lucene.util.BytesRef;

public class SemanticPayloadNearSpanQuery extends PayloadNearQuery {
	
	private final BytesRef compTag;
	
	public SemanticPayloadNearSpanQuery(SpanQuery[] clauses, int slop,
			boolean inOrder, BytesRef compPayload, String fieldName) {
		super(clauses, slop, inOrder, new SemanticPayloadTermFunction());
		this.fieldName =  fieldName;
		this.compTag = compPayload;
	}
	
	  @Override
	  public Weight createWeight(IndexSearcher searcher) throws IOException {
		  if(searcher.getSimilarity().getClass() == SemanticPayloadTermSimilarity.class){
			  SemanticPayloadTermSimilarity ss = (SemanticPayloadTermSimilarity) searcher.getSimilarity();
			  ss.addCompPayload(compTag);
		  }
	    return new PayloadNearSpanWeight(this, searcher);
	  }
	
	  @Override
	  public int hashCode() {
		  int prime = 31;
		  int result = super.hashCode();
		  result = prime * result + compTag.hashCode();
	    return result;
	  }

}
