package main;

import algorithms.Greedy;
import graphReader.GraphReader;
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
		Greedy gre = new Greedy(g.getEdges());
		gre.applyAlgorithmComplete(g);
		System.out.println(((SimpleGraph)g).toString());
		System.out.println(g.getQuantityColors());
		System.out.println(g.calculateMaxVertexDegree());
		
	}
}
