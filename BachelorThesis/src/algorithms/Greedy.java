package algorithms;

import java.awt.Point;
import java.util.List;
import java.util.Stack;

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
	private int i = 0; 
	private Stack<int[]> steps = new Stack<int[]>();
	private List<Point> edges;
	
	public Greedy(List<Point> neighbor){
		this.edges = neighbor;
	}
	
	@Override
	public void applyAlgorithmComplete(Graph graph) {
		
		for(; i < edges.size(); i++){
			u = edges.get(i).x;
			v = edges.get(i).y;
			if(!graph.isEdgeColored(u, v)){
				tryColorEdge(graph,u,v);
			}
		}
	}

	@Override
	public void applyAlgorithmStepwise(Graph graph) {
		
		if(!graph.isColored()){
			u = ((Point)edges.get(i)).x;
			v = ((Point)edges.get(i)).y;
			System.out.println(u + " " + v);
			if(!graph.isEdgeColored(u, v)){
				tryColorEdge(graph, u, v);
			}
			i++;
			
		}
	}

	@Override
	public void undoLastColoring(Graph graph) {
		if(!steps.isEmpty()){
				int[] last = steps.pop();
				graph.removeLastStep();
				graph.removeEdgeColor(last[0], last[1]);
				i--;
		}
	}
	
	@Override
	public void resetColoring(Graph graph) {
		graph.uncolor();
		i = 0; 
		steps.clear();
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
		steps.push(graph.getLastStep());
	}
	
}
