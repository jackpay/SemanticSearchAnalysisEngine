package ac.uk.susx.tag.collection;

import org.apache.lucene.search.Collector;
import org.apache.lucene.search.PositiveScoresOnlyCollector;
import org.apache.solr.handler.component.CollectorFactory;
import org.apache.solr.handler.component.CollectorSpec;
import org.apache.solr.search.SolrIndexSearcher;
import org.apache.solr.search.grouping.distributed.command.QueryCommand;

public class PositiveScoreOnlyCollectorFactory extends CollectorFactory{

	public Collector getCollector(SolrIndexSearcher searcher, CollectorSpec spec, QueryCommand cmd, int len, boolean needScores){
		
		return new PositiveScoresOnlyCollector(null);
	}

}
