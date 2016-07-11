package nodeAlgorithms;

import java.util.List;
import java.util.Stack;

import edgeAlgorithms.ColoringAlgorithm;
import exceptions.IllegalGraphTypeException;
import graphs.Graph;
import graphs.LineGraph;
import helper.AlgorithmStep;
import helper.ColorNodeStep;

/**
 * A greedy algorithm that colors the nodes of a given
 * graph. It proceeds node by node through the graph and 
 * uses the first color on the node that is valid. 
 * @author Stephanie Heyderich
 */
public class Greedy implements ColoringAlgorithm {

	private List<Integer> nodes; 
	private Stack<AlgorithmStep> steps = new Stack<AlgorithmStep>(); 
	private int index = 0; 
	private LineGraph g; 
	
	public Greedy(List<Integer> nodeOrder){
		this.nodes = nodeOrder; 
	}
	
	@Override
	public void applyAlgorithmComplete(Graph graph) {
		
		if(graph instanceof LineGraph){
			g = (LineGraph)graph; 
		}else{
			throw new IllegalGraphTypeException("Only LineGraphs allowed");
		}
		
		for(; index < graph.getVertexNumber(); index++){
			int currentNode = nodes.get(index);
			if(!g.isNodeColored(currentNode)){
				tryColorNode(currentNode);
			}
		}
	}

	@Override
	public void applyAlgorithmStepwise(Graph graph) {
		
		if(graph instanceof LineGraph){
			g = (LineGraph)graph; 
		}else{
			throw new IllegalGraphTypeException("Only LineGraphs allowed");
		}
		if(!g.isColored()){
			int currentNode = nodes.get(index);
			if(!g.isNodeColored(currentNode)){
				tryColorNode(currentNode);
			}
			index++;
		}
	}

	@Override
	public void undoLastColoring(Graph graph) {
		
		if(graph instanceof LineGraph){
			g = (LineGraph)graph; 
		}else{
			throw new IllegalGraphTypeException("Only LineGraphs allowed");
		}
		
		if(!steps.isEmpty()){
			AlgorithmStep last = steps.pop(); 
			g.removeLastStep();
			last.undo(graph);
			index--;
		}
	}

	@Override
	public void resetColoring(Graph graph) {
		
		if(graph instanceof LineGraph){
			g = (LineGraph)graph; 
		}else{
			throw new IllegalGraphTypeException("Only LineGraphs allowed");
		}
		
		g.uncolor();
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
			g.setNodeColor(node, color);
			color++;
		}while(!g.isNodeColoringValid(node));
		g.setLastStep(node);
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
