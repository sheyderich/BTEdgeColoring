package graphs;

import java.util.Arrays;
import java.util.List;

import exceptions.InvalidColorException;
import exceptions.NodeNotFoundException;
import helper.EdgeColor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 * 
 * @author Stephanie Heyderich
 */
public class LineGraph extends SimpleGraph {
	
	private Graph original; 
	private int[] nodeColors;

	public LineGraph(int vertexNumber, Graph g) {
		super(vertexNumber);
		setOriginal(g); 
		this.setChanged();
		this.notifyObservers();
		nodeColors = new int[vertexNumber]; 
		Arrays.fill(nodeColors, UNCOLORED);
	}
	
	public LineGraph(LineGraph g){
		super(g.getVertexNumber());
		for(int i = 0; i < graph.length; i++){
			for(int j = 0; j < graph.length; j++){
				graph[i][j] = g.isEdgeExistent(i, j)? UNCOLORED: NOTEXISTENT;
			}
		}
		colors = new int[this.getVertexNumber()];
		Arrays.fill(colors, 0);
		nodeColors = new int[g.getVertexNumber()]; 
		Arrays.fill(nodeColors, UNCOLORED);
	}
	
	/**
	 * Sets the given node to the given color
	 * @param node
	 * @param color
	 */
	public void setNodeColor(int node, int color){
		if(color != UNCOLORED && color < 1) throw new InvalidColorException("Color has to be chosen from 1 .. n. Color was: " + color);
		if(node < 0 || node > getVertexNumber()) throw new NodeNotFoundException("Node not existent");
		
		nodeColors[node] = color;
	}
	
	/**
	 * checks whether a given node has a color
	 * @param node
	 * @return
	 */
	public boolean isNodeColored(int node){
		if(nodeColors[node] != UNCOLORED){
			return true; 
		}
		return false; 
	}
	
	/**
	 * Sets the given node to uncolored
	 * @param node
	 */
	public void removeNodeColor(int node){
		if(node < 0 || node > getVertexNumber()) throw new NodeNotFoundException("Node not existent");
		int color = nodeColors[node];
		nodeColors[node] = UNCOLORED;
		colors[color-1]--;
	}
	
	/**
	 * Returns the color of the givenn node
	 * @param node
	 */
	public int getNodeColor(int node){
		if(node < 0 || node > getVertexNumber()) throw new NodeNotFoundException("Node not existent");
		return nodeColors[node];
	}
	
	/**
	 * Checks whether it is legal to have the given
	 * node colored the way it is
	 * @param node
	 * @return
	 */
	public boolean isNodeColoringValid(int node){
		List<Integer> neighbors = getNeighbors(node);
		for(Integer i: neighbors){
			if(nodeColors[i] == nodeColors[node]){
				return false; 
			}
		}
		return true; 
	}
	
	@Override
	public boolean isGraphColoringValid(){
		for(int node = 0; node < getVertexNumber(); node++){
			if(!isNodeColoringValid(node)){
				return false;
			}
		}
		return true; 
	}
	
	/**
	 * Uses the original graph to calculate the boundaries
	 * for the chromatic index, since the boundary for
	 * edge coloring is more strongly restricted
	 */
	@Override
	public int calculateLBChromaticIndex() {
		return original.calculateLBChromaticIndex();
	}
	
	/**
	 * Uses the original graph to calculate the boundaries
	 * for the chromatic index, since the boundary for
	 * edge coloring is more strongly restricted
	 */
	@Override
	public int calculateUBChromaticIndex() {
		return original.calculateUBChromaticIndex();
	}
	
	
	public String toString(){
		String s = super.toString();
		String tmp = "\n"; 
		for(Integer i: nodeColors){
			tmp += i + " ";
		}
		tmp += "\n";
		return s+tmp;
	}
	
	@Override
	public String getType(){
		return "Line Graph";
	}
	
	@Override
	public boolean isColored(){
		for(Integer i: nodeColors){
			if(i == UNCOLORED){
				return false; 
			}
		}
		return true; 
	}
	
	@Override
	public boolean isUncolored(){
		for(Integer i: nodeColors){
			if(i != UNCOLORED){
				return false; 
			}
		}
		return true; 
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
	 * Sets a step on the last step stack
	 * @param node
	 */
	public void setLastStep(int node){
		int color = nodeColors[node];
		colors[color-1]++;
		steps.push(new int[]{node, color});
		this.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public void uncolor(){
		Arrays.fill(nodeColors, UNCOLORED);
		Arrays.fill(colors, 0);
		steps.clear();
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Paints the given graph on the graphics context g. 
	 * Nodes instead of edges are colored based on the 
	 * colors that are assigned to the graph
	 */
	public void paintGraph(Graphics g, Dimension d){

		List<Point> coordinates = calculateNodeCoordinates(d);
		
		int node = 0; 
		for(Point p: coordinates){
			Color old = g.getColor();
			int newC = nodeColors[node];
			if(newC != UNCOLORED){
				g.setColor(EdgeColor.getColor(newC-1));
			}
			paintNode(p, g);
			g.setColor(old);
			node++;
		}
		
		for(int i = 0; i < graph.length; i++){
			for(int j = i+1; j < graph.length; j++){
				if(graph[i][j] != NOTEXISTENT){
					paintEdgeBlack(coordinates.get(i), coordinates.get(j), g);
				}
			}
		}
		
		node = 1;
		g.setFont(new Font("", Font.BOLD, 12));
		for(Point p: labelCoordinates){
			g.drawString(String.valueOf(node), (int)p.getX(), (int)p.getY());
			node++;
		}
		
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
