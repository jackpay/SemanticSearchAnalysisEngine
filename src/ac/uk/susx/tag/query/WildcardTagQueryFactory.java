package ac.uk.susx.tag.query;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.spans.SpanPayloadCheckQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.util.BytesRef;

import ac.uk.susx.tag.query.payload.SemanticPayloadTermFunction;
import ac.uk.susx.tag.query.payload.SemanticPayloadTermQuery;

public class WildcardTagQueryFactory {
	
//	private String posTag;
//	private String field;
//	private String term;
//	private boolean boostField;
	private static final HashMap<String, String[]> tags;
	static {
		tags = new HashMap<String,String[]>();
		tags.put("postag", new String[] {"$","\"","\\(","\\)", ",", ".",":","CC","CD","DT","EX","FW","IN","JJ","JJR","JJS","LS","MD","NN","NNP","NNPS","NNS",
				"PDT","POS","PRP","PRP$","RB","RBR","RBS","RP","SYM","TO","UH","VB","VBD","VBG","VBN","VBP","VBZ","WDT","WP","WP$","WRB"});
		tags.put("chunktag", new String[] {"NP","PP","VP","ADVP","SBAR","PRT","INTJ","PNP"});
	}
	
//	/**
//	 * For debugging purposes.
//	 * @param args
//	 */
//	public static void main(String[] args){
//		WildcardTagQueryFactory wag = new WildcardTagQueryFactory("P*","chunktag","goose",false);
//		Query query = wag.getQuery("P*","chunktag","goose",false);
//		System.err.println(query);
//	}
//	
//	public WildcardTagQueryFactory(String posTag, String field, String term, boolean boostField) {
////		this.posTag = posTag;
////		this.field = field;
////		this.term = term;
////		this.boostField = boostField;
////		tags = new HashMap<String,String[]>();
////		String[] pTag = {"$","\"","\\(","\\)", ",", ".",":","CC","CD","DT","EX","FW","IN","JJ","JJR","JJS","LS","MD","NN","NNP","NNPS","NNS",
////				"PDT","POS","PRP","PRP$","RB","RBR","RBS","RP","SYM","TO","UH","VB","VBD","VBG","VBN","VBP","VBZ","WDT","WP","WP$","WRB"};
////		String[] cTag = {"NP","PP","VP","ADVP","SBAR","PRT","INTJ","PNP"};
////		tags.put("postag", pTag);
////		tags.put("chunktag", cTag);
//	}
	
	
	public static ArrayList<SpanQuery> getQuery(String posTag, String field, String term, boolean boostField){
		ArrayList<byte[]> payloads = new ArrayList<byte[]>();
		ArrayList<SpanQuery>
		if(tags.get(field) != null){
			for(String p : tags.get(field)){
				if(stringMatch(posTag,p)){
					System.err.println(p);
					payloads.add(p.getBytes());
				}
			}
		}
		if(!payloads.isEmpty()){
			return boostField ? getBooleanBoostQuery(payloads, field, term) : new SpanPayloadCheckQuery(new SpanTermQuery(new Term(field,term)),payloads);
		}
		return null;
	}
	
	private static boolean stringMatch(String posTag, String compPosTag){
		boolean matches = true;
		matches = posTag.length() <= compPosTag.length();
		int i = 0;
		while(matches == true && i < posTag.length() && i < compPosTag.length()){
			matches = (posTag.charAt(i) == '*' || posTag.charAt(i) == compPosTag.charAt(i));
			i++;
		}
		return matches;
	}
	
	private static Query getBooleanBoostQuery(ArrayList<byte[]> payloads, String field, String term){
		BooleanQuery query = new BooleanQuery();
		query.setMinimumNumberShouldMatch(1);
		for(byte[] payload : payloads){
			query.add(new SemanticPayloadTermQuery(new Term(field, term), new SemanticPayloadTermFunction(),true, new BytesRef(payload)), BooleanClause.Occur.SHOULD);
		}
		return query;
	}

}
