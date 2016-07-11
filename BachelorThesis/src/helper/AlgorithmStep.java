package helper;

import graphs.Graph;

/**
 * An interface that saves the information of an algorithm step
 * to undo them. 
 * @author Stephanie Heyderich
 */
public interface AlgorithmStep {
	
	/**
	 * Resets the algorithm step in the given graph. 
	 * @param g
	 */
	public void undo(Graph g);
}
