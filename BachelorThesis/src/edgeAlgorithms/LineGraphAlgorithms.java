package edgeAlgorithms;

import java.awt.Point;
import java.util.List;

import controller.GraphPanelViewController;
import exceptions.InvalidColorException;
import graphs.Graph;
import graphs.LineGraph;

public abstract class LineGraphAlgorithms implements ColoringAlgorithm {
	
	protected LineGraph lg; 
	private GraphPanelViewController controller;
	boolean init;  
	
	/**
	 * Constructor for usage without the MVC pattern
	 * when no visualization is necessary
	 */
	public LineGraphAlgorithms(Graph g){
		controller = null; 
		lg = LineGraph.convertToLineGraph(g);
		init = true; 
	}
	
	public LineGraphAlgorithms(GraphPanelViewController graphPanelViewController, Graph g) {
		controller = graphPanelViewController; 
		lg = LineGraph.convertToLineGraph(g);
		init = true; 
	}

	@Override
	public void applyAlgorithmComplete(Graph graph) {
		while(!lg.isColored()){
			applyAlgorithm();
		}
		colorOriginal();
		if(controller!=null){
			controller.setModel(((graphs.DrawableGraph)lg.getOriginal()));
		}
		
	}

	@Override
	public void applyAlgorithmStepwise(Graph graph) {
		if(init){
			controller.setModel((graphs.DrawableGraph)lg);
			controller.setLineGraphAlgorithm(this);
			init = false; 
		} else if (lg.isColored()){
			colorOriginal();
			controller.setModel(((graphs.DrawableGraph)lg.getOriginal()));
		} else {
			applyAlgorithm();
		}
	}

	@Override
	public void undoLastColoring(Graph graph) {
		undoAlgorithm();
	}

	@Override
	public void resetColoring(Graph graph) {
		if(controller!=null){
			controller.setModel(((graphs.DrawableGraph)lg.getOriginal()));
		}
		graph.uncolor();
		resetAlgorithm(); 
		init = true; 
	}
	
	/**
	 * Applys the coloring determined by the Line Graph to 
	 * the original graph
	 */
	private void colorOriginal() {
		int[] colors = lg.getNodeColors();
		Graph original = lg.getOriginal();
		List<Point> edges = original.getEdges();
		int i = 0; 
		for(Point p: edges){
			original.setEdgeColor(p.x, p.y, colors[i]);
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
	
	public Graph getLineGraph(){
		return this.lg; 
	}
	
	public Graph getOriginalGraph(){
		return this.lg.getOriginal(); 
	}

}
