package edgeAlgorithmsConcrete;

import edgeAlgorithms.Improvement;
import graphs.Graph;

/**
 * This tabu search starts with a unicolor first coloring, where
 * every edge is colored in the color 1 (dark red) 
 * @author Stephanie Heyderich
 */
public class ImprovementUnicolorStart extends Improvement {

	@Override
	public void generateStartingSolution(Graph graph) {
		for(int u = 0; u < graph.getVertexNumber(); u++){
			for(int v = u+1; v < graph.getVertexNumber(); v++){
				if(graph.isEdgeExistent(u, v)){
					graph.setEdgeColor(u, v, 1);
					graph.setLastStep(u, v);
				}
			}
		}
		best = 1;
	}

}
