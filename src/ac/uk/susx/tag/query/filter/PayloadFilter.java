package ac.uk.susx.tag.query.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.search.DocIdSet;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.spans.SpanPayloadCheckQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.BytesRef;

import org.apache.lucene.search.spans.Spans;

public class PayloadFilter extends SpanPayloadCheckQuery{
	
	public PayloadFilter(SpanQuery match, Collection<byte[]> payloadToMatch) {
		super(match, payloadToMatch);
		// TODO Auto-generated constructor stub
	}

	private float minScore;
	
	public float minScore(){
		return minScore;
	}
	
	  @Override
	  protected AcceptStatus acceptPosition(Spans spans) throws IOException {
	    boolean result = spans.isPayloadAvailable();
	    if (result == true){
	      Collection<byte[]> candidate = spans.getPayload();
	      System.err.println(candidate.size() + " canSize " + payloadToMatch.size() + " payloadSize");
	      if (candidate.size() == payloadToMatch.size()){
	        //TODO: check the byte arrays are the same
	        Iterator<byte[]> toMatchIter = payloadToMatch.iterator();
	        //check each of the byte arrays, in order
	        //hmm, can't rely on order here
	        for (byte[] candBytes : candidate) {
	        	BytesRef br = new BytesRef(candBytes);
	        	BytesRef pr = new BytesRef(toMatchIter.next());
	        	System.err.println(new BytesRef(br.bytes) + "candBytes");
	        	System.err.println(pr + "checkBytes");
	        	
	          //if one is a mismatch, then return false
	          if (Arrays.equals(br.bytes, pr.bytes) == false){
	            return AcceptStatus.NO;
	          }
	        }
	        //we've verified all the bytes
	        return AcceptStatus.YES;
	      } else {
	        return AcceptStatus.NO;
	      }
	    }
	    return AcceptStatus.YES;
	  }

}
