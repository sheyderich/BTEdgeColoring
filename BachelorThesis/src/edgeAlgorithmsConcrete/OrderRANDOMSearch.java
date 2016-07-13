package edgeAlgorithmsConcrete;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edgeAlgorithms.OrderBasedAlgorithms;


/**
 * A radomized algorithm that uses random orders of the given edges in a 
 * graph to use the greedy algorithm as implemented in {@code Greedy.java#}
 * In each iteration a new oder is created and if the resulting chromatic
 * index of the greedy solution is smaller than the current best solution
 * (Start solution generated with greedy algorithm and order of edges) the 
 * new solution is set. 100 iterations without improvements are done before
 * the algorithm stops. It also ends if the lower bound of the chromatic 
 * index is reached.
 * 
 * @author Stephanie Heyderich
 */
public class OrderRANDOMSearch extends OrderBasedAlgorithms {
	
	/**
	 * Creates a random new Order of a Point List
	 * based on a given one 
	 * @param old
	 * @return
	 */
	protected List<Point> createNewOrder(List<Point> old){
		List<Point> neighbor = new ArrayList<Point>(solutionOrder);
		Collections.shuffle(neighbor, rand);
		return neighbor;
	}
	

}
