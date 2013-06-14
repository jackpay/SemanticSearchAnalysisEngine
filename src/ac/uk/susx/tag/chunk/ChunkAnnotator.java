package ac.uk.susx.tag.chunk;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import opennlp.tools.chunker.ChunkerME;
import opennlp.tools.chunker.ChunkerModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
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

public class ChunkAnnotator extends JCasAnnotator_ImplBase {
	
	private static final String CHUNK_START = "B-";
	
	private SentenceDetectorME splitter;
	private TokenizerME tokeniser;
	private POSTaggerME posTagger;
	private ChunkerME chunker;
	

	@Override
	public void process(JCas document) throws AnalysisEngineProcessException {
		try {
			
			SentenceModel sentenceModel = new SentenceModel(getContext().getResourceAsStream("EnglishSentenceModel"));
			splitter = new SentenceDetectorME(sentenceModel);
			
			TokenizerModel tokModel = new TokenizerModel(getContext().getResourceAsStream("EnglishTokeniserModel"));
			tokeniser = new TokenizerME(tokModel);
			
			POSModel posModel = new POSModel(getContext().getResourceAsStream("EnglishPoSTaggerModel"));
			posTagger = new POSTaggerME(posModel);
			
			ChunkerModel chunkModel = new ChunkerModel(getContext().getResourceAsStream("EnglishChunkingModel"));
			chunker = new ChunkerME(chunkModel);
			
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
		for(String sentence : sentences){
			String[] tokens = tokeniser.tokenize(sentence);
			String[] posTags = posTagger.tag(tokens);
			Span[] chunks = chunker.chunkAsSpans(tokens, posTags);
			String[] chunkTags = chunker.chunk(tokens, posTags);

			int tracer = begin;
			for(Span span : chunks){
				ChunkAnnotation annotation = new ChunkAnnotation(document);
				Pattern pattern = Pattern.compile(Pattern.quote(tokens[span.getStart()]));
				Matcher matcher = pattern.matcher(docText);
				if(matcher.find(tracer)){
					annotation.setBegin(matcher.start());
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
				annotation.setEnd(tracer);
				annotation.setChunk(chunkTags[span.getStart()].replace(CHUNK_START, ""));
				annotation.addToIndexes();
			}
			Pattern pattern = Pattern.compile(Pattern.quote(sentence));
			Matcher matcher = pattern.matcher(docText);
			matcher.find();
			begin = matcher.end(); 
		}
	}

}
