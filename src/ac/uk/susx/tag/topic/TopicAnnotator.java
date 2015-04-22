package ac.uk.susx.tag.topic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;

import ac.uk.susx.tag.topic.model.TopicModel;

public class TopicAnnotator extends JCasAnnotator_ImplBase {
	
	private String workingDir = "/Volumes/LocalDataHD/jp242/Documents/Projects/topic-solr/tmp/";

	@Override
	public void process(JCas document) throws AnalysisEngineProcessException {
		
		TopicModel tm = new TopicModel();
		String docText = document.getDocumentText();
		try {
			String fileName = workingDir + "doc-" + Calendar.getInstance().get(Calendar.MILLISECOND);
			File tmp = File.createTempFile(fileName, ".tmp");
			BufferedWriter bw = new BufferedWriter(new FileWriter(tmp));
			bw.write(docText);
			bw.close();
			tmp.deleteOnExit();
			
			tm.run(tmp.getAbsolutePath(), fileName + ".json");
			Map<Integer,Double> out = tm.getOutput(fileName + ".json");
			for(int i : out.keySet()) {
				TopicAnnotation ta = new TopicAnnotation(document);
				ta.setBegin(i);
				ta.setEnd(i);
				ta.setTopicAnnotation(out.get(i));
				ta.addToIndexes();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
