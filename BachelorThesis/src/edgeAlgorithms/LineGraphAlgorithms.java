package edgeAlgorithms;

import java.util.Stack;

import controller.GraphPanelViewController;
import graphs.Graph;
import graphs.LineGraph;
import helper.AlgorithmStep;


/**
 * Describes a set of algorithms that work on the line graph
 * of a graph to color the nodes instead of the edges and later
 * reverse this to get a valid edge coloring. 
 * @author Stephanie Heyderich
 */
public abstract class LineGraphAlgorithms implements ColoringAlgorithms {
	
	protected LineGraph lg; 
	private GraphPanelViewController controller;
	boolean init;  
	boolean finished;
	
	/**
	 * Constructor for usage without the MVC pattern
	 * when no visualization is necessary
	 */
	public LineGraphAlgorithms(Graph g){
		controller = null; 
		lg = LineGraph.convertToLineGraph(g);
		init = true; 
		finished = false; 
	}
	
	/**
	 * Constructor for usage with the MVC pattern
	 * when visualization is required
	 * @param graphPanelViewController
	 * @param g
	 */
	public LineGraphAlgorithms(GraphPanelViewController graphPanelViewController, Graph g) {
		controller = graphPanelViewController; 
		lg = LineGraph.convertToLineGraph(g);
		init = true; 
		finished = false; 
	}

	@Override
	public void applyAlgorithmComplete(Graph graph) {
		if(!finished){
			while(!lg.isColored()){
				applyAlgorithm();
			}
			if(controller!=null){
				controller.setModelLineGraph((lg.getOriginal()));
			}
			finished = true;
		}
		
	}

	@Override
	public void applyAlgorithmStepwise(Graph graph) {
		if(!finished){
			if(init){
				controller.setModelLineGraph(lg);
				init = false; 
			} else if (lg.isColored()){
				controller.setModelLineGraph((lg.getOriginal()));
				finished = true; 
			} else {
				applyAlgorithm();
			}
		}
	}

	@Override
	public void undoLastColoring(Graph graph) {
		if(finished){
			controller.setModelLineGraph(lg);
			finished = false; 
		}else if(getSteps().isEmpty() && !init){
			lg.getOriginal().uncolor();
			controller.setModelLineGraph((lg.getOriginal()));
			init = true; 
		}else{
			undoAlgorithm();
		}
	}

	@Override
	public void resetColoring(Graph graph) {
		if(controller!=null){
			controller.setModelLineGraph((lg.getOriginal()));
		}
		graph.uncolor();
		lg.uncolor();
		resetAlgorithm(); 
		finished = false; 
		init = true; 
	}
	
	/**
	 * Here the desired algorithm can be implemented
	 */
	protected abstract void applyAlgorithm();
	
	/**
	 * Here the desired algorithm is undone stepwise
	 */
	protected abstract void undoAlgorithm();
	
	/**
	 * resets the desired algorithm
	 */
	protected abstract void resetAlgorithm();
	
	/**
	 * Delivers the steps that were taken during the algorithm
	 * for the undone button
	 * @return
	 */
	protected abstract Stack<AlgorithmStep> getSteps();
	
	/**
	 * Returns the line graph
	 * @return
	 */
	public Graph getLineGraph(){
		return this.lg; 
	}
	
	/**
	 * Returns the original graph of 
	 * a line graph. 
	 * @return
	 */
	public Graph getOriginalGraph(){
		return this.lg.getOriginal(); 
	}

}
