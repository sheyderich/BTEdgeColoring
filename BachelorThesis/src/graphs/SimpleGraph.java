package graphs;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import exceptions.ChromaticIndexNotCalculatedException;
import exceptions.EdgeNotColoredException;
import exceptions.EdgeNotFoundException;
import exceptions.InvalidColorException;
import exceptions.InvalidEdgeException;
import helper.EdgeColor;

/**
 * Implements the Graph-Interface for a simple graph. 
 * Uses adjacency matrices as an internal model to easily 
 * access the edges and provide means for coloring them. 
 * The matrix is filled with 
 *  0 if there is no edge
 *  -2 if the edge exists but is not colored yet
 *  k if the edge is colored in the color k (1<=k<=n)
 * @author Stephanie Heyderich
 *
 */
public class SimpleGraph extends DrawableGraph implements Graph{
	
	public final static int UNDEFINED = -1; 
	public final static int UNCOLORED = -2;
	public final static int NOTEXISTENT = 0;
	
	protected int[][] graph; 
	protected int numberEdges;
	protected int colors; 
	protected int maxVertexDegree 	= 	UNDEFINED; 
	protected int lbChromaticIndex	= 	UNDEFINED;
	protected int ubChromaticIndex 	= 	UNDEFINED;
	protected int chromaticIndex 	= 	UNDEFINED; 
	protected List <Point> coordinates = new ArrayList<Point>();
	protected int[] lastStep = new int[3];
	protected Stack<int[]> steps = new Stack<int[]>();
	
	
	/**
	 * Constructs a graph by creating an empty adjacency matrix
	 * @param vertexNumber
	 */
	public SimpleGraph(int vertexNumber){
		graph = new int[vertexNumber][vertexNumber];
	}
	
	/**
	 * Copy Constructor
	 */
	public SimpleGraph(SimpleGraph g){
		graph = new int[g.getVertexNumber()][g.getVertexNumber()];
		for(int i = 0; i < graph.length; i++){
			for(int j = 0; j < graph.length; j++){
				graph[i][j] = g.isEdgeExistent(i, j)? -2: 0;
			}
		}
	}
	
	@Override
	public int getVertexNumber(){
		return graph.length;
	}

	@Override
	public void addEdge(int u, int v) {
		if(u > graph.length || v > graph.length){
			throw new InvalidEdgeException("The edge you tried to access is not valid: (" + (u+1) + "," +(v+1)+")");
		}
		if(graph[u][v] == NOTEXISTENT || graph[v][u] == NOTEXISTENT){
			numberEdges += 1;
			graph[u][v] = UNCOLORED;
			graph[v][u] = UNCOLORED;
		}
	}
	
	@Override
	public boolean isEdgeExistent(int u, int v) {
		if(u < 0 || u >= graph.length || v < 0 || v >= graph.length){
			return false; 
		}
		return graph[u][v] != NOTEXISTENT;
	}
	
	@Override
	public boolean isEdgeColored(int u, int v){
		if(graph[u][v] == NOTEXISTENT) throw new InvalidEdgeException("This edge does not exist.");
		if(graph[u][v] == UNCOLORED){
			return false;
		}else{
			return true;
		}
		
	}

	@Override
	public void setEdgeColor(int u, int v, int color) {
		if(color < 1) throw new InvalidColorException("Color has to be chosen from 1 .. n. Color was: " + color);
		if(color > colors){
			colors++;
		}
		graph[u][v] = color;
		graph[v][u] = color;
//		lastStep[0] = u;
//		lastStep[1] = v;
//		lastStep[2] = color;
//		steps.push(lastStep);
		this.setChanged();
		this.notifyObservers();
	}
	
