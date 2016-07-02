package algorithms;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import graphs.Graph;
import graphs.SimpleGraph;

public class LocalSearchGreedy implements EdgeColoringAlgorithm {

	@Override
	public void applyAlgorithmComplete(Graph graph) {
		//generate initial solution
		EdgeColoringAlgorithm sStar = new Greedy(graph.getEdges());
		List<Object> solutionOrder = graph.getEdges();
		sStar.applyAlgorithmComplete(graph);
		int minimalAmountOfColors = graph.getQuantityColors();
		int numberOfIterationsWithoutImprovement = 0;
		Random rand = new Random();
		Object[] edges = solutionOrder.toArray();
		
		//while better solution exists, do: 
		while(numberOfIterationsWithoutImprovement < 100){
			int first = rand.nextInt(solutionOrder.size());
			int second = rand.nextInt(solutionOrder.size());
			Point tmp = (Point) solutionOrder.get(first);
			edges[first] = solutionOrder.get(second);
			edges[second] = tmp;
			List<Object> neighbor = Arrays.asList(edges);
			sStar = new Greedy(neighbor);
			sStar.applyAlgorithmComplete(new SimpleGraph((SimpleGraph)graph));
			if(minimalAmountOfColors > graph.getQuantityColors()){
				minimalAmountOfColors = graph.getQuantityColors();
				solutionOrder = neighbor;
			}else{
				sStar = new Greedy(solutionOrder);
				sStar.applyAlgorithmComplete(new SimpleGraph((SimpleGraph)graph));
				numberOfIterationsWithoutImprovement++;
			}
		}
	}

	@Override
	public void applyAlgorithmStepwise(Graph graph) {
		EdgeColoringAlgorithm sStar = new Greedy(graph.getEdges());
		sStar.applyAlgorithmComplete(graph);
		int minimalAmountOfColors = graph.getQuantityColors();
		

	}

	@Override
	public void undoLastColoring(Graph graph) {
		// TODO Auto-generated method stub

	}

}
