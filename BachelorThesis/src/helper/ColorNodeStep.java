package helper;

import exceptions.IllegalGraphTypeException;
import graphs.Graph;
import graphs.LineGraph;

/**
 * An algorithm step that defines a node coloring. 
 * @author Stephanie Heyderich
 */
public class ColorNodeStep implements AlgorithmStep {

	private int node; 
	
	public ColorNodeStep(int n){
		node = n; 
	}

	@Override
	public void undo(Graph g) {
		if(g instanceof LineGraph){
			((LineGraph) g).removeNodeColor(node);
		}else{
			throw new IllegalGraphTypeException("Only Line Graphs are permitted");
		}
	}

}
