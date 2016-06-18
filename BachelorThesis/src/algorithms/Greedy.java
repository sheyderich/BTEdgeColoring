package algorithms;

import graphs.Graph;

/**
 * A greedy algorithm that colors the graph with at most 1 color
 * more than is necessary by coloring the edges iterative with 
 * the first color that is available.
 * @author Stephanie Heyderich
 * @version 12.05.2016
 */
public class Greedy implements EdgeColoringAlgorithm {

	private int u = 0; 
	private int v = 0;
	
	
	@Override
	public void applyAlgorithmComplete(Graph graph) {
		u = graph.getVertexNumber();
		v = graph.getVertexNumber();
		for(int u = 0; u < graph.getVertexNumber(); u++){
			for(int v = 0; v < graph.getVertexNumber(); v++){
				if(graph.isEdgeExistent(u, v)){
					tryColorEdge(graph, u, v);
				}
			}
		}
	}

	@Override
	public void applyAlgorithmStepwise(Graph graph) {
		if(!graph.isColored()){
			for(; u < graph.getVertexNumber(); u++){
				for(; v < graph.getVertexNumber(); v++){
					if(graph.isEdgeExistent(u, v) && !graph.isEdgeColored(u, v)){
						tryColorEdge(graph, u, v);
						graph.setLastStep(u,v);
						return;
					}
				}
				v = 0;
			}
		}
	}

	@Override
	public void undoLastColoring(Graph graph) {
		if(!graph.isUncolored()){
			for(int i = u; i >= 0; i --){
				for(int j = v; j >= 0; j--){
					if(graph.isEdgeExistent(i, j) && graph.isEdgeColored(i, j)){
						graph.removeEdgeColor(i, j);
						graph.removeLastStep();
						u = i; 
						v = j; 
						return;
					}
					v = graph.getEdgeNumber();
				}
			}
		}
	}

	/**
	 * Colors the given edge with the first color that does not
	 * break the rules of edge coloring
	 * @param graph
	 * @param u
	 * @param v
	 */
	private void tryColorEdge(Graph graph, int u, int v) {
		for(int color = 1; color <= Integer.MAX_VALUE ; color++){
			graph.setEdgeColor(u, v, color);
			if(graph.isEdgeColoringValid(u, v)){
				break;
			}else{
				graph.removeEdgeColor(u, v);
			}
		}
		
	}
	
}
