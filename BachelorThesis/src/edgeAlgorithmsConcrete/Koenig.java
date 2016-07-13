package edgeAlgorithmsConcrete;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import edgeAlgorithms.ColoringAlgorithms;
import exceptions.EdgeNotColoredException;
import exceptions.EdgeNotFoundException;
import exceptions.IllegalGraphTypeException;
import exceptions.NoFreeColorException;
import graphs.BipartiteGraph;
import graphs.Graph;
import graphs.SimpleGraph;
import helper.AlgorithmStep;
import helper.ColorEdgeStep;
import helper.PathSwitchingStep;
/**
 * A algorithm for coloring the edges of a bipartite graph 
 * introduced by König. It colors the edges with the first available color
 * and if there is none available, switches colors on an augmented path.
 * @author Stephanie Heyderich
 */
public class Koenig implements ColoringAlgorithms{
	
	private Stack<AlgorithmStep> steps = new Stack<AlgorithmStep>();
	private int u = 0; 
	private int v = 0;
	private int command = 0; 
	
	/**
	 * Uses Koenig's Algorithm to color a bipartite graph 
	 * @param graph
	 * @throws EdgeNotColoredException 
	 * @throws EdgeNotFoundException 
	 */
	public void applyAlgorithmComplete(Graph g){
		BipartiteGraph graph;
		
		if(!(g instanceof BipartiteGraph)){
			throw new IllegalGraphTypeException("Only Bipartite Graphs are allowed in Koenig's algorithm.");
		}else{
			graph = (BipartiteGraph)g;
		}
		
		for(; u < graph.getVertexNumber(); u++){
			for(; v < graph.getVertexNumber(); v++){
				if(graph.isEdgeExistent(u, v) && !graph.isEdgeColored(u, v)){
					applyColoringForEdge(graph,u,v);
				}
			}
			v = 0;
		}
	}
	
	/**
	 * Proceeds step by step through the edge coloring algorithm. 
	 * Does not set u, v to a new value if graph is completely colored already
	 * @param graph
	 */
	public void applyAlgorithmStepwise(Graph graph){

		if(!graph.isColored()){
			for(; u < graph.getVertexNumber(); u++){
				for(; v < graph.getVertexNumber(); v++){
					if(graph.isEdgeExistent(u, v) && !graph.isEdgeColored(u, v)){
						applyColoringForEdge((BipartiteGraph)graph,u,v, command);
						if(!graph.isEdgeColored(u, v)){
							command++;
						}else{
							command = 0; 
						}
						return;
					}
				}
				v = 0; 
			}
			u = 0; 
		}
	}
	
	/**
	 * Removes the last coloring that was conducted by the algorithm
	 * Does not set u, v when the graph is completely uncolored
	 * @param graph
	 */
	public void undoLastColoring(Graph g){
		if(!steps.empty()){
			AlgorithmStep last = steps.pop();
			g.removeLastStep();
			last.undo(g);
			u = 0;
			v = 0;
		}
	}
	
	@Override
	public void resetColoring(Graph graph) {
		graph.uncolor();
		steps.clear();
		u = 0; 
		v = 0;
		command = 0; 
	}
	
	/**
	 * Sets the color for a specific Edge based on a command to 
	 * be able to sequentially run thorugh the programm
	 * @param graph
	 * @param u
	 * @param v
	 */
	private void applyColoringForEdge(BipartiteGraph graph, int u, int v, int command){
		switch(command){
		case 0: 
			boolean colored = tryColorEdge(graph,u,v);
			if(!colored){
				int cv = getFreeColor(graph,u);
				int cu = getFreeColor(graph,v);
				List<Integer> path = findAlternatingPath(graph, v, cv, cu);
				graph.setAugmentedPath(path);
			}else{
				graph.setLastStep(u,v);
				steps.push(new ColorEdgeStep(u,v));
			}
			break; 
		case 1: 
			int cv = getFreeColor(graph,u);
			int cu = getFreeColor(graph,v);
			switchColorOnPath(graph, graph.getAugmentedPath(), cv, cu);
			steps.push(new PathSwitchingStep(cv, cu, graph.getAugmentedPath()));
			break;
		case 2: 
			graph.deleteAugmentedPath();
			tryColorEdge(graph, u, v);
			graph.setLastStep(u, v);
			steps.push(new ColorEdgeStep(u,v));
		default: 
			break;
		}
	}
	
