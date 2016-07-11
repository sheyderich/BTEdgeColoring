package edgeAlgorithms;

import java.awt.Point;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import graphs.Graph;
import graphs.SimpleGraph;
import helper.OrderStep;

/**
 * Describes algorithms that use specific orders that are then
 * given to the greedy algorithm to color the edges of a graph. 
 * Only the transformation step from one order to a neighboring one
 * in the next iteration has to be implemented by writing the method
 * createNewOrder()
 * @author Stephanie Heyderich
 */
public abstract class OrderBasedAlgorithms implements ColoringAlgorithm {

	private int numberOfIterations;
	private ColoringAlgorithm sStar;
	private int minimalAmountOfColors;
	protected List<Point> solutionOrder;
	protected Random rand = new Random();
	private Stack<OrderStep> lastSteps = new Stack<OrderStep>();

	@Override
	public void applyAlgorithmComplete(Graph graph) {

		createStartSolution(graph);		
		int numberOfIterationsWithoutImprovement = 0;
		while (numberOfIterationsWithoutImprovement < 100 && minimalAmountOfColors > graph.calculateLBChromaticIndex()) {
			
			List<Point> neighbor = createNewOrder(solutionOrder);
			if (isNewOrderBetter(neighbor, graph)) {
				setNewBestSolution(graph,neighbor);
				lastSteps.add(new OrderStep(neighbor));
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
				lastSteps.add(new OrderStep(neighbor));
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
				last = lastSteps.peek().getOrder();
			}else {
				last = graph.getEdges();
			}
			setNewBestSolution(graph, last);
		}
	}
	
	@Override
	public void resetColoring(Graph graph) {
		lastSteps.clear();
		numberOfIterations = 0; 
		graph.uncolor();
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
	protected abstract List<Point> createNewOrder(List<Point> old);
	
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
