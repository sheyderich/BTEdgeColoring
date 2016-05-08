package main;

import algorithms.AlgorithmKoenig;
import graphReader.GraphReader;
import graphs.BipartiteGraph;
import graphs.Graph;
import graphs.SimpleGraph;

/**
 * The main programm when the comparison between the
 * algorithms is performed. 
 * @author Stephanie Heyderich
 * @version 06.05.2016
 */
public class Haupt {
	public static void main(String[] args){
		
		GraphReader gr = new GraphReader(args[0]);
		Graph g = gr.buildGraphFromFile();
		AlgorithmKoenig ak = new AlgorithmKoenig();
		ak.applyAlgorithmComplete((BipartiteGraph) g);
		System.out.println(((SimpleGraph)g).toString());
		
		
	}
}
