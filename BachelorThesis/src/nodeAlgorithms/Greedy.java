package nodeAlgorithms;

import java.util.List;
import java.util.Stack;

import edgeAlgorithms.ColoringAlgorithm;
import exceptions.IllegalGraphTypeException;
import graphs.Graph;
import graphs.LineGraph;

public class Greedy implements ColoringAlgorithm {

	private List<Integer> nodes; 
	private Stack<int[]> steps = new Stack<int[]>();
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
			int[] last = steps.pop();
			g.removeLastStep();
			g.removeNodeColor(last[0]);
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
		steps.push(g.getLastStep());
	}

}
