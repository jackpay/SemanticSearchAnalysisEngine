package ac.uk.susx.tag.analysis;

import java.io.Reader;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;

public class ChunkTokenTokeniserFactory extends TokenizerFactory {

	@Override
	public Tokenizer create(Reader input) {
		return new SemanticSearchPayloadTokeniser(input, "/desc/ChunkTokenAnnotatorDescriptor.xml", "ac.uk.susx.tag.chunk.chunktoken.ChunkTokenAnnotation", "chunk_token");
	}

}
