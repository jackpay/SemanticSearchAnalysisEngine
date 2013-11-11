package ac.uk.susx.tag.query.payload;

import java.util.ArrayList;

import org.apache.lucene.search.similarities.DefaultSimilarity;
import org.apache.lucene.util.BytesRef;

public class SemanticPayloadTermSimilarity extends DefaultSimilarity {
	private ArrayList<BytesRef> compPayload;
	
	@Override
	public float scorePayload(int doc, int start, int end, BytesRef payload) {
		return compPayload.contains(payload) ? 1.0f : 0.0f;
	}

	public void addCompPayload(BytesRef payload){
		if(compPayload == null){
			compPayload = new ArrayList<BytesRef>();
		}
		compPayload.add(payload);
	}
  
}
