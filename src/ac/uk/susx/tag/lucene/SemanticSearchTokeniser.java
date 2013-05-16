package ac.uk.susx.tag.lucene;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.*;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.XMLInputSource;

public class SemanticSearchTokeniser extends Tokenizer {
	
	private Reader input;
	private String descriptorPath;
	private String stringTokenType;
	private FSIterator<AnnotationFS> iterator;
	private TypeAttribute typeAttr;
	private CharTermAttribute termAttr;
	private OffsetAttribute offsetAttr;
	private FeaturePath featurePath;
	private String typeAttributeFeaturePath;
	private PositionIncrementAttribute positionIncrementAttr;

	protected SemanticSearchTokeniser(Reader input, String descriptorPath, String tokenType, String typeAttributeFeaturePath) {
		super(input);
		this.input = input;
		this.descriptorPath = descriptorPath;
		this.stringTokenType = tokenType;
		this.typeAttributeFeaturePath = typeAttributeFeaturePath;
		
	}
	
	private void processText(){
			CAS cas = null;
			try {
				cas = getCAS();
			} catch (CASRuntimeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AnalysisEngineProcessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidXMLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResourceInitializationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CASException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Type type = cas.getTypeSystem().getType(stringTokenType);
			iterator = cas.getAnnotationIndex(type).iterator();
	}
	
	private CAS getCAS() throws CASRuntimeException, IOException, InvalidXMLException, ResourceInitializationException, AnalysisEngineProcessException, CASException{
		URL url = SemanticSearchTokeniser.class.getResource(descriptorPath);
		XMLInputSource xml = new XMLInputSource(url);
		ResourceSpecifier rs =  UIMAFramework.getXMLParser().parseResourceSpecifier(xml);
		AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(rs);
		CAS cas = ae.newCAS();
		cas.setDocumentText(IOUtils.toString(input));
		featurePath = cas.createFeaturePath();
		featurePath.initialize(this.typeAttributeFeaturePath);
		this.positionIncrementAttr = addAttribute(PositionIncrementAttribute.class);
		ae.process(cas);
		ae.destroy();
		return cas;
	}
	
	@Override
	public boolean incrementToken() throws IOException {
		if(iterator == null){
			try {
				processText();
			}
			catch(Exception e){
				throw new IOException(e);
			}
		}
		if (iterator.hasNext()) {
		      AnnotationFS next = iterator.next();
		      termAttr.setEmpty();
		      termAttr.append(next.getCoveredText());
		      termAttr.setLength(next.getCoveredText().length());
		      offsetAttr.setOffset(next.getBegin(), next.getEnd());
		      typeAttr.setType(featurePath.getValueAsString(next));
		      positionIncrementAttr.setPositionIncrement(next.getEnd() - next.getBegin());
		      return true;
		    } else {
		      iterator = null;
		      return false;
		    }
		return false;
	}

}
