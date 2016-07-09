package graphs;

import java.util.Arrays;
import java.util.List;
import java.awt.Point;

public class LineGraph extends SimpleGraph {
	
	private Graph original; 
	private int[] nodeColors;

	public LineGraph(int vertexNumber, Graph g) {
		super(vertexNumber);
		setOriginal(g); 
		this.setChanged();
		this.notifyObservers();
		nodeColors = new int[vertexNumber]; 
		Arrays.fill(nodeColors, SimpleGraph.UNCOLORED);
	}

	/**
	 * @return the original
	 */
	public Graph getOriginal() {
		return original;
	}

	/**
	 * @param original the original to set
	 */
	public void setOriginal(Graph original) {
		this.original = original;
	}
	
	/**
	 * Returns the node Colors that were set during the coloring
	 * @return
	 */
	public int[] getNodeColors(){
		return nodeColors; 
	}
	
	/**
	 * Returns the LineGraph of a given graph where the nodes represent
	 * the edges of the original graph and the edges are nodes where to 
	 * edges were connected in the original graph
	 * @param g
	 * @return
	 */
	public static LineGraph convertToLineGraph(Graph g){
		
		LineGraph l = new LineGraph(g.getEdgeNumber(),g);
		List <Point> edges = g.getEdges();
		int edgeNr1 = 0; 
		int edgeNr2 = 0; 
		for(Point edge1: edges){
			for(Point edge2: edges){
				if(haveEdgesSameNode(edge1, edge2) && !edge1.equals(edge2)){
					l.addEdge(edgeNr1, edgeNr2);
				}
				edgeNr2++;
			}
			edgeNr2 = 0; 
			edgeNr1++;
		}
		
		return l;
	}


	/**
	 * Checks whether two edges have a node that connects them
	 * @param edge1
	 * @param edge2
	 * @return
	 */
	private static boolean haveEdgesSameNode(Point edge1, Point edge2) {
		
		int e1x = edge1.x;
		int e2x = edge2.x;
		int e1y = edge1.y;
		int e2y = edge2.y;
		
		if(e1x == e2x || e1x == e2y || e1y == e2x || e1y == e2y){
			return true; 
		}
		return false;
	}
}
