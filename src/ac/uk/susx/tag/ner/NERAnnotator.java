package ac.uk.susx.tag.ner;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceAccessException;

public class NERAnnotator extends JCasAnnotator_ImplBase {
	
	private SentenceDetectorME splitter;
	private TokenizerME tokeniser;
	private String docText;
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
			
			TokenizerModel tokModel = new TokenizerModel(getContext().getResourceAsStream("EnglishTokenModel"));
			tokeniser = new TokenizerME(tokModel);
			
			TokenNameFinderModel nameModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishPersonModel"));
			personFinder = new NameFinderME(nameModel);
			
			TokenNameFinderModel dateModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishDateModel"));
			dateFinder = new NameFinderME(dateModel);
			
			TokenNameFinderModel timeModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishTimeModel"));
			timeFinder =  new NameFinderME(timeModel);
			
			TokenNameFinderModel locationModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishLocationModel"));
			locationFinder =  new NameFinderME(locationModel);
			
			TokenNameFinderModel percentModel = new TokenNameFinderModel(getContext().getResourceAsStream("EnglishPercentageModel"));
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
		
		docText = document.getDocumentText();
		String[] sentences = splitter.sentDetect(docText);
		
		int begin = 0;
		for(int i = 0; i < sentences.length; i++){
			String[] tokens = tokeniser.tokenize(sentences[i]);
			
			Span[] spans = personFinder.find(tokens);
			int peTracer = begin;
			if(spans.length > 0 && spans != null){
				for(Span span : spans){
					PersonAnnotation pa = new PersonAnnotation(document);
					findEntity(pa, peTracer, span, tokens);
					pa.setPerson(docText.substring(pa.getBegin(), pa.getEnd()));
					pa.addToIndexes();
				}
			}
			
			spans = dateFinder.find(tokens);
			int daTracer = begin;
			if(spans.length > 0 && spans != null){
				for(Span span : spans){
					DateAnnotation da = new DateAnnotation(document);
					findEntity(da, daTracer, span, tokens);
					da.setDate(docText.substring(da.getBegin(), da.getEnd()));
					da.addToIndexes();
				}
			}
			
			spans = timeFinder.find(tokens);
			int tiTracer = begin;
			if(spans.length > 0 && spans != null){
				for(Span span : spans){
					TimeAnnotation ta = new TimeAnnotation(document);
					findEntity(ta, tiTracer, span, tokens);
					ta.setTime(docText.substring(ta.getBegin(), ta.getEnd()));
					ta.addToIndexes();
				}
			}
			
			spans = locationFinder.find(tokens);
			int lTracer = begin;
			if(spans.length > 0 && spans != null){
				for(Span span : spans){
					LocationAnnotation la = new LocationAnnotation(document);
					findEntity(la, lTracer, span, tokens);
					la.setLocation(docText.substring(la.getBegin(), la.getEnd()));
					la.addToIndexes();
				}
			}
			
			spans = percentFinder.find(tokens);
			int percTracer = begin;
			if(spans.length > 0 && spans != null){
				for(Span span : spans){
					PercentAnnotation perca = new PercentAnnotation(document);
					findEntity(perca, percTracer, span, tokens);
					perca.setPercent(docText.substring(perca.getBegin(), perca.getEnd()));
					perca.addToIndexes();
				}
			}
			
			spans = organisationFinder.find(tokens);
			int oTracer = begin;
			if(spans.length > 0 && spans != null){
				for(Span span : spans){
					OrganisationAnnotation orga = new OrganisationAnnotation(document);
					findEntity(orga, oTracer, span, tokens);
					orga.setOrganisation(docText.substring(orga.getBegin(), orga.getEnd()));
					orga.addToIndexes();
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
