package main;

import edgeAlgorithmsConcrete.Greedy;
import edgeAlgorithmsConcrete.OrderAPISearch;
import edgeAlgorithmsConcrete.OrderRANDOMSearch;
import edgeAlgorithmsConcrete.OrderSWAPSearch;
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
		OrderAPISearch gre = new OrderAPISearch();
		gre.applyAlgorithmComplete(g);
		System.out.println(g.getQuantityColors());
	}
}
