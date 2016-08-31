package nodeAlgorithms;

import java.awt.Point;
import java.util.List;
import java.util.Stack;

import edgeAlgorithms.ColoringAlgorithms;
import exceptions.IllegalGraphTypeException;
import graphs.Graph;
import graphs.LineGraph;
import helper.AlgorithmStep;
import helper.ColorEdgeStep;
import helper.ColorNodeStep;

/**
 * A greedy algorithm that colors the nodes of a given
 * graph. It proceeds node by node through the graph and 
 * uses the first color on the node that is valid. 
 * @author Stephanie Heyderich
 */
public class Greedy implements ColoringAlgorithms {

	private List<Integer> nodes; 
	private Stack<AlgorithmStep> steps = new Stack<AlgorithmStep>(); 
	private Stack<AlgorithmStep> stepsOr = new Stack<AlgorithmStep>(); 
	private int index = 0; 
	private LineGraph lg; 
	
	public Greedy(List<Integer> nodeOrder){
		this.nodes = nodeOrder; 
	}
	
	@Override
	public void applyAlgorithmComplete(Graph graph) {
		Graph og = ((LineGraph)graph).getOriginal();
		List<Point> edges = og.getEdges();
		if(graph instanceof LineGraph){
			lg = (LineGraph)graph; 
		}else{
			throw new IllegalGraphTypeException("Only LineGraphs allowed");
		}
		
		for(; index < graph.getVertexNumber(); index++){
			int currentNode = nodes.get(index);
			if(!lg.isNodeColored(currentNode)){
				tryColorNode(currentNode);
			}
			Point edge = edges.get(currentNode);
			og.setEdgeColor(edge.x, edge.y, lg.getNodeColor(currentNode));
			stepsOr.add(new ColorEdgeStep(edge.x, edge.y));
		}
	}

	@Override
	public void applyAlgorithmStepwise(Graph graph) {
		Graph og = ((LineGraph)graph).getOriginal();
		List<Point> edges = og.getEdges();
		if(graph instanceof LineGraph){
			lg = (LineGraph)graph; 
		}else{
			throw new IllegalGraphTypeException("Only LineGraphs allowed");
		}
		if(!lg.isColored()){
			int currentNode = nodes.get(index);
			if(!lg.isNodeColored(currentNode)){
				tryColorNode(currentNode);
			}
			Point edge = edges.get(currentNode);
			og.setEdgeColor(edge.x, edge.y, lg.getNodeColor(currentNode));
			stepsOr.add(new ColorEdgeStep(edge.x, edge.y));
			index++;
		}
	}

	@Override
	public void undoLastColoring(Graph graph) {
		Graph og = ((LineGraph)graph).getOriginal();
		if(graph instanceof LineGraph){
			lg = (LineGraph)graph; 
		}else{
			throw new IllegalGraphTypeException("Only LineGraphs allowed");
		}
		
		if(!steps.isEmpty()){
			AlgorithmStep last = steps.pop(); 
			AlgorithmStep lastOr = stepsOr.pop();
			og.removeLastStep();
			lg.removeLastStep();
			lastOr.undo(og);
			last.undo(graph);
			index--;
		}
	}

	@Override
	public void resetColoring(Graph graph) {

		if(graph instanceof LineGraph){
			lg = (LineGraph)graph; 
		}else{
			throw new IllegalGraphTypeException("Only LineGraphs allowed");
		}
		
		lg.uncolor();
		graph.uncolor();
		index = 0; 
		steps.clear();
	}
	
	/**
	 * Colors the given node in the first colors that does
	 * not break the rules of node coloring. 
	 * @param node
	 */
	private void tryColorNode(int node) {
		int color = 1; 
		do{
			lg.setNodeColor(node, color);
			color++;
		}while(!lg.isNodeColoringValid(node));
		lg.setLastStep(node);
		steps.push(new ColorNodeStep(node));
	}
	
	/**
	 * Returns the stack with the steps the algorithm
	 * has taken so far
	 * @return
	 */
	public Stack<AlgorithmStep> getSteps() {
		return steps;
	}

}
