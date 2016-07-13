package edgeAlgorithmsConcrete;

import java.util.Random;

import edgeAlgorithms.TabuSearch;
import graphs.Graph;

/**
 * This tabu search starts with a random first coloring, where
 * every edge is colored in a color from 1 - m (number of edges) 
 * according to a Random generator. 
 * @author Stephanie Heyderich
 */
public class TabuSearchRandomStart extends TabuSearch {

	@Override
	public void generateStartingSolution(Graph graph) {
		Random rand = new Random(); 
		for(int u = 0; u < graph.getVertexNumber(); u++){
			for(int v = u+1; v < graph.getVertexNumber(); v++){
				if(graph.isEdgeExistent(u, v)){
					if(graph.isEdgeExistent(u, v)){
						graph.setEdgeColor(u, v, rand.nextInt(graph.getEdgeNumber())+1);
						graph.setLastStep(u, v);
					}
				}
			}
		}
		best = graph.getQuantityColors();
	}

}
