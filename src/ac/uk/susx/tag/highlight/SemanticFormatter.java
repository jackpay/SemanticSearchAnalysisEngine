package ac.uk.susx.tag.highlight;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.TokenGroup;

public class SemanticFormatter implements Formatter {
	
	private final String PRE;
	private final String POST;
	private static final String START = "<";
	private static final String END = ">";
	private static final String DEFAULT_PRE = START + "SEM";
	private static final String DEFAULT_POST = START + "/SEM" + END;
	private static final String DELIM = "=";
	private static final String POS_TAG = "pos" + DELIM;
	private static final String CHUNK_TAG = "chunk" + DELIM;
	private static final String PROX_TAG = "prox" + DELIM;
	private static final String CLOSE_TO = "closeto" + DELIM;
	private static final String SPACE = " ";
	
	public SemanticFormatter(){
		this(DEFAULT_PRE,DEFAULT_POST);
	}
	
	public SemanticFormatter(String pre, String post){
		this.PRE = pre;
		this.POST = post;
	}

	@Override
	public String highlightTerm(String originalText, TokenGroup tokenGroup) {
	    if (tokenGroup.getTotalScore() <= 0) {
	        return originalText;
	    }
	    String out = null;
	    for(int i = 0; i < tokenGroup.getNumTokens(); i++){
	    	Token token = tokenGroup.getToken(i);
	    	System.err.println(token.getPayload());
	    	System.err.println(token.getPositionIncrement());
	    	System.err.println(token.getPositionLength());
	    	System.err.println(token.getFlags());
	    	System.err.println(token.getBytesRef());
	    	//System.err.println(originalText);
	    	out = PRE + SPACE + POS_TAG + token.getPayload() + END + originalText + POST;
	    }
		return out;
	}

}
