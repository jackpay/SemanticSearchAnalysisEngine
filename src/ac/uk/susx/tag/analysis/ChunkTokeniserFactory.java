package ac.uk.susx.tag.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

public class ChunkTokeniserFactory extends TokenizerFactory{

	@Override
	public Tokenizer create(Reader input) {
		return new SemanticSearchTokeniser(input, "/desc/ChunkAnnotatorDescriptor.xml", "ac.uk.susx.tag.chunk.ChunkAnnotation", "chunk");
	}
}
