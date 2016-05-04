package main;

//import algorithms.AlgorithmKoenig;
import exceptions.EdgeNotColoredException;
import exceptions.EdgeNotFoundException;
import graphReader.GraphReader;
//import graphs.BipartiteGraph;
import graphs.Graph;
import graphs.SimpleGraph;


public class Haupt {
	public static void main(String[] args){
		
		GraphReader gr = new GraphReader(args[0]);
		Graph g = gr.buildGraphFromFile();
//		System.out.println(g);
		try {
//			AlgorithmKoenig.applyKoenigAlgorithm((BipartiteGraph)g);
		} catch (EdgeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EdgeNotColoredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(((SimpleGraph)g).toString());
		
		
	}
}
