package ac.uk.susx.tag.query.payload;

import org.apache.lucene.search.payloads.PayloadFunction;

public class SemanticPayloadTermFunction extends PayloadFunction {

	  @Override
	  public float currentScore(int docId, String field, int start, int end, int numPayloadsSeen, float currentScore, float currentPayloadScore) {
	    return currentPayloadScore + currentScore;
	  }

	  @Override
	  public float docScore(int docId, String field, int numPayloadsSeen, float payloadScore) {
		  System.err.println(payloadScore);
	    return numPayloadsSeen > 0 ? payloadScore : 0;
	  }

	  @Override
	  public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + this.getClass().hashCode();
	    return result;
	  }

	  @Override
	  public boolean equals(Object obj) {
	    if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    return true;
	  }
}