	@Override
	public void removeEdgeColor(int u, int v) {
		if(graph[u][v] == NOTEXISTENT){
			throw new EdgeNotFoundException("The Edge does not exist");
		}
		graph[u][v] = UNCOLORED;
		graph[v][u] = UNCOLORED;
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public int getEdgeColor(int u, int v) {
		if(graph[u][v] == NOTEXISTENT){
			throw new EdgeNotFoundException("The Edge does not exist");
		}
		if(graph[u][v] == UNCOLORED){
			throw new EdgeNotColoredException("The Edge has not been colored yet");
		}
		return graph[u][v];
	}
	
	@Override
	public boolean isEdgeColoringValid(int u, int v){

		int color = getEdgeColor(u,v);
		List<Integer> neighbors = getNeighbors(u);
		neighbors.remove(new Integer(v));
		for(Integer i : neighbors){
			if(graph[u][i]==color) 
				return false;  
		}
		
		neighbors = getNeighbors(v);
		neighbors.remove(new Integer(u));
		for(Integer i : neighbors){
			if(graph[v][i]==color) 
				return false;  
		}
		return true;
	}
	
	@Override
	public boolean isGraphColoringValid(){ 
		for(int u = 0; u < graph.length; u++){
			for(int v = u+1; v < graph.length; v++){
				if(graph[u][v] != NOTEXISTENT){
					if(!isEdgeColoringValid(u,v)){
						return false;
					}
				}
			}
		}
		return true;
	}

	@Override
	public int calculateMaxVertexDegree() {
		if(maxVertexDegree == UNDEFINED){
			for(int i = 0; i < graph.length; i++){
				int i_vertexDegree = 0; 
				for(int j = 0; j < graph.length; j++){
					i_vertexDegree += graph[i][j] > 0 || graph[i][j] == UNCOLORED? 1 : 0;
				}
				maxVertexDegree = maxVertexDegree > i_vertexDegree? maxVertexDegree: i_vertexDegree;
			}
		}
		return maxVertexDegree;
	}
	
	/**
	 * Using Vizing's Theorem for simple Graphs to calculate the 
	 * lower bound for the chromatic index
	 */
	@Override
	public int calculateLBChromaticIndex() {
		if (lbChromaticIndex == UNDEFINED){
			calculateMaxVertexDegree();
			lbChromaticIndex = maxVertexDegree; 
		}
		return lbChromaticIndex;
	}

	/**
	 * Using Vizing's Theorem for simple Graphs to calculate the 
	 * upper bound for the chromatic index
	 */
	@Override
	public int calculateUBChromaticIndex() {
		if (ubChromaticIndex == UNDEFINED){
			calculateMaxVertexDegree();
			ubChromaticIndex = maxVertexDegree + 1; 
		}
		return ubChromaticIndex;
	}

	@Override
	public boolean isChromaticIndexDeterminedByBounds() {
		if(lbChromaticIndex == UNDEFINED || ubChromaticIndex == UNDEFINED){
			calculateUBChromaticIndex();
			calculateLBChromaticIndex();
		}
		return lbChromaticIndex == ubChromaticIndex;
	}

	@Override
	public int getChromaticIndex(){
		if(lbChromaticIndex == UNDEFINED || ubChromaticIndex == UNDEFINED){
			calculateUBChromaticIndex();
			calculateLBChromaticIndex();
		}
		if(!isChromaticIndexDeterminedByBounds()){
			throw new ChromaticIndexNotCalculatedException("Cannot Return Chromatic Index due to inequality of bounds");
		}
		chromaticIndex = lbChromaticIndex; 
		return chromaticIndex;
	}

	@Override
	public int getQuantityColors() {
		return colors;
	}

	@Override
	public List<Integer> getNeighbors(int vertex) {
		List<Integer> neighbors = new ArrayList<Integer>();
		for(int neighbor = 0; neighbor < graph.length; neighbor ++){
			if(graph[vertex][neighbor] != NOTEXISTENT){
				neighbors.add(new Integer(neighbor));
			}
		}
		return neighbors;
	}

	@Override
	public String toString(){
		String output = new String();
		for(int i = 0; i < graph.length; i++){
			for(int j = 0; j < graph.length; j++){
				output += String.format("%4d",graph[i][j]);
			}
			output += "\n";
		}
		return output;
	}
	
	/**
	 * Returns a representation of the graph as edges and
	 * their respective color in the form: 
	 * edge u, edge v = color
	 */
	public String toEdgeString(){
		String s = new String();
		for(int i = 0; i < graph.length; i++){
			for(int j = i+1; j <graph.length; j++){
				if(graph[i][j] != NOTEXISTENT){
					s += "("+i+"," + j +") = " + graph[i][j] + "\n";
				}
			}
		}
		return s; 
	}
	
	@Override
	public String getType(){
		return "Simple Graph";
	}
	
	
	@Override
	public void paintGraph(Graphics g, Dimension d) {
		
		List<Point> coordinates = calculateNodeCoordinates(d);
		
		for(Point p: coordinates){
			paintNode(p, g);
		}
		for(int i = 0; i < graph.length; i++){
			for(int j = i+1; j < graph.length; j++){
				if(graph[i][j] != NOTEXISTENT){
					if(graph[i][j] == UNCOLORED){
						paintEdgeBlack(coordinates.get(i), coordinates.get(j), g);
					}else{
						paintEdgeColor(coordinates.get(i), coordinates.get(j), false, EdgeColor.getColor(graph[i][j]-1), g);
					}
				}
			}
		}
	}
	
	/**
	 * Sets coordinates for the nodes so that they form a cicle. 
	 * This guarantees that the edges between the nodes are not 
	 * crossing each other. 
	 * @param Dimension d
	 * @return List<Point>
	 */
	@Override
	public List<Point> calculateNodeCoordinates(Dimension d) {
		if(coordinates.isEmpty()){
			double radius = d.getWidth() < d.getHeight()? (d.getWidth()/2)-20 : (d.getHeight()/2)-20;
			Point center = new Point((int)(d.getWidth()/2 + 0.5),(int)(d.getHeight()/2 + 0.5));
			double degreeBetweenPoints = (2*Math.PI)/graph.length;
			
			for(int i = 0; i < graph.length; i++){
				double x = Math.cos(i*degreeBetweenPoints)*radius+center.getX();
				double y = Math.sin(i*degreeBetweenPoints)*radius+center.getY();
				Point p = new Point((int)(x+0.5), (int)(y+0.5));
				coordinates.add(p);
			}
		}
		return coordinates;
	}

	@Override
	public int getEdgeNumber() {
		return numberEdges;
	}
	
	@Override
	public boolean isColored(){
		for(int i = 0; i < graph.length; i++){
			for(int j = 0; j < graph.length; j++){
				if(graph[i][j] == UNCOLORED){
					return false;
				}
			}
		}
		return true;
	}
	
	@Override
	public boolean isUncolored(){
		for(int i = 0; i < graph.length; i++){
			for(int j = 0; j < graph.length; j++){
				if(!(graph[i][j] == UNCOLORED || graph[i][j] == NOTEXISTENT)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Returns an array where the last step is saved in
	 */
	public int[] getLastStep(){
		if(steps.isEmpty()){
			return new int[3];
		}
		return steps.peek();
	}
	
	/**
	 * Removes the last step from the stack 
	 */
	public void removeLastStep(){
		if(!steps.isEmpty()){
			steps.pop();
		}
	}
	
	/**
	 * Sets a step on the last step stack
	 * @param u
	 * @param v
	 */
	public void setLastStep(int u, int v){
		steps.push(new int[]{u,v,graph[u][v]});
	}
}
