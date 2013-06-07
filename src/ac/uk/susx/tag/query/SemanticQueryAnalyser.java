package ac.uk.susx.tag.query;

public class SemanticQueryAnalyser {
	
	private static final String POSTAG = "postype";
	private static final String CHUNK = "chunktype";
	private final String queryString;
	
	
	public SemanticQueryAnalyser(String qstr){
		queryString = qstr;
	}
	
	public boolean isSemanticQuery(){	
		return (queryString.contains(POSTAG) || queryString.contains(CHUNK)) ? true : false;
	}
	
	public String analyseQuery(){
		StringBuilder sb = new StringBuilder();
		
		return sb.toString();
	}
	
	
}
