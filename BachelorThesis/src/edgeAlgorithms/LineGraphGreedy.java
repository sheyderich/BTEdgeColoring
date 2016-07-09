package edgeAlgorithms;

import controller.GraphPanelViewController;
import graphs.Graph;

public class LineGraphGreedy extends LineGraphAlgorithms {

	private Greedy greedy; 
	
	public LineGraphGreedy(GraphPanelViewController graphPanelViewController, Graph g) {
		super(graphPanelViewController, g);
		greedy = new Greedy(lg.getEdges());
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
	
	

}
