package ac.uk.susx.tag.location;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceAccessException;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

/**
 * Location finder annotator.
 * @author jp242
 *
 */
public class LocationAnnotator extends JCasAnnotator_ImplBase {
	
	private SentenceDetectorME splitter;
	private TokenizerME tokeniser;
	private String docText;
	private NameFinderME locationFinder;
	
	@Override
	/**
	 * Annotates the document object with Location annotations.
	 */
	public void process(JCas document) throws AnalysisEngineProcessException {

		try {
			
			SentenceModel sentenceModel;
			sentenceModel = new SentenceModel(getContext().getResourceAsStream("EnglishSentenceModel"));
			splitter = new SentenceDetectorME(sentenceModel);
			
			TokenizerModel tokModel = new TokenizerModel(getContext().getResourceAsStream("EnglishTokenModel"));
			tokeniser = new TokenizerME(tokModel);
			
			TokenNameFinderModel locationModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishLocationModel"));
			locationFinder =  new NameFinderME(locationModel);
			
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
		docText = document.getDocumentText();
		String[] sentences = splitter.sentDetect(docText);
		
		int begin = 0;
		for(int i = 0; i < sentences.length; i++){
			String[] tokens = tokeniser.tokenize(sentences[i]);
			
			Span[] spans = locationFinder.find(tokens);
			int lTracer = begin;
			if(spans.length > 0 && spans != null){
				for(Span span : spans){
					LocationAnnotation la = new LocationAnnotation(document);
					findEntity(la, lTracer, span, tokens);
					la.setLocation(docText.substring(la.getBegin(), la.getEnd()));
					la.addToIndexes();
				}
			}
			
			Pattern pattern = Pattern.compile(Pattern.quote(sentences[i]));
			Matcher matcher = pattern.matcher(docText);
			matcher.find();
			begin = matcher.end();
			
		}
		
	}
	
	private void findEntity(Annotation an, int tracer, Span span, String[] tokens){
		Pattern pattern = Pattern.compile(Pattern.quote(tokens[span.getStart()]));
		Matcher matcher = pattern.matcher(docText);
		if(matcher.find(tracer)){
			an.setBegin(matcher.start());
			tracer = matcher.end();
		}
		if(span.getStart() < span.getEnd()){
			int it = span.getStart() + 1;
			while(it < span.getEnd()){
				Pattern patt = Pattern.compile(Pattern.quote(tokens[it]));
				Matcher mtchr = patt.matcher(docText);
				if(mtchr.find(tracer)){
					tracer = mtchr.end();
				}
				it++;
			}
		}
		an.setEnd(tracer);
	}

}
