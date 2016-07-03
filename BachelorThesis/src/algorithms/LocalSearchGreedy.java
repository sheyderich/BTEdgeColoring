package algorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import graphs.Graph;
import graphs.SimpleGraph;

public class LocalSearchGreedy implements EdgeColoringAlgorithm {

	private int numberOfIterations;
	private EdgeColoringAlgorithm sStar;
	private int minimalAmountOfColors;
	private List<Point> solutionOrder;
	private Random rand = new Random();
	private Stack<List<Point>> lastSteps = new Stack<List<Point>>();

	@Override
	public void applyAlgorithmComplete(Graph graph) {

		createStartSolution(graph);		
		int numberOfIterationsWithoutImprovement = 0;
		while (numberOfIterationsWithoutImprovement < 100) {
			
			List<Point> neighbor = createNewOrder(solutionOrder);
			if (isNewOrderBetter(neighbor, graph)) {
				setNewBestSolution(graph,neighbor);
				lastSteps.add(neighbor);
			} else {
				numberOfIterationsWithoutImprovement++;
			}
		}
	}

	@Override
	public void applyAlgorithmStepwise(Graph graph) {
		
		if (numberOfIterations == 0) {
			createStartSolution(graph);
		}else{
			List<Point> neighbor = createNewOrder(solutionOrder);
			if (isNewOrderBetter(neighbor, graph)) {
				setNewBestSolution(graph, neighbor);
				lastSteps.add(neighbor);
			}
		}
		numberOfIterations++;
	}

	@Override
	public void undoLastColoring(Graph graph) {
		if(!lastSteps.isEmpty()){
			lastSteps.pop();
			List<Point> last;
			if(!lastSteps.isEmpty()){
				last = lastSteps.peek();
			}else {
				last = graph.getEdges();
			}
			setNewBestSolution(graph, last);
		}
	}
	
	/**
	 * Creates an initial solution
	 * @param graph
	 */
	private void createStartSolution(Graph graph){
		solutionOrder = graph.getEdges();
		sStar = new Greedy(solutionOrder);
		sStar.applyAlgorithmComplete(graph);
		minimalAmountOfColors = graph.getQuantityColors();
	}
	
	/**
	 * Creates a random new Order of a Point List
	 * based on a given one 
	 * @param old
	 * @return
	 */
	private List<Point> createNewOrder(List<Point> old){
		List<Point> neighbor = new ArrayList<Point>(solutionOrder);
		Collections.shuffle(neighbor, rand);
		return neighbor;
	}
	
	/**
	 * Returns true if new Order leads to more efficient 
	 * coloring
	 * @param neighbor
	 * @param graph
	 * @return
	 */
	private boolean isNewOrderBetter(List<Point> neighbor, Graph graph){
		Greedy greedy = new Greedy(neighbor);
		Graph tmp = new SimpleGraph((SimpleGraph)graph);
		greedy.applyAlgorithmComplete(tmp);
		return tmp.getQuantityColors() < minimalAmountOfColors;

	}
	
	/**
	 * Sets a new best Solution and colors the 
	 * edges of the graph in the corresponding 
	 * colors of the new order
	 * @param graph
	 * @param neighbor
	 */
	private void setNewBestSolution(Graph graph, List<Point> neighbor) {
		sStar = new Greedy(neighbor);
		graph.uncolor();
		sStar.applyAlgorithmComplete(graph);
		minimalAmountOfColors = graph.getQuantityColors();
	}
}
