package edgeAlgorithms;

import java.awt.Point;
import java.util.List;

import controller.GraphPanelViewController;
import exceptions.InvalidColorException;
import graphs.Graph;
import graphs.LineGraph;


/**
 * Describes a set of algorithms that work on the line graph
 * of a graph to color the nodes instead of the edges and later
 * reverse this to get a valid edge coloring. 
 * @author Stephanie Heyderich
 */
public abstract class LineGraphAlgorithms implements ColoringAlgorithm {
	
	protected LineGraph lg; 
	private GraphPanelViewController controller;
	boolean init;  
	boolean notfinished;
	
	/**
	 * Constructor for usage without the MVC pattern
	 * when no visualization is necessary
	 */
	public LineGraphAlgorithms(Graph g){
		controller = null; 
		lg = LineGraph.convertToLineGraph(g);
		init = true; 
		notfinished = true; 
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
		notfinished = true; 
	}

	@Override
	public void applyAlgorithmComplete(Graph graph) {
		if(notfinished){
			while(!lg.isColored()){
				applyAlgorithm();
			}
			colorOriginal();
			if(controller!=null){
				controller.setModelLineGraph(((graphs.DrawableGraph)lg.getOriginal()));
			}
			notfinished = false;
		}
		
	}

	@Override
	public void applyAlgorithmStepwise(Graph graph) {
		if(notfinished){
			if(init){
				controller.setModelLineGraph((graphs.DrawableGraph)lg);
				init = false; 
			} else if (lg.isColored()){
				colorOriginal();
				controller.setModelLineGraph(((graphs.DrawableGraph)lg.getOriginal()));
				notfinished = false; 
			} else {
				applyAlgorithm();
			}
		}
	}

	@Override
	public void undoLastColoring(Graph graph) {
		undoAlgorithm();
	}

	@Override
	public void resetColoring(Graph graph) {
		if(controller!=null){
			controller.setModelLineGraph(((graphs.DrawableGraph)lg.getOriginal()));
		}
		graph.uncolor();
		lg.uncolor();
		resetAlgorithm(); 
		notfinished = true; 
		init = true; 
	}
	
	/**
	 * Applies the coloring determined by the Line Graph to 
	 * the original graph
	 */
	private void colorOriginal() {
		int[] colors = lg.getNodeColors();
		Graph original = lg.getOriginal();
		List<Point> edges = original.getEdges();
		int i = 0; 
		for(Point p: edges){
			original.setEdgeColor(p.x, p.y, colors[i]);
			original.setLastStep(p.x, p.y);
			i++;
		}
		if(!original.isGraphColoringValid()) throw new InvalidColorException("Conversion from Line Graph to Original graph failed at coloring");
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
