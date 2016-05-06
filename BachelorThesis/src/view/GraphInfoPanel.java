package view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import graphs.DrawableGraph;
import graphs.SimpleGraph;

/**
 * Displays a panel that consists of information about a given graph. 
 * Graph type, number of nodes, the adjacency matrix, max vertex degree, chromatic 
 * index bounds and the algorithm that is used currently on this graph are
 * displayed. 
 * @author Stephanie Heyderich
 * @version 29.04.2016
 */
@SuppressWarnings("serial")
public class GraphInfoPanel extends JPanel {
	
	private DrawableGraph model;
	
	/**
	 * Sets up a panel where information about the graph is shown. 
	 * @param width
	 * @param height
	 * @param model
	 */
	public GraphInfoPanel(int width, int height, DrawableGraph model){
		if (width <= 0 || height <= 0)
			throw new IllegalArgumentException("Panel has to be at least 1x1 Pixel.");
		
		this.model = model;
		setPreferredSize(new Dimension(width,height));
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setUpInfo();
	}
	
	/**
	 * Calls different functions that set up the info panel about the graph
	 */
	private void setUpInfo(){
		setUpFacts();
		setUpSeperation();
		setUpCalculations();
		setUpSeperation();
		setUpAlgorithmUsed();
	}
	
	/**
	 * Displays basic facts about the graph like type and number of nodes
	 */
	private void setUpFacts(){
		String type = new String(); 
		int vertexNumber = 0; 
		
		if(model != null){
			type = model.getType();
			vertexNumber = model.getVertexNumber();
		}
		
		JLabel typeLabel = new JLabel("  Type: " + type);
		this.add(typeLabel);
		JLabel nodes = new JLabel("  Number of Nodes: " + vertexNumber);
		this.add(nodes);
	}
	
	/**
	 * Provides an optical seperation between different layers of information
	 */
	private void setUpSeperation(){
		String seperation = new String("  ---------");
		JLabel sep = new JLabel(seperation);
		sep.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(sep);
	}
	
	/**
	 * Displays Calculations that were made on the graph
	 */
	private void setUpCalculations(){
		
		int maxVertexDegree = 0;
		int lowerBound = 0; 
		int upperBound = 0; 
		
		if(model != null){
			maxVertexDegree = ((SimpleGraph)model).calculateMaxVertexDegree();
			lowerBound = ((SimpleGraph)model).calculateLBChromaticIndex();
			upperBound = ((SimpleGraph)model).calculateUBChromaticIndex();
		}
		
		JLabel maxVertexLabel = new JLabel("  Max. Vertex Degree: " + maxVertexDegree);
		this.add(maxVertexLabel);
		JLabel lowerBoundLabel = new JLabel("  Chromatic Index Lower Bound: " + lowerBound);
		this.add(lowerBoundLabel);
		JLabel upperBoundLabel = new JLabel("  Chromatic Index Upper Bound: " + upperBound);
		this.add(upperBoundLabel);
	}
	
	
	/**
	 * Sets up the used algorithm 
	 */
	private void setUpAlgorithmUsed(){
		String algorithm = new String();
		String type = new String();
		if(model != null){
			type = model.getType();
		}
		switch(type){
		case "Simple graph": algorithm = "to be implemented...";
			break;
		case "Bipartite graph": algorithm = "König's Algorithm";
			break;
		default: algorithm = "No Algorithm implemented";
		}
		this.add(new JLabel("  Used " + algorithm));
	}
}
