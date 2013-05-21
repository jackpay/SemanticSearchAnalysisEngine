package ac.uk.susx.tag.postag;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;

public class PoSTagAnnotator extends JCasAnnotator_ImplBase {
	
	private TokenizerME tokeniser;
	private POSTaggerME posTagger;

	@Override
	public void process(JCas document) throws AnalysisEngineProcessException {
		
		try {
			TokenizerModel tokModel = new TokenizerModel(getContext().getResourceAsStream("EnglishTokeniserModel"));
			tokeniser = new TokenizerME(tokModel);
			
			POSModel posModel = new POSModel(getContext().getResourceAsStream("EnglishPoSTaggerModel"));
			posTagger = new POSTaggerME(posModel);
		} catch (ResourceAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String docText = document.getDocumentText();
		System.err.println(docText);
		String[] tokens = tokeniser.tokenize(docText);
		String[] tags = posTagger.tag(tokens);
		
		int begin = 0;
		for(int i = 0; i < tokens.length; i++){
			Pattern pattern = Pattern.compile(Pattern.quote(tokens[i]));
			Matcher matcher = pattern.matcher(docText);
			if(matcher.find(begin)){
				PoSTag annotation = new PoSTag(document);
				TokenAnnotation tannotation = new TokenAnnotation(document);
				annotation.setBegin(matcher.start());
				annotation.setEnd(matcher.end());
				tannotation.setBegin(matcher.start());
				tannotation.setEnd(matcher.end());
				annotation.setPostag(tags[i] + '-' + tokens[i]);
				tannotation.setToken(tokens[i]);
				annotation.setToken(tokens[i]);
				//tannotation.addToIndexes();
				annotation.addToIndexes();
			}
			begin = matcher.end();
		}
	}

}
