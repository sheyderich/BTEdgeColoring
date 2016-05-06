package graphs;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;

public class BipartiteGraph extends SimpleGraph{
	
	private int vertexCount1;
	private int vertexCount2;
	
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
				xCoordinate += distanceBetweenFirstSet;
			}
			
			xCoordinate = 25 + distanceBetweenSecondSet/2;
			for(int i = 0; i < vertexCount2; i++){
				coordinates.add(new Point((int)xCoordinate, (int)yCoordinate2Row));
				xCoordinate += distanceBetweenSecondSet;
			}
			
		}
		return coordinates;
	}
}
