package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import graphs.DrawableGraph;
import graphs.SimpleGraph;

/**
 * Displays a panel that consists of information about a given graph. 
 * Graph type, number of nodes, the adjacency matrix, max vertex degree, chromatic 
 * index bounds and the algorithm that is used currently on this graph are
 * displayed. 
 * @author Stephanie Heyderich
 * @version 7.05.2016
 */
@SuppressWarnings("serial")
public class GraphInfoPanel extends JPanel {
	
	private DrawableGraph model;
	private JLabel type;
	private JLabel vertexCount;
	private JLabel vertexDegree;
	private JLabel lBound;
	private JLabel uBound;
	
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
		this.setLayout(new GridLayout(0,2));
		setUpBlanks();
		setUpInfoLabels();
	}
	
	/**
	 * Sets up Labels for information that is filled out later 
	 * about the model graph. 
	 */
	private void setUpBlanks(){
		Font font = new Font("Times New Roman", Font.ITALIC, 14);
		JLabel typeLabel = new JLabel("  Type:");
		typeLabel.setFont(font);
		this.add(typeLabel);
		type = new JLabel("--");
		type.setFont(font);
		this.add(type);
		JLabel nodes = new JLabel("  Number of Nodes:");
		nodes.setFont(font);
		this.add(nodes);
		vertexCount = new JLabel("--");
		vertexCount.setFont(font);
		this.add(vertexCount);
		JLabel maxVertexLabel = new JLabel("  Max. Vertex Degree:");
		maxVertexLabel.setFont(font);
		this.add(maxVertexLabel);
		vertexDegree = new JLabel("--");
		vertexDegree.setFont(font);
		this.add(vertexDegree);
		JLabel chromIn = new JLabel("  Chromatic Index");
		chromIn.setFont(new Font("Times New Roman", Font.BOLD , 14));
		this.add(chromIn);
		JLabel empty = new JLabel("");
		this.add(empty);
		JLabel lowerBoundLabel = new JLabel("  Lower Bound:" );
		lowerBoundLabel.setFont(font);
		this.add(lowerBoundLabel);
		lBound = new JLabel("--");
		lBound.setFont(font);
		this.add(lBound);
		JLabel upperBoundLabel = new JLabel("  Upper Bound:");
		upperBoundLabel.setFont(font);
		this.add(upperBoundLabel);
		uBound = new JLabel("--");
		uBound.setFont(font);
		this.add(uBound);
	}
	
	/**
	 * Fills in information about the graph like type, vertex number, 
	 * maximum vertex degree and chromatic Index bounds.
	 */
	private void setUpInfoLabels(){
		if(model != null){
			type.setText(model.getType());
			vertexCount.setText(String.valueOf(model.getVertexNumber()));
			vertexDegree.setText(String.valueOf(((SimpleGraph)model).calculateMaxVertexDegree()));
			lBound.setText(String.valueOf(((SimpleGraph)model).calculateLBChromaticIndex()));
			uBound.setText(String.valueOf(((SimpleGraph)model).calculateUBChromaticIndex()));
		}
	}
	
	/**
	 * Sets the old model to another given one 
	 * and resets the Info Labels to the new Values
	 * @param model
	 */
	public void setModel(DrawableGraph model){
		this.model = model; 
		setUpInfoLabels();
	}
}
