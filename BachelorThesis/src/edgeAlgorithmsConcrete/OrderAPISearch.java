package edgeAlgorithmsConcrete;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edgeAlgorithms.OrderBasedAlgorithms;

/**
 * Creates all neighbors of one list by taking the API-Neighborhood 
 * (every order that can be reached by switching the position of 
 * two neighboring elements in the first order). 
 * @author Stephanie Heyderich
 */
public class OrderAPISearch extends OrderBasedAlgorithms {

	@Override
	protected List<Point> createNewOrder(List<Point> old) {
		List<List<Point>> neighbors = getNeighbors(old);
		List<Point> best = getBest(neighbors);
		return best;
	}

	/**
	 * Creates all neighbouring orders
	 * @param old
	 * @return
	 */
	private List<List<Point>> getNeighbors(List<Point> old) {
		List<List<Point>> neighbors = new ArrayList<List<Point>>(); 
		for(int i = 0; i < old.size()-1; i++){
			int j = i+1; 
			List<Point> neighbor = new ArrayList<Point>(old);
			Collections.swap(neighbor, i, j);
			neighbors.add(neighbor);
		}
		List<Point> neighbor = new ArrayList<Point>(old);
		Collections.swap(neighbor, old.size()-1, 0);
		neighbors.add(neighbor);
		return neighbors;
	}
	
	/**
	 * Evaluates the given orders and returns the best
	 * @param neighbors
	 * @return
	 */
	private List<Point> getBest(List<List<Point>> neighbors) {
		List<Point> best = neighbors.get(0);
		int nrColors = getNumberColors(best);
		int tmp; 
		for(List<Point> n : neighbors){
			if((tmp = getNumberColors(n)) < nrColors){
				nrColors = tmp; 
				best = n; 
			}
		}
		return best;
	}
	
	private int getNumberColors(List<Point> order){
		Greedy g = new Greedy(order);
		g.applyAlgorithmComplete(graph);
		return graph.getQuantityColors();
	}

}