	/**
	 * Applies the edge coloring algorithm on a specific edge
	 * @param graph
	 * @param u
	 * @param v
	 */
	private void applyColoringForEdge(BipartiteGraph graph, int u, int v){
		boolean colored = tryColorEdge(graph,u,v);
		if(!colored){
			int cv = getFreeColor(graph,u);
			int cu = getFreeColor(graph,v);
			List<Integer> path = findAlternatingPath(graph, v, cv, cu);
			graph.setAugmentedPath(path);
			switchColorOnPath(graph, graph.getAugmentedPath(), cv, cu);
			steps.push(new PathSwitchingStep(cv,cu,graph.getAugmentedPath()));
			graph.deleteAugmentedPath();
			tryColorEdge(graph, u, v);
			steps.push(new ColorEdgeStep(u,v));
		}else{
			steps.push(new ColorEdgeStep(u,v));
		}
		graph.setLastStep(u, v);
	}
	

	
	/**
	 * Tries to color the given edge of the graph in a color from [1 ... chromaticIndex]
	 * If no color leads to a valid edge coloring, the edge is uncolored and false is returned
	 * @param graph
	 * @param u
	 * @param v
	 * @return
	 */
	private static boolean tryColorEdge(Graph graph, int u, int v){
		int color = 1; 
		do{
			graph.setEdgeColor(u, v, color);
			color++;
		}while(!graph.isEdgeColoringValid(u, v) && color <= graph.getChromaticIndex());
		
		if(graph.isEdgeColoringValid(u, v)){
			return true;
		}else{
			graph.setEdgeColor(u,v, SimpleGraph.UNCOLORED);
			return false;
		}
	}
	
	/**
	 * Returns a free color at the given edge
	 * @param graph
	 * @param vertex
	 * @return
	 */
	private static int getFreeColor(Graph graph, int vertex){
		List<Integer> neighbors = graph.getNeighbors(vertex);
		boolean[] colors = new boolean[graph.getChromaticIndex()];
		Arrays.fill(colors, true);
		
		for(Integer neighbor: neighbors){
			try{
				int color = graph.getEdgeColor(vertex, neighbor);
				colors[color-1] = false;
			}catch (EdgeNotColoredException e){
				// does not matter if edge not colored yet
			}
		}
		for(int i = 0; i < colors.length; i++){
			if(colors[i]){
				return i+1;
			}
		}
		
		throw new NoFreeColorException("There is no free Color");
	}
	
	/**
	 * Finds the maximal alternating path of edges of the colors cl and cq
	 * @param graph
	 * @param vertex
	 * @param cv
	 * @param cu
	 */
	private static List<Integer> findAlternatingPath(Graph graph, int vertex, int cv, int cu) {
		
		List<Integer> path = new ArrayList<Integer>();
		int activeColor = cv;
		int evenOdd = 1; 
		while(vertex != -1){
			path.add(vertex);
			vertex = getNeighboringVertexWithColor(graph, vertex, activeColor);
			activeColor = evenOdd%2 == 0? cv : cu;
			evenOdd++;
		}
		return path; 
	}
	
	/**
	 * Returns a vertex with a specified color that is connected to the given vertex.
	 * Returns -1 if no edge with specified color is found
	 * @param graph
	 * @param vertex
	 * @param color
	 * @return
	 */
	private static int getNeighboringVertexWithColor(Graph graph, int vertex, int color) {
		
		List<Integer> neighbors = graph.getNeighbors(vertex);
		for(Integer neighbor: neighbors){
			try{
				if(graph.getEdgeColor(vertex, neighbor) == color)
					return neighbor;
			}catch(EdgeNotColoredException e){
				
			}
		}
		return -1;
	}

	/**
	 * Switches the colors on the alternating path
	 * @param graph
	 * @param path
	 * @param cv
	 * @param cu
	 */
	public static void switchColorOnPath(Graph graph, List<Integer> path, int cv, int cu) {
		
		int active = path.get(0);
		for(int next = 1; next < path.size(); next++){
			int color = graph.getEdgeColor(active, path.get(next));
			if(color == cv){
				graph.setEdgeColor(active, path.get(next), cu);
			}else{
				graph.setEdgeColor(active, path.get(next), cv);
			}
			active = path.get(next);
		}
		
	}
	
}
