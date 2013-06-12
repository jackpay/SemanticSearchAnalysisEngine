package ac.uk.susx.tag.query;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.payloads.PayloadTermQuery;
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
public class SemanticQParserPlugin extends QParserPlugin{
	
	private static final String NAME = "semantic";
	
	public SemanticQParserPlugin(){}

	@Override
	public void init(NamedList arg0) {}

	@Override
	public QParser createParser(String qString, SolrParams lpar, SolrParams par,
			SolrQueryRequest sReq) {
		return new SemanticQParser(qString, lpar, par, sReq);
	}
	
	
	public class SemanticQParser extends QParser{
		
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
		    if(sqa.isSemanticQuery()){
		    	System.err.println("IsSemantic!");
		    	BytesRef tag = sqa.getPayload();
		    	System.err.println(tag.toString());
		    	System.err.println(getParam(QueryParsing.V));
		    	
		    	if(!sqa.isPhraseQuery()){
		    		return new SemanticPayloadTermQuery(new Term(defaultField, getParam(QueryParsing.V)), new SemanticPayloadTermFunction(), false, tag);
		    	}
		    	else{
		    		return new SemanticPayloadNearSpanQuery(sqa.getTerms() , sqa.getDistance(), false, tag, defaultField);
		    	}
		    }
		    System.err.println("IsNotSemantic!");
			return new LuceneQParserPlugin().createParser(qstr, localParams, params, req).parse();
		}
		
		public class SemanticQueryAnalyser {
			
			private static final String POSTAG = "postype";
			private static final String POSTAG_FIELD = "postag";
			private static final String CHUNK_FIELD = "chunk";
			private static final String CHUNK = "chunktype";
			private static final String PROX = "phr";
			private static final String DIST = "tdist";
			private static final String IN_CHUNK = "inck";
			
			
			public SemanticQueryAnalyser(){ }
			
			public boolean isSemanticQuery(){
				System.out.println(getParam(POSTAG) != null);
				if(getParam(POSTAG) != null){
					defaultField = POSTAG_FIELD;
				}
				else{
					if(getParam(CHUNK) != null){
						defaultField = CHUNK_FIELD;
					}
				}
				return getParam(POSTAG) != null || getParam(CHUNK) != null;
			}
			
			public boolean isPhraseQuery(){
				if(getParam(PROX) != null){
					if(getParam(PROX).equals("false")){
						return false;
					}
				}
				return true;
			}
			
			public int getDistance(){
				if(getParam(DIST) != null){
					try{
						int dist = Integer.parseInt(getParam(DIST));
						System.err.println(dist);
						return dist;
					}
					catch (Exception e){
						return Integer.MAX_VALUE;
					}
				}
				System.err.println(getParam(DIST) != null + " - is null");
				return Integer.MAX_VALUE;
			}
			
			public SpanQuery[] getTerms(){
				String qstr = getParam(QueryParsing.V);
				TokenizerModel model;
				try {
					model = new TokenizerModel(this.getClass().getResourceAsStream("/entoken.bin"));

					Tokenizer tokeniser = new TokenizerME(model);
					String[] tokens = tokeniser.tokenize(qstr);
					SpanTermQuery[] terms = new SpanTermQuery[tokens.length];
					for(int i = 0; i < terms.length; i++){
						terms[i] = new SpanTermQuery(new Term(defaultField, tokens[i]));
					}
					return terms;
					
				} catch (InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			
			public BytesRef getPayload(){
				if(getParam(POSTAG) != null){
					System.err.println(getParam(POSTAG));
					return new BytesRef(getParam(POSTAG));
				}
				if(getParam(CHUNK) != null){
					return new BytesRef(getParam(CHUNK));
				}
				return null;
			}
		}
	}
	
}
