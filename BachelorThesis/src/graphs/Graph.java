package graphs;

import java.awt.Point;
import java.util.List;
/**
 * Interface that defines the operations a graph must provide.
 * 
 * @author Stephanie Heyderich
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
	 * Returns a List filled with points, where 
	 * the two coordinates of the point denote
	 * an edge
	 * @return
	 */
	public List<Point> getEdges();
	
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
	 * Returns whether the graph is uncolored or not
	 */
	public boolean isUncolored();
	
	/**
	 * Returns whether the graph is completely colored or not
	 * @return
	 */
	public boolean isColored();
	
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
	 * Returns a list of all the colors that were used on the edges of 
	 * the given vertex
	 * @param vertex
	 * @return
	 */
	public List<Integer> getColorsAtVertex(int vertex);

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
	
	/**
	 * Notifys the model that a new step was done during an algorithm 
	 * @param u
	 * @param v
	 */
	public void setLastStep(int u, int v);

	/**
	 * Notifys the Model that the last step was undone
	 * and the visual model has to change
	 */
	public void removeLastStep();
		
	/**
	 * Uncolors the graph to reset the algorithm
	 */
	public void uncolor();
	
	/**
	 * Returns a copy of the graph. It also copies
	 * the colors (!= implemented copy constructor)
	 * @return
	 */
	public Graph copyWithColors();
	
}
