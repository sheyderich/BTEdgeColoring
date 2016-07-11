package helper;

import java.util.List;

import edgeAlgorithms.Koenig;
import graphs.Graph;

/**
 * Helps keeping track of augmented path during König's algorithm and 
 * switches them back to their original order if the undo-button is pressed
 * @author Stephanie Heyderich
 */
public class PathSwitchingStep implements AlgorithmStep {
	
	private int color1; 
	private int color2; 
	List<Integer> path; 
	
	public PathSwitchingStep(int color1, int color2, List<Integer> path){
		this.color1 = color1; 
		this.color2 = color2; 
		this.path = path; 
	}

	@Override
	public void undo(Graph g) {
		Koenig.switchColorOnPath(g, path, color1, color2);
	}

}
