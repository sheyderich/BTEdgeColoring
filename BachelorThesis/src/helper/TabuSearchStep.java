package helper;

import graphs.Graph;
import graphs.SimpleGraph;

public class TabuSearchStep implements AlgorithmStep {

	private int node1; 
	private int node2;
	private int color; 
	private int colorBefore; 
	
	public TabuSearchStep(int n1, int n2, int c, int cB){
		node1 = n1; 
		node2 = n2; 
		color = c; 
		colorBefore = cB; 
	}
	
	/**
	 * Realizes the TabuSearchStep
	 * @param g
	 */
	public void setStep(Graph g){
		if(g.isEdgeExistent(node1, node2)){
			g.setEdgeColor(node1, node2, color);
		}
	}
	
	@Override
	public void undo(Graph g) {
		if(g.isEdgeColored(node1, node2)){
			g.removeEdgeColor(node1, node2);
			if(colorBefore != SimpleGraph.UNCOLORED){
				g.setEdgeColor(node1, node2, colorBefore);
			}
		}
	}
	
	/**
	 * Checks whether two TabuSearchSteps are equal to each other. 
	 * @param other
	 * @return
	 */
	public boolean equals(TabuSearchStep other){
		if(other.getColor() == color && other.getNode1() == node1 && other.getNode2() == node2){
			return true; 
		}
		
		if(other.getColor() == color && other.getNode1() == node2 && other.getNode2() == node1){
			return true; 
		}
		
		return false;
	}

	/**
	 * @return the node1
	 */
	public int getNode1() {
		return node1;
	}

	/**
	 * @return the node2
	 */
	public int getNode2() {
		return node2;
	}

	/**
	 * @return the color
	 */
	public int getColor() {
		return color;
	}


}
