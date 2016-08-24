package main;

import java.util.ArrayList;
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
import graphs.Graph;

/**
 * The main programm when the comparison between the
 * algorithms is performed. 
 * @author Stephanie Heyderich
 */
public class Haupt {
	public static void main(String[] args){
		
		List<String> graphs = getGraphs(); 
		
		List<ColoringAlgorithms> algs = getAlgorithms(); 

	}

	private static List<String> getGraphs() {
		List<String> fin = new ArrayList<String>(); 
		// File Namen
		return fin;
	}

	private static List<ColoringAlgorithms> getAlgorithms() {
		List<ColoringAlgorithms> fin = new ArrayList<ColoringAlgorithms>(); 
//		fin.add(new Greedy());
//		fin.add(new LineGraphDegreeDown()); 
//		fin.add(new LineGraphDegreeUp());
		fin.add(new OrderAPISearch());
		fin.add(new OrderRANDOMSearch());
		fin.add(new OrderSWAPSearch());
		fin.add(new TabuSearchUnicolorStart());
		fin.add(new TabuSearchRandomStart());
		return fin;
	}
}
