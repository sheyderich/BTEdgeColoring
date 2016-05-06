package graphs;

import java.util.List;
/**
 * Interface that defines the operations a graph must provide.
 * 
 * @author Stephanie Heyderich
 * @version 23.04.2016
 */
public interface Graph {
	
	/**
	 * Returns number of nodes
	 * @return
	 */
	public int getVertexNumber();
	
	/**
	 * Returns number of edges
	 * @return
	 */
	public int getEdgeNumber();
	
	/**
	 * Adds an edge to the graph by providing
	 * @param u one vertex of the edge
	 * @param v the other vertex of the edge
	 */
	public void addEdge(int u, int v);
	
	/**
	 * Returns true if an edge exists, false else
	 * @param u
	 * @param v
	 * @return
	 */
	public boolean isEdgeExistent(int u, int v);
	
	/**
	 * Returns boolean that defines whether or not an Edge has been colored
	 */
	public boolean isEdgeColored(int u, int v);
	
	/**
	 * Sets the color of an edge based on the two vertices that the edge
	 * connects and an integer for the color
	 * @param u
	 * @param v
	 * @param color
	 */
	public void setEdgeColor(int u, int v, int color);
	
	/**
	 * Removes the edge color that was given to this particular edge
	 * @param u
	 * @param v
	 */
	public void removeEdgeColor(int u, int v);
	
	/**
	 * Returns the color as an integer which colors the edge that connects
	 * the vertices 
	 * @param u
	 * @param v
	 * @return
	 */
	public int getEdgeColor(int u, int v);
	
	/**
	 * Returns the number of used colors in the edge coloring
	 * @return
	 */
	public int getQuantityColors();
	
	/**
	 * Checks whether the color chosen for an edge is a legal choice
	 * for the edge coloring problem
	 * @param u
	 * @param v
	 * @return
	 */
	public boolean isEdgeColoringValid(int u, int v);
	
	/**
	 * Checks whether the coloring of the whole graph is valid
	 * @return
	 */
	public boolean isGraphColoringValid();
	
	/**
	 * Calculates the maximum vertex degree of the graph at hand
	 * to set lower and upper bounds for the chromatic index.
	 */
	public int calculateMaxVertexDegree();
	
	/**
	 * Calculate the lower bound for the chromatic Index
	 * @return
	 */
	public int calculateLBChromaticIndex();
	
	/**
	 * Calculates the upper bound for the chromatic index
	 * @return
	 */
	public int calculateUBChromaticIndex();
	
	/**
	 * Returns true if there is a distinct chromatic index based on the Bounds, 
	 * false if the chromatic index can't be calculated distinctly
	 * @return
	 */
	public boolean isChromaticIndexDeterminedByBounds();
	
	/**
	 * Returns the chromatic Index if it is distinct, else
	 * it will return -1 
	 * @return
	 * @throws ChromaticIndexNotDistinctException 
	 */
	public int getChromaticIndex();
	
	/**
	 * Returns a list with the vertices that connect to the 
	 * given vertex.
	 * @param vertex
	 * @return
	 */
	public List<Integer> getNeighbors(int vertex);

	/**
	 * Returns the type of the graph
	 */
	public String getType();
	
	/**
	 * Returns the graph as an adjacency matrix
	 * @return
	 */
	public String toString();
	
	/**
	 * Returns the edges of the Graph an the corresponding color
	 * @return
	 */
	public String toEdgeString();
}
