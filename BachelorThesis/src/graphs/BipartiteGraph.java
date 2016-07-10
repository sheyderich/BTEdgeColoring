package graphs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import helper.EdgeColor;

/**
 * Represents a bipartite graph. 
 * it is a simple graph with a few extra functionalites 
 * and different calculations regarding the node position
 * in the visual representation and for determining the 
 * chromatic index. 
 * @author Stephanie Heyderich
 */
public class BipartiteGraph extends SimpleGraph{
	
	private int vertexCount1;
	private int vertexCount2;
	protected List <Integer> augmentedPath;
	
	/**
	 * Create empty adjacency matrix for the bipartite graph
	 * @param vertexNumber
	 */
	public BipartiteGraph(int vertexNumber1, int vertexNumber2) {
		super(vertexNumber1 + vertexNumber2);
		this.vertexCount1 = vertexNumber1;
		this.vertexCount2 = vertexNumber2;
	}
	
	/**
	 * Copy Constructor
	 * @param g
	 */
	public BipartiteGraph(BipartiteGraph g){
		super(g);
		this.vertexCount1 = g.vertexCount1;
		this.vertexCount2 = g.vertexCount2;
	}

	/**
	 * Using Koenig's Theorem for bipartite Graphs to calculate the 
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
	 * Using Koenig's Theorem for bipartite Graphs to calculate the 
	 * upper bound for the chromatic index
	 */
	@Override
	public int calculateUBChromaticIndex() {
		if (ubChromaticIndex == UNDEFINED){
			calculateMaxVertexDegree();
			ubChromaticIndex = maxVertexDegree; 
		}
		return ubChromaticIndex;
	}
	
	@Override
	public String getType(){
		return "Bipartite graph";
	}

	/**
	 * Sets an augmented Path at which lines the colors are switched
	 * @param path
	 */
	public void setAugmentedPath(List<Integer> path){
		this.augmentedPath = path;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Returns the currently set augmented path
	 * @return
	 */
	public List<Integer> getAugmentedPath(){
		return augmentedPath;
	}
	
	/**
	 * Deletes the augmented Path
	 */
	public void deleteAugmentedPath(){
		this.augmentedPath = null;
	}
	
	@Override
	public List<Point> calculateNodeCoordinates(Dimension d){
		if(coordinates.isEmpty()){
			double distanceBetweenFirstSet = (d.getWidth()-50)/(double)vertexCount1;
			double distanceBetweenSecondSet = (d.getWidth()-50)/(double)vertexCount2;
			double distanceBetweenRows = d.getHeight()/3;
			double yCoordinate1Row = distanceBetweenRows;
			double yCoordinate2Row = distanceBetweenRows*2;
			double xCoordinate = 25 + distanceBetweenFirstSet/2;
			
			for(int i = 0; i < vertexCount1; i++){
				coordinates.add(new Point((int)xCoordinate, (int)yCoordinate1Row));
				labelCoordinates.add(new Point((int)xCoordinate, (int) yCoordinate1Row - 5));
				xCoordinate += distanceBetweenFirstSet;
			}
			
			xCoordinate = 25 + distanceBetweenSecondSet/2;
			for(int i = 0; i < vertexCount2; i++){
				coordinates.add(new Point((int)xCoordinate, (int)yCoordinate2Row));
				labelCoordinates.add(new Point((int)xCoordinate, (int) yCoordinate2Row + 25));
				xCoordinate += distanceBetweenSecondSet;
			}
			
		}
		return coordinates;
	}
	
	/**
	 * Paints the graph in the given colors. If an augmented path exists, it is
	 * underlined with a lightgray thicker edge to highlight it
	 */
	@Override
	public void paintGraph(Graphics g, Dimension d) {
		
		List<Point> coordinates = calculateNodeCoordinates(d);
		for(Point p: coordinates){
			paintNode(p, g);
		}

		int node = 1;
		g.setFont(new Font("", Font.BOLD, 12));
		for(Point p: labelCoordinates){
			g.drawString(String.valueOf(node), (int)p.getX(), (int)p.getY());
			node++;
		}
		
		for(int i = 0; i < graph.length; i++){
			for(int j = i+1; j < graph.length; j++){
				
				if(graph[i][j] != NOTEXISTENT){
					if(graph[i][j] == UNCOLORED){
						paintEdgeBlack(coordinates.get(i), coordinates.get(j), g);
					}else{
						Color color = EdgeColor.getColor(graph[i][j]-1);
						if(augmentedPath != null){	
							if(augmentedPath.contains(i)){
								int position = augmentedPath.indexOf(i);
								int before = position; 
								int after = position; 
								if(position > 0){
									before--;
								}
								if(position < augmentedPath.size()-1){
									after++;
								}
								if(j == augmentedPath.get(before) || 
										j == augmentedPath.get(after)){
									paintEdgeColor(coordinates.get(i), coordinates.get(j), true,color, g);
								}
							}
						}
						
						paintEdgeColor(coordinates.get(i), coordinates.get(j), false, color, g);
					}
				}
			}
		}
	}
}
