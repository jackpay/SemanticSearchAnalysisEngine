package ac.uk.susx.tag.topic.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TopicModel {
	
	public void run(final String document, final String jsonLoc) {
		
		ProcessBuilder pb = new ProcessBuilder("/Volumes/LocalDataHD/jp242/anaconda/bin/python","/Volumes/LocalDataHD/jp242/PycharmProjects/GensimTopicModelling/TopicModelling.py", document, jsonLoc);
		File log = new File("/Volumes/BackupHD/Phd-LDA/Experiment-1/log.txt");
		pb.redirectError(log);
		try {
			Process p = pb.start();
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Map<Integer,Double> getOutput(final String jsonLoc) {
		Map<Integer,Double> output = new HashMap<Integer,Double>();
		JSONParser jp = new JSONParser();
		JSONArray arr;
		try {
			arr = (JSONArray) jp.parse(new BufferedReader(new FileReader(new File(jsonLoc))));
			for(Object a : arr) {
				JSONArray cst = (JSONArray) a;
				output.put(new Long((long) cst.get(0)).intValue(),(double) cst.get(1));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
	
	/**
	 * Used for debugging only
	 * @param args
	 */
	public static void main(String[] args) {
		String document = "/Volumes/BackupHD/Phd-LDA/Experiment-1/adrenoleukodystrophy-0.txt";
		String jsonLoc = "/Volumes/BackupHD/Phd-LDA/Experiment-1/test4.json";
		TopicModel mod = new TopicModel();
		mod.run(document, jsonLoc);
		mod.getOutput(jsonLoc);
	}

}
