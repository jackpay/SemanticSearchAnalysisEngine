package ac.uk.susx.tag.lucene;

import ac.uk.susx.tag.lucene.SemanticSearchTokeniser;
import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeSource.AttributeFactory;

public class PoSTagTokeniserFactory extends TokenizerFactory{

	protected PoSTagTokeniserFactory(Map<String, String> args) {
		super(args);
	}

	@Override
	public Tokenizer create(AttributeFactory arg0, Reader input) {
		return new SemanticSearchTokeniser(input, "/desc/PoSTagAnnotatorDescriptor.xml", "ac.uk.susx.tag.PoSTag", "postag");
	}
	
	

}
