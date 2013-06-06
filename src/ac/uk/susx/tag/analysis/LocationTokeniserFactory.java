package ac.uk.susx.tag.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

public class LocationTokeniserFactory extends TokenizerFactory{

	@Override
	public Tokenizer create(Reader input) {
		return new SemanticSearchTokeniser(input, "/desc/NERAnnotatorDescriptor.xml", "ac.uk.susx.tag.ner.LocationAnnotation", "location");
	}
}
