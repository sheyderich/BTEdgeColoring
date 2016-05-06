package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import exceptions.EdgeNotColoredException;
import exceptions.EdgeNotFoundException;
import exceptions.NoFreeColorException;
import graphs.BipartiteGraph;
import graphs.Graph;

public class AlgorithmKoenig {
	
	private static int u = 0; 
	private static int v = 0;
	private static int command = 0; 
	
	/**
	 * Uses Koenig's Algorithm to color a bipartite graph 
	 * @param graph
	 * @throws EdgeNotColoredException 
	 * @throws EdgeNotFoundException 
	 */
	public static void applyKoenigAlgorithmComplete(BipartiteGraph graph){
		for(int u = 0; u < graph.getVertexNumber(); u++){
			for(int v = u+1; v < graph.getVertexNumber(); v++){
				if(graph.isEdgeExistent(u, v)){
					applyColoringForEdge(graph,u,v);
				}
			}
		}
	}
	
	/**
	 * Proceeds step by step through the edge coloring algorithm. 
	 * @param graph
	 */
	public static void applyKoenigAlgorithmStepwise(BipartiteGraph graph){
		if(!graph.isColorizationFinished()){
			for(; u < graph.getVertexNumber(); u++){
				for(; v < graph.getVertexNumber(); v++){
					if(graph.isEdgeExistent(u, v) && !graph.isEdgeColored(u, v)){
						applyColoringForEdge(graph,u,v, command);
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
	
	public static void undoLastColoring(BipartiteGraph graph){
		if(!graph.isUncolored()){
			for(int i = u; i >= 0; i --){
				for(int j = v; j >= 0; j--){
					if(graph.isEdgeExistent(i, j) && graph.isEdgeColored(i, j)){
						graph.removeEdgeColor(i, j);
						u = i; 
						v = j; 
						return;
					}
					v = graph.getEdgeNumber();
				}
			}
		}
	}
	
	/**
	 * resets the nodes u and v that are tested for coloring
	 */
	public static void resetStepwiseAlgorithm(){
		u = 0; 
		v = 0; 
	}
	
	/**
	 * Sets the color for a specific Edge based on a command to 
	 * be able to sequentially run thorugh the programm
	 * @param graph
	 * @param u
	 * @param v
	 */
	private static void applyColoringForEdge(BipartiteGraph graph, int u, int v, int command){
		switch(command){
		case 0: 
			boolean colored = tryColorEdge(graph,u,v);
			if(!colored){
				int cv = getFreeColor(graph,u);
				int cu = getFreeColor(graph,v);
				List<Integer> path = findAlternatingPath(graph, v, cv, cu);
				graph.setAugmentedPath(path);
			}
			break; 
		case 1: 
			int cv = getFreeColor(graph,u);
			int cu = getFreeColor(graph,v);
			switchColorOnPath(graph, graph.getAugmentedPath(), cv, cu);
			break;
		case 2: 
			graph.deleteAugmentedPath();
			tryColorEdge(graph, u, v);
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
	private static void applyColoringForEdge(BipartiteGraph graph, int u, int v){
		boolean colored = tryColorEdge(graph,u,v);
		if(!colored){
			int cv = getFreeColor(graph,u);
			int cu = getFreeColor(graph,v);
			List<Integer> path = findAlternatingPath(graph, v, cv, cu);
			graph.setAugmentedPath(path);
			switchColorOnPath(graph, graph.getAugmentedPath(), cv, cu);
			graph.deleteAugmentedPath();
			tryColorEdge(graph, u, v);
		}
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
		for(int color = 1; color <= graph.getChromaticIndex(); color++){
			graph.setEdgeColor(u, v, color);
//			System.out.println("Edge (" + u + "," + v + ") colored in "+ color);
			
			if(graph.isEdgeColoringValid(u, v)){
				break;
			}else{
				graph.removeEdgeColor(u, v);
			}
		}
		return graph.isEdgeColored(u, v);
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
	private static void switchColorOnPath(Graph graph, List<Integer> path, int cv, int cu) {
		
		int active = path.get(0);
		for(int next = 1; next < path.size(); next++){
			int color = graph.getEdgeColor(active, path.get(next));
			if(color == cv){
				graph.setEdgeColor(active, path.get(next), cu);
//				System.out.println("Switched color on (" + active + "," + path.get(next) + ") to " + cu);
			}else{
				graph.setEdgeColor(active, path.get(next), cv);
//				System.out.println("Switched color on (" + active + "," + path.get(next) + ") to " + cv);
			}
			active = path.get(next);
		}
		
	}
	
	/**
	 * Reveals method call for getFreeColor to test it
	 * @param g
	 * @param vertex
	 * @return
	 */
	public static int testFreeColor(Graph g, int vertex){
		return getFreeColor(g, vertex);
	}
	
	/**
	 * Reveals method call for the method SwitchColorOnPath for testing purposes
	 * @param graph
	 * @param path
	 * @param cv
	 * @param cu
	 */
	public static void testSwitchColorOnPath(Graph graph, List<Integer> path, int cv, int cu){
		switchColorOnPath(graph, path, cv,cu);
	}
	/**
	 * Reveals method call for the method FindAlternatingPath for testing purposes
	 * @param g
	 * @param vertex
	 * @param cv
	 * @param cu
	 * @return
	 */
	public static List<Integer> testFindAlternatingPath(Graph g, int vertex, int cv, int cu){
		return findAlternatingPath(g, vertex, cv, cu);
	}
	
	/**
	 * Reveals method call for the method GetNeighboringVertexWithColor for testing purposes
	 * @param g
	 * @param vertex
	 * @param color
	 * @return
	 */
	public static int testGetNeighboringVertexWithColor(Graph g, int vertex, int color){
		return getNeighboringVertexWithColor(g, vertex, color);
	}
	
}
