package ac.uk.susx.tag.query.payload;

import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.util.BytesRef;

public class SemanticPayloadTermSimilarity extends DefaultSimilarity {
	
	private BytesRef compPayload;
	
	  @Override
	  public float scorePayload(int doc, int start, int end, BytesRef payload) {
		  System.err.println(compPayload + " - Reached new similarity measure");
		  return payload.bytesEquals(compPayload) ? 1.0f : 0.0f;
	  }

	  public void setCompPayload(BytesRef payload){
		  compPayload = payload;
		  System.err.println("similarity - comp set");
	  }
	  
	  public BytesRef getComp(){
		  return compPayload;
	  }
}
