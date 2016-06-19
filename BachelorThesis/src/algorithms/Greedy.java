package algorithms;

import java.util.ArrayList;
import java.util.List;

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
	private List<int[]> steps = new ArrayList<int[]>();
	
	
	@Override
	public void applyAlgorithmComplete(Graph graph) {
		for(; u < graph.getVertexNumber(); u++){
			for(; v < graph.getVertexNumber(); v++){
				if(graph.isEdgeExistent(u, v) && !graph.isEdgeColored(u, v)){
					tryColorEdge(graph, u, v);
				}
			}
			v = 0;
		}
	}

	@Override
	public void applyAlgorithmStepwise(Graph graph) {
		if(!graph.isColored()){
			for(; u < graph.getVertexNumber(); u++){
				for(; v < graph.getVertexNumber(); v++){
					if(graph.isEdgeExistent(u, v) && !graph.isEdgeColored(u, v)){
						tryColorEdge(graph, u, v);
						return;
					}
				}
				v = 0;
			}
		}
	}

	@Override
	public void undoLastColoring(Graph graph) {
		if(!steps.isEmpty()){
			
				int[] last = steps.get(steps.size()-1);
				steps.remove(steps.size()-1);
				graph.removeEdgeColor(last[0], last[1]);
				graph.removeLastStep();
				u = last[0];
				v = last[1];
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
		int color = 1;
		do{
			graph.setEdgeColor(u, v, color);
			color++;
		}while(!graph.isEdgeColoringValid(u, v));
		
		graph.setLastStep(u, v);
		steps.add(graph.getLastStep());
	}
	
}
