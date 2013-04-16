package ac.uk.susx.tag.sentann;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;


public class SentenceAnnotator extends JCasAnnotator_ImplBase {
	
	private SentenceDetectorME splitter;

	@Override
	public void process(JCas document) throws AnalysisEngineProcessException {
		try {
			SentenceModel sentenceModel = new SentenceModel(getContext().getResourceAsStream("EnglishSentenceModel"));
			splitter = new SentenceDetectorME(sentenceModel);
			
		} catch (ResourceAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String docText = document.getDocumentText();
		String[] sentences = splitter.sentDetect(docText);
		
		int begin = 0;
		for(int i = 0; i < sentences.length; i++){
			Pattern pattern = Pattern.compile(Pattern.quote(sentences[i]));
			Matcher matcher = pattern.matcher(docText);
			if(matcher.find(begin)){
				SentenceAnnotation annotation = new SentenceAnnotation(document);
				annotation.setBegin(matcher.start());
				annotation.setEnd(matcher.end());
				annotation.setSentenceAnnotation(sentences[i]);
				annotation.addToIndexes();
			}
			begin = matcher.end();
		}

	}

}
