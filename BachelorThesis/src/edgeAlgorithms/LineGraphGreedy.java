package edgeAlgorithms;

import java.util.ArrayList;
import java.util.List;

import controller.GraphPanelViewController;
import graphs.Graph;

public class LineGraphGreedy extends LineGraphAlgorithms {

	private nodeAlgorithms.Greedy greedy; 
	
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
	
	

}
