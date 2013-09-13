package ac.uk.susx.tag.highlight;

import org.apache.lucene.search.highlight.Formatter;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.highlight.SolrFormatter;

import org.apache.solr.highlight.HighlightingPluginBase;

public class SemanticSolrFormatter extends HighlightingPluginBase implements SolrFormatter {

	@Override
	public Formatter getFormatter(String fieldName, SolrParams params) {
		numRequests++;
		params = SolrParams.wrapDefaults(params, defaults);
		return new SemanticFormatter();
	}

	@Override
	public String getDescription() {
		return "SemanticFormatter";
	}

	@Override
	public String getSource() {
		return "Still to confirm";
	}

}
