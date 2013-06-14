package ac.uk.susx.tag.query;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.payloads.PayloadTermQuery;
import org.apache.lucene.search.spans.SpanMultiTermQueryWrapper;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.util.BytesRef;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.common.util.NamedList;
import org.apache.solr.request.SolrQueryRequest;
import org.apache.solr.search.LuceneQParserPlugin;
import org.apache.solr.search.QParser;
import org.apache.solr.search.QParserPlugin;
import org.apache.solr.search.QueryParsing;
import org.apache.solr.search.SolrQueryParser;
import org.apache.solr.search.SyntaxError;
import org.apache.uima.cas.CAS;

import ac.uk.susx.tag.query.payload.SemanticPayloadNearSpanQuery;
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
		    	System.err.println("IsSemantic!");
		    	BytesRef tag = sqa.getPayload();
		    	System.err.println(getParam(QueryParsing.V));
		    	
		    	if(!sqa.isProximityQuery()) {
		    		if(sqa.isChunkandPoS()) {
		    			System.err.println("Boolean Query");
		    			SemanticPayloadTermQuery posQuery = new SemanticPayloadTermQuery(new Term(sqa.getPoSField(), getParam(QueryParsing.V)), new SemanticPayloadTermFunction(), false, sqa.getPoSTagPayload());
		    			SemanticPayloadTermQuery chunkQuery = new SemanticPayloadTermQuery(new Term(sqa.getChunkField(), getParam(QueryParsing.V)), new SemanticPayloadTermFunction(), false, sqa.getChunkPayload());
		    			BooleanQuery boolQuery = new BooleanQuery();
		    			boolQuery.add(chunkQuery, BooleanClause.Occur.MUST);
		    			boolQuery.add(posQuery, BooleanClause.Occur.MUST);
		    			return boolQuery;
		    		}
		    		return new SemanticPayloadTermQuery(new Term(defaultField, getParam(QueryParsing.V)), new SemanticPayloadTermFunction(), false, tag);
		    	}
		    	else{
		    		try {
						return new SemanticPayloadNearSpanQuery(sqa.getSpans() , sqa.getDistance(), false, tag, defaultField);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    }
		    System.err.println("IsNotSemantic!");
			return new LuceneQParserPlugin().createParser(qstr, localParams, params, req).parse();
		}
		
		public class SemanticQueryAnalyser {
			
			// Query parameter prefixes
			private static final String POSTAG = "postype";
			private static final String POSTAG_FIELD = "postag";
			private static final String CHUNK_FIELD = "chunktoken";
			private static final String CHUNK = "chunktype";
			private static final String DIST = "tdist";
			private static final String IN_CHUNK = "inck";
			
			// Used to chain a number of tags (1 per search token) TODO: Add functionality to enable this.
			private final String TAG_DELIM = "\\+";
			
			
			public SemanticQueryAnalyser() { }
			
			public BytesRef getPayload() {
				if(getParam(CHUNK) != null){
					return new BytesRef(getParam(CHUNK));
				}
				return new BytesRef(getParam(POSTAG));
			}

			public boolean isSemanticQuery() {
				System.out.println(getParam(POSTAG) != null);
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
			
			public boolean isInChunk() {
				return getParam(IN_CHUNK) != null;
			}
			
			public boolean isChunkandPoS() {
				return (getParam(POSTAG) != null && getParam(CHUNK) != null);
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
			
			public String[] getTags() {
				if(getParam(POSTAG) != null) {
					return getParam(POSTAG).split(TAG_DELIM);
				}
				return getParam(CHUNK).split(TAG_DELIM);
			}
			
			public SpanQuery[] getSpans() throws IOException {
				String[] tokens = tokenise();
				
				String[] tags = getTags();
				if(tags.length != tokens.length && tags.length != 1) {
					throw new IOException("Number of tags must be singular or equal to the number of search terms");
				}
				
				SpanQuery[] terms = new SpanQuery[tokens.length];
				for(int i = 0; i < terms.length; i++) {
					if(tokens[i].equals("*") || tokens[i].contains("?") || tokens[i].contains("*")){
						WildcardQuery wildcard = new WildcardQuery(new Term(defaultField, tokens[i]));
						SpanQuery sq = new SpanMultiTermQueryWrapper<WildcardQuery>(wildcard);
						terms[i] = sq;
					}
					else{
						String tag = (tags.length > 1) ? tags[i] : tags[0];
						terms[i] = new SemanticPayloadTermQuery(new Term(defaultField, tokens[i]), new SemanticPayloadTermFunction(), true, new BytesRef(tag));
					}
				}
				return terms;
			}
			
			public BytesRef getPoSTagPayload() {
				return new BytesRef(getParam(POSTAG));
			}
			
			public BytesRef getChunkPayload() {
				return new BytesRef(getParam(CHUNK));
			}
			
			public String getPoSField() {
				return POSTAG_FIELD;
			}
			
			public String getChunkField() {
				return CHUNK_FIELD;
			}
			
			public String[] tokenise() {
				String qstr = getParam(QueryParsing.V);
				TokenizerModel model;
				try {
					model = new TokenizerModel(this.getClass().getResourceAsStream("/entoken.bin"));
					Tokenizer tokeniser = new TokenizerME(model);
					String[] tokens = tokeniser.tokenize(qstr);
					return tokens;
					
				} catch (InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}
	}
	
}
