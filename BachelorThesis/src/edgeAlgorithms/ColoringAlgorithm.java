package edgeAlgorithms;

import graphs.Graph;

/**
 * Interface that describes methods an Edge Coloring
 * Algorithm should provide. It needs to be possible to 
 * proceed through the algorithm completely for the comparison 
 * or stepwise for the visualization. 
 * 
 * @author Stephanie Heyderich
 */
public interface ColoringAlgorithm {
	
	/**
	 * Proceed completely through the algorithm 
	 * on the given graph
	 * @param graph
	 */
	public void applyAlgorithmComplete(Graph graph);
	
	/**
	 * Proceed stepwise through the algorithm 
	 * on the given graph
	 * @param graph
	 */
	public void applyAlgorithmStepwise(Graph graph);
	
	/**
	 * Revokes the last step that was done by the algorithm
	 * @param graph
	 */
	public void undoLastColoring(Graph graph);
	
	/**
	 * Resets the whole coloring
	 * @param graph
	 */
	public void resetColoring(Graph graph);
	
	
}
