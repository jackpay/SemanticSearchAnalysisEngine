package ac.uk.susx.tag.ner;

import java.io.IOException;

import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.Span;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceAccessException;

public class NERAnnotator extends JCasAnnotator_ImplBase {
	
	private SentenceDetectorME splitter;
	private TokenizerME tokeniser;
	private NameFinderME personFinder;
	private NameFinderME dateFinder;
	private NameFinderME locationFinder;
	private NameFinderME organisationFinder;
	private NameFinderME timeFinder;
	private NameFinderME percentFinder;

	@Override
	public void process(JCas document) throws AnalysisEngineProcessException {
		
		try {
			SentenceModel sentenceModel = new SentenceModel(getContext().getResourceAsStream("EnglishSentenceModel"));
			splitter = new SentenceDetectorME(sentenceModel);
			
			TokenizerModel tokModel = new TokenizerModel(getContext().getResourceAsStream("EnglishTokeniserModel"));
			tokeniser = new TokenizerME(tokModel);
			
			TokenNameFinderModel nameModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishPersonModel"));
			personFinder = new NameFinderME(nameModel);
			
			TokenNameFinderModel dateModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishDateModel"));
			dateFinder = new NameFinderME(dateModel);
			
			TokenNameFinderModel timeModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishTimeModel"));
			timeFinder =  new NameFinderME(timeModel);
			
			TokenNameFinderModel locationModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishLocationModel"));
			locationFinder =  new NameFinderME(locationModel);
			
			TokenNameFinderModel percentModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishPercentModel"));
			percentFinder =  new NameFinderME(percentModel);
			
			TokenNameFinderModel organisationModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishOrganisationModel"));
			organisationFinder =  new NameFinderME(organisationModel);
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
		String[] sentences = splitter.sentDetect(docText);
		
		int begin = 0;
		for(int i = 0; i < sentences.length; i++){
			String[] tokens = tokeniser.tokenize(sentences[i]);
			Span[] spans = personFinder.find(tokens);
			
			
		}

	}

}
