package helper;

import graphs.Graph;

/**
 * A step in the algorithm that colors an edge
 * @author Stephanie Heyderich
 */
public class ColorEdgeStep implements AlgorithmStep {

	private int nodeA; 
	private int nodeB;
	
	public ColorEdgeStep(int nA, int nB){
		nodeA = nA; 
		nodeB = nB; 
	}
	
	@Override
	public void undo(Graph g) {
		g.removeEdgeColor(nodeA, nodeB);
	}

}
