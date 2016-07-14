package edgeAlgorithmsConcrete;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import controller.GraphPanelViewController;
import edgeAlgorithms.LineGraphAlgorithms;
import graphs.Graph;
import helper.AlgorithmStep;

/**
 * A line graph edge coloring algorithm that uses 
 * a greedy node coloring algorithm as its means
 * of coloring the edges. The order the nodes are colored
 * is set up as a the lexical order of the nodes. 
 *@author Stephanie Heyderich
 */
public class LineGraphGreedy extends LineGraphAlgorithms {

	protected nodeAlgorithms.Greedy greedy; 
	
	/**
	 * Sets up a LlineGraphAlgorithm and determines the order of the 
	 * greedy algorithm by just taking the lexical order of the nodes
	 * for the case when visual representation is not necessary
	 * @param g
	 */
	public LineGraphGreedy(Graph g) {
		super(g);
		List<Integer> nodes = new ArrayList<Integer>();
		for(int i = 0; i < lg.getVertexNumber(); i++){
			nodes.add(new Integer(i));
		}
		greedy = new nodeAlgorithms.Greedy(nodes);
	}
	
	
	/**
	 * Sets up a LlineGraphAlgorithm and determines the order of the 
	 * greedy algorithm by just taking the lexical order of the nodes
	 * @param graphPanelViewController
	 * @param g
	 */
	public LineGraphGreedy(GraphPanelViewController graphPanelViewController, Graph g) {
		super(graphPanelViewController, g);
		List<Integer> nodes = new ArrayList<Integer>();
		for(int i = 0; i < lg.getVertexNumber(); i++){
			nodes.add(new Integer(i));
		}
		greedy = new nodeAlgorithms.Greedy(nodes);
	}

	@Override
	protected void applyAlgorithm() {
		greedy.applyAlgorithmStepwise(lg);
	}

	@Override
	protected void undoAlgorithm() {
		greedy.undoLastColoring(lg);
	}

	@Override
	protected void resetAlgorithm() {
		greedy.resetColoring(lg);
	}

	@Override
	protected Stack<AlgorithmStep> getSteps() {
		return greedy.getSteps(); 
	}

}
