package ac.uk.susx.tag.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.analysis.util.TokenizerFactory;

public class PoSTagTokeniserFactory extends TokenizerFactory{

	@Override
	public Tokenizer create(Reader input) {
		return new SemanticSearchTokeniser(input, "/desc/PoSTagAnnotatorDescriptor.xml", "ac.uk.susx.tag.postag.PoSTag", "postag");
	}
}
