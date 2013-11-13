package ac.uk.susx.tag.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.spans.SpanMultiTermQueryWrapper;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanPayloadCheckQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.util.BytesRef;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.schema.SchemaField;
import org.apache.solr.search.LuceneQParserPlugin;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;
import org.apache.solr.search.QueryParsing;
import org.apache.solr.search.SyntaxError;

import ac.uk.susx.tag.query.payload.SemanticPayloadTermFunction;
import ac.uk.susx.tag.query.payload.SemanticPayloadTermQuery;

/**
 * @author jackpay
 *
 */
public class SemanticQParserPlugin extends QParserPlugin {
	
	private static final String NAME = "semantic";
	
	public SemanticQParserPlugin() {}

	@Override
	public void init(NamedList arg0) {}

	@Override
	public QParser createParser(String qString, SolrParams lpar, SolrParams par,
			SolrQueryRequest sReq) {
		return new SemanticQParser(qString, lpar, par, sReq);
	}
	
	
	public class SemanticQParser extends QParser {
		
		private String defaultField;

		public SemanticQParser(String qString, SolrParams localParams, SolrParams params,
				SolrQueryRequest req) {
			super(qString, localParams, params, req);	
		}

		@Override
		public Query parse() throws SyntaxError {
			
			String qstr = getString();
		    if (qstr == null || qstr.length() == 0) return null;

		    SemanticQueryAnalyser sqa = new SemanticQueryAnalyser();
		    if(sqa.isSemanticQuery()) {
		    	BytesRef tag = sqa.getPayload();
	    		ArrayList<byte[]> payload = new ArrayList<byte[]>();
	    		payload.add(tag.bytes);
		    	if(!sqa.isProximityQuery()) {
		    		if(sqa.isChunkandPoS()) {
		    			ArrayList<byte[]> posBytes = new ArrayList<byte[]>();
		    			posBytes.add(sqa.getPoSTagPayload().bytes);
		    			ArrayList<byte[]> chunkBytes = new ArrayList<byte[]>();
		    			chunkBytes.add(sqa.getChunkPayload().bytes);
		    			
		    			Query posQuery = sqa.isWildcardTag(getParam(sqa.getPoSType())) ? WildcardTagQueryFactory.createBooleanQuery(sqa.getWildcardTagQueries(sqa.getPoSField(), getParam(sqa.getPoSType()), getParam(QueryParsing.V))) : sqa.isBoostField(sqa.getPoSField()) ? new SemanticPayloadTermQuery(new Term(sqa.getPoSField(), getParam(QueryParsing.V)), new SemanticPayloadTermFunction(), true, sqa.getPoSTagPayload()) : new SpanPayloadCheckQuery(new SpanTermQuery(new Term(sqa.getPoSField(), getParam(QueryParsing.V))), posBytes);
		    			Query chunkQuery = sqa.isWildcardTag(getParam(sqa.getChunkType())) ? WildcardTagQueryFactory.createBooleanQuery(sqa.getWildcardTagQueries(sqa.getChunkField(), getParam(sqa.getChunkType()), getParam(QueryParsing.V))) : sqa.isBoostField(sqa.getChunkField()) ? new SemanticPayloadTermQuery(new Term(sqa.getChunkField(), getParam(QueryParsing.V)), new SemanticPayloadTermFunction(), true, sqa.getChunkPayload()) : new SpanPayloadCheckQuery(new SpanTermQuery(new Term(sqa.getChunkField(), getParam(QueryParsing.V))), chunkBytes);
		    			BooleanQuery boolQuery = new BooleanQuery();
		    			boolQuery.add(chunkQuery, BooleanClause.Occur.MUST);
		    			boolQuery.add(posQuery, BooleanClause.Occur.MUST);
		    			return sqa.addFieldQuery(boolQuery);
		    		}
		    		Query query = sqa.isWildcardTag(sqa.getPayload().utf8ToString()) ? WildcardTagQueryFactory.createBooleanQuery(sqa.getWildcardTagQueries(defaultField, sqa.getPayload().utf8ToString(), getParam(QueryParsing.V))) : sqa.isBoostField(defaultField) ? new SemanticPayloadTermQuery(new Term(defaultField, getParam(QueryParsing.V)), new SemanticPayloadTermFunction(), true, sqa.getPayload()) : new SpanPayloadCheckQuery(new SpanTermQuery(new Term(defaultField, getParam(QueryParsing.V))), payload);
		    		return sqa.addFieldQuery(query);
		    	}
		    	else{
		    		if(sqa.isChunkandPoS()){
			    		try {
			    			SpanQuery posSpan = new SpanNearQuery(sqa.getSpanQueries(sqa.getPoSType(), sqa.getPoSField()), sqa.getDistance(), false, true);
			    			SpanQuery chunkSpan = new SpanNearQuery(sqa.getSpanQueries(sqa.getChunkType(), sqa.getChunkField()), sqa.getDistance(), true, true);
			    			BooleanQuery boolQuery = new BooleanQuery();
			    			boolQuery.add(posSpan, BooleanClause.Occur.MUST);
			    			boolQuery.add(chunkSpan, BooleanClause.Occur.MUST);
			    			return sqa.addFieldQuery(boolQuery);
						} catch (IOException e) {
							e.printStackTrace();
						}
		    		}
		    		if(getParam(sqa.getPoSType()) != null){
		    			try {
		    				SpanQuery[] queries = sqa.getSpanQueries(sqa.getPoSType(), sqa.getPoSField());
		    				if(queries.length > sqa.getTags(sqa.getPoSType()).length){
		    					return sqa.addFieldQuery(sqa.getBooleanSpanNearQueries(queries));
		    				}
		    				else{
								return sqa.addFieldQuery(new SpanNearQuery(sqa.getSpanQueries(sqa.getPoSType(), sqa.getPoSField()), sqa.getDistance(), true, true));
		    				}
						} catch (IOException e) {
							e.printStackTrace();
						}
		    		}
		    		try {
		    			SpanQuery[] queries = sqa.getSpanQueries(sqa.getChunkType(), sqa.getChunkField());
		    			if(queries.length > sqa.getTags(sqa.getPoSType()).length){
		    				return sqa.addFieldQuery(sqa.getBooleanSpanNearQueries(queries));
		    			}
						return sqa.addFieldQuery(new SpanNearQuery(sqa.getSpanQueries(sqa.getChunkType(), sqa.getChunkField()), sqa.getDistance(), true, true));
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	}
		    }
			return new LuceneQParserPlugin().createParser(qstr, localParams, params, req).parse();
		}
		
		public class SemanticQueryAnalyser {
			
			// Query parameter prefixes
			private static final String POSTAG = "postype";
			private static final String POSTAG_FIELD = "postag";
			private static final String CHUNK_FIELD = "chunktoken";
			private static final String CHUNK = "chunktype";
			private static final String DIST = "tdist";
			private static final String BOOST_FIELD = "sbf";
			//private static final String IN_CHUNK = "inck"; // Was going to be used as a boolean to specify whether matched terms must be in the same chunk as one another.
			
			// Used to chain a number of tags or tokens
			private static final String TAG_DELIM = "\\+";
			private static final String TOK_DELIM = "\\+";
			
			// String for wildcards in tag matching
			private static final String TAG_WILDCARD = "*";
			
			public BytesRef getPayload() {
				if(getParam(CHUNK) != null){
					return getChunkPayload();
				}
				return getPoSTagPayload();
			}

			public boolean isSemanticQuery() {
				if(getParam(POSTAG) != null){
					defaultField = POSTAG_FIELD;
				}
				else{
					if(getParam(CHUNK) != null) {
						defaultField = CHUNK_FIELD;
					}
				}
				return getParam(POSTAG) != null || getParam(CHUNK) != null;
			}
			
			public boolean isProximityQuery() {
				return tokenise().length > 1;
			}
			
			public boolean isChunkandPoS() {
				return (getParam(POSTAG) != null && getParam(CHUNK) != null);
			}
			
			public boolean isWildcardTag(String payload) {
				return (payload.contains("*") && payload.length() > 1);
			}
			
			public ArrayList<SpanQuery> getWildcardTagQueries(String field, String tag, String term) {
				return WildcardTagQueryFactory.getQuery(tag, field, term, isBoostField(field));
			}
			
			public BooleanQuery getBooleanSpanNearQueries(SpanQuery[] queries) {
				BooleanQuery boolQuery = new BooleanQuery();
				boolQuery.setMinimumNumberShouldMatch(1);
				for(int i = 0; i < queries.length; i++){
					for(int j = 0; j < queries.length; j++){
						if(!queries[i].equals(queries[j])){
							SpanNearQuery spanQ = new SpanNearQuery(new SpanQuery[]{queries[i],queries[j]},getDistance(),true,true);
							boolQuery.add(spanQ,BooleanClause.Occur.SHOULD);
						}
					}
				}
				return boolQuery;
			}
			
			public int getDistance() {
				if(getParam(DIST) != null) {
					try{
						int dist = Integer.parseInt(getParam(DIST));
						return dist;
					}
					catch (Exception e){
						return Integer.MAX_VALUE;
					}
				}
				return Integer.MAX_VALUE;
			}
			
			public String[] getTags(String type) {
				if(getParam(type) != null){
					return getParam(type).split(TAG_DELIM);
				}
				return null;
			}
			
			public SpanQuery[] getSpanQueries(String type, String field) throws IOException {
				String[] tokens = tokenise();
				String[] tags = getTags(type);

				if((tags != null && (tags.length != tokens.length && tags.length != 1))) {
					throw new IOException("Number of tags must be singular or equal to the number of search terms");
				}
				ArrayList<SpanQuery> terms = new ArrayList<SpanQuery>();
				for(int i = 0; i < tokens.length; i++) {
					if(tokens[i].equals("*") || tokens[i].contains("?") || tokens[i].contains("*")){
						WildcardQuery wildcard = new WildcardQuery(new Term(field, tokens[i]));
						SpanQuery sq = new SpanMultiTermQueryWrapper<WildcardQuery>(wildcard);
						terms.add(sq);
					}
					else{
						String tag = (tags.length > 1) ? tags[i] : tags[0];
						if(tag.equals(TAG_WILDCARD)){
							terms.add(new SpanTermQuery(new Term(field, tokens[i])));
						}
						else{
							byte[] tagBytes = tag.getBytes();
							BytesRef bPayload = new BytesRef(tagBytes);
							ArrayList<byte[]> payload = new ArrayList<byte[]>();
							payload.add(tagBytes);
				    		if(!isWildcardTag(tag)){
								terms.add(isBoostField(field) ? new SemanticPayloadTermQuery(new Term(field, tokens[i]), new SemanticPayloadTermFunction(),true, bPayload) : new SpanPayloadCheckQuery(new SpanTermQuery(new Term(field, tokens[i])), payload));
				    		}
				    		else{
				    			
				    			terms.addAll(getWildcardTagQueries(field, tag, tokens[i]));
				    		}
						}
					}
				}
				return terms.toArray(new SpanQuery[terms.size()]);
			}
			
			public BytesRef getPoSTagPayload() {
				return new BytesRef(getParam(POSTAG).getBytes());
			}
			
			public BytesRef getChunkPayload() {
				return new BytesRef(getParam(CHUNK).getBytes());
			}
			
			
			public String getPoSField() {
				return POSTAG_FIELD;
			}
			
			public String getChunkField() {
				return CHUNK_FIELD;
			}
			
			public String getPoSType(){
				return POSTAG;
			}
			
			public String getChunkType(){
				return CHUNK;
			}
			
			public String[] tokenise() {
				String qstr = getParam(QueryParsing.V);
				String[] tokens = qstr.split(TOK_DELIM);
				if(tokens.length == 1){
					tokens = qstr.split(" ");
				}
				return tokens;
			}
			
			public boolean isBoostField(String field){
				if(getParam(BOOST_FIELD) == null){
					return false;
				}
				return getParam(BOOST_FIELD).equals(field);
			}
			
			public BooleanQuery addFieldQuery(Query query){
				Map<String,SchemaField> fields = req.getSchema().getFields();
				BooleanQuery boolQuery = new BooleanQuery();
				for(String fieldName : fields.keySet()){
					String value = getParam(fieldName);
					if(value != null && !value.equals(CHUNK_FIELD) && !value.equals(POSTAG_FIELD)){
						String qstr = fieldName + ":" + value;
						try {
							boolQuery.add(new LuceneQParserPlugin().createParser(qstr, localParams, params, req).parse(), BooleanClause.Occur.MUST);
						} catch (SyntaxError e) {
							e.printStackTrace();
						}
					}
				}
				boolQuery.add(query, BooleanClause.Occur.MUST);
				return boolQuery;
			}
		}
	}
	
}
