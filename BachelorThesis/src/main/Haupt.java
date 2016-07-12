package main;

import edgeAlgorithms.Greedy;
import edgeAlgorithms.LocalSearchGreedy;
import edgeAlgorithms.RandomSearchGreedy;
import graphReader.GraphReader;
import graphs.Graph;
import graphs.SimpleGraph;

/**
 * The main programm when the comparison between the
 * algorithms is performed. 
 * @author Stephanie Heyderich
 */
public class Haupt {
	public static void main(String[] args){
		
		GraphReader gr = new GraphReader("r250.5.col.txt");
		Graph g = gr.buildGraphFromFile();
		RandomSearchGreedy gre = new RandomSearchGreedy();
		gre.applyAlgorithmComplete(g);
		System.out.println(((SimpleGraph)g).toString());
		System.out.println(g.getQuantityColors());
	}
}
