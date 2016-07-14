package edgeAlgorithmsConcrete;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controller.GraphPanelViewController;
import graphs.Graph;

/**
 * Calculates an order where the nodes are sorted decliningly 
 * with their vertex degree, so the greedy algorithm starts with
 * the node with the most neighbors
 * @author Stephanie Heyderich
 */
public class LineGraphDegreeDown extends LineGraphGreedy {
	
	public LineGraphDegreeDown(Graph g) {
		super(g);
		List<Integer> nodes = getNodeOrder();
		greedy = new nodeAlgorithms.Greedy(nodes);
	}
	
	public LineGraphDegreeDown(GraphPanelViewController graphPanelViewController, Graph g) {
		super(graphPanelViewController, g);
		List<Integer> nodes = getNodeOrder();
		greedy = new nodeAlgorithms.Greedy(nodes);
	}

	/**
	 * Returns the nodes ordered according to their 
	 * vertex degrees. 
	 * @return
	 */
	private List<Integer> getNodeOrder() {
		List<Integer> nodes = new ArrayList<Integer>();
		List<Integer> degrees = new ArrayList<Integer>();
		for(int i = 0; i < lg.getVertexNumber(); i++){
			int degree = lg.getNeighbors(i).size();
			degrees.add(degree);
		}

		for(int i = 0; i < lg.getVertexNumber(); i++){
			int biggest = Collections.max(degrees);
			if(biggest!= 0){
				int node = degrees.indexOf(biggest);
				nodes.add(node);
				degrees.set(node, 0);
			}
		}
		return nodes;
	}

}
