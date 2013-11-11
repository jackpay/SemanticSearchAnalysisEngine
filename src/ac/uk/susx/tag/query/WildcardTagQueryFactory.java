package ac.uk.susx.tag.query;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.spans.SpanPayloadCheckQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.util.BytesRef;

import ac.uk.susx.tag.query.payload.SemanticPayloadTermFunction;
import ac.uk.susx.tag.query.payload.SemanticPayloadTermQuery;

public class WildcardTagQueryFactory {

	private static final HashMap<String, String[]> tags;
	static {
		tags = new HashMap<String,String[]>();
		tags.put("postag", new String[] {"$","\"","\\(","\\)", ",", ".",":","CC","CD","DT","EX","FW","IN","JJ","JJR","JJS","LS","MD","NN","NNP","NNPS","NNS",
				"PDT","POS","PRP","PRP$","RB","RBR","RBS","RP","SYM","TO","UH","VB","VBD","VBG","VBN","VBP","VBZ","WDT","WP","WP$","WRB"});
		tags.put("chunktoken", new String[] {"NP","PP","VP","ADVP","SBAR","PRT","INTJ","PNP"});
	}

	public static ArrayList<SpanQuery> getQuery(String posTag, String field, String term, boolean boostField){
		ArrayList<byte[]> payloads = new ArrayList<byte[]>();
		ArrayList<SpanQuery> queries = new ArrayList<SpanQuery>();
		if(tags.get(field) != null){
			for(String p : tags.get(field)){
				if(stringMatch(posTag,p)){
					payloads.add(p.getBytes());
				}
			}
		}
		if(!payloads.isEmpty()){
			if(boostField){
				queries.addAll(getSemanticPayloadQueries(payloads, field, term));
			}
			for(byte[] payload :  payloads){
				ArrayList<byte[]> payloadColl = new ArrayList<byte[]>();
				payloadColl.add(payload);
				queries.add(new SpanPayloadCheckQuery(new SpanTermQuery(new Term(field,term)),payloadColl));
			}
			queries.add(new SpanPayloadCheckQuery(new SpanTermQuery(new Term(field,term)),payloads));
		}
		return queries;
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
	
	private static ArrayList<SpanQuery> getSemanticPayloadQueries(ArrayList<byte[]> payloads, String field, String term){
		ArrayList<SpanQuery> queries = new ArrayList<SpanQuery>();
		for(byte[] payload : payloads){
			queries.add(new SemanticPayloadTermQuery(new Term(field, term), new SemanticPayloadTermFunction(),true, new BytesRef(payload)));
		}
		return queries;
	}
	
	public static BooleanQuery createBooleanQuery(ArrayList<SpanQuery> queries) {
		BooleanQuery boolQuery = new BooleanQuery();
		boolQuery.setMinimumNumberShouldMatch(1);
		for(SpanQuery query : queries){
			boolQuery.add(query, BooleanClause.Occur.SHOULD);
		}
		return boolQuery;
	}

}
