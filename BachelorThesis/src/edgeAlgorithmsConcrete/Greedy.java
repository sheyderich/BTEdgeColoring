package edgeAlgorithmsConcrete;

import java.awt.Point;
import java.util.List;
import java.util.Stack;

import edgeAlgorithms.ColoringAlgorithms;
import graphs.Graph;
import helper.AlgorithmStep;
import helper.ColorEdgeStep;

/**
 * A greedy algorithm that colors the graph with at most 1 color
 * more than is necessary by coloring the edges iterative with 
 * the first color that is available. It is given a specific order
 * in which it colors the edges which influences the result. 
 * @author Stephanie Heyderich
 */
public class Greedy implements ColoringAlgorithms {

	private int u = 0; 
	private int v = 0;
	private int i = 0; 
	private Stack<AlgorithmStep> steps = new Stack<AlgorithmStep>();
	private List<Point> edges;
	
	public Greedy(List<Point> edgeOrder){
		this.edges = edgeOrder;
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
			if(!graph.isEdgeColored(u, v)){
				tryColorEdge(graph, u, v);
			}
			i++;
			
		}
	}

	@Override
	public void undoLastColoring(Graph graph) {
		if(!steps.isEmpty()){
				AlgorithmStep last = steps.pop(); 
				graph.removeLastStep();
				last.undo(graph);
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
		steps.push(new ColorEdgeStep(u,v));
	}
	
}
