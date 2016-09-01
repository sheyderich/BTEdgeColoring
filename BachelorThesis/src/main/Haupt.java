package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edgeAlgorithms.ColoringAlgorithms;
import edgeAlgorithmsConcrete.Greedy;
import edgeAlgorithmsConcrete.LineGraphDegreeDown;
import edgeAlgorithmsConcrete.LineGraphDegreeUp;
import edgeAlgorithmsConcrete.OrderAPISearch;
import edgeAlgorithmsConcrete.OrderRANDOMSearch;
import edgeAlgorithmsConcrete.OrderSWAPSearch;
import edgeAlgorithmsConcrete.TabuSearchRandomStart;
import edgeAlgorithmsConcrete.TabuSearchUnicolorStart;
import graphReader.GraphReader;
import graphs.Graph;

/**
 * The main programm when the comparison between the
 * algorithms is performed. 
 * @author Stephanie Heyderich
 */
public class Haupt {
	
	private final static int ALGORITHM = 7; 
	
	/**
	 * Starts the algorithms for every graph
	 * @param args
	 */
	public static void main(String[] args){
		
		List<Graph> graphs = getGraphs(); 
		for(Graph g: graphs){
			List<ColoringAlgorithms> algs = getAlgorithms(g);
			doAlgorithms(g,algs);
			algs.clear();
		}

	}

	/**
	 * Measures the time and records the results of the algorithm in a file
	 * @param g
	 * @param algs
	 */
	private static void doAlgorithms(Graph g, List<ColoringAlgorithms> algs) {
		String s = new String();
		s += g.getEdgeNumber() + "_" + g.getVertexNumber() + "\n";
		ColoringAlgorithms a = algs.get(ALGORITHM);
		s += (a.getClass() + " ");
		final long timeStart = System.currentTimeMillis();
		a.applyAlgorithmComplete(g);
		final long timeEnd = System.currentTimeMillis();
		long diff = timeEnd - timeStart;
		s += ("time: " + diff + " colors: " + g.getQuantityColors() + " diff: "
				+ (double) ((double) g.getQuantityColors() - (double) g.calculateMaxVertexDegree())
						/ (double) g.calculateMaxVertexDegree()
				+ "\n");
		writeInFile(s);
		System.out.println("wrote " +s);
	}

	/**
	 * Writes the results in a file
	 * @param s
	 */
	private static void writeInFile(String s) {
		File f = new File("Results.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
			String curr; 
			while((curr = reader.readLine()) != null){
				s+= curr + "\n"; 
			}
			curr += "\n";
		} catch(Exception e){
			System.out.println("Sorry");
		}
		try (Writer writer = new BufferedWriter(new FileWriter(f))) {
			writer.write(s);
		} catch(Exception e){
			System.out.println("Sorry");
		}
		
	}

	/**
	 * All the graphs that are used for measurement 
	 * @return
	 */
	private static List<Graph> getGraphs() {
		List<File> fin = new ArrayList<File>(); 
		File f = new File("MeasurementGraphs");		
		File[] files = f.listFiles();
		fin = Arrays.asList(files);
		List<Graph> graphs = new ArrayList<>();
		for(File s: fin){
			GraphReader gr = new GraphReader("MeasurementGraphs/" + s.getName());
			Graph g = gr.buildGraphFromFile();
			graphs.add(g);
		}
		return graphs;
	}

	/**
	 * The Algorithms that are used for comparison
	 * @param g
	 * @return
	 */
	private static List<ColoringAlgorithms> getAlgorithms(Graph g) {
		List<ColoringAlgorithms> fin = new ArrayList<ColoringAlgorithms>(); 
		fin.add(new Greedy(g.getEdges()));
		fin.add(new LineGraphDegreeDown(g)); 
		fin.add(new LineGraphDegreeUp(g));
		fin.add(new OrderAPISearch());
		fin.add(new OrderRANDOMSearch());
		fin.add(new OrderSWAPSearch());
		fin.add(new TabuSearchUnicolorStart());
		fin.add(new TabuSearchRandomStart());
		return fin;
	}
}
