package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
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
 */
@SuppressWarnings("serial")
public class GraphInfoPanel extends JPanel {
	
	private DrawableGraph model;
	private JLabel type;
	private JLabel vertexCount;
	private JLabel vertexDegree;
	private JLabel lBound;
	private JLabel uBound;
	private JLabel usedAlgorithm;
	private JLabel numberOfColors;
	
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
		JLabel desc = new JLabel("  Description");
		desc.setFont(new Font("Times New Roman", Font.BOLD , 14));
		this.add(desc);
		JLabel empty1 = new JLabel("");
		this.add(empty1);
		JLabel typeLabel = new JLabel("  Graph Type:");
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
		JLabel empty2 = new JLabel("");
		this.add(empty2);
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
		JLabel algorithm = new JLabel("  Algorithm");
		algorithm.setFont(new Font("Times New Roman", Font.BOLD , 14));
		this.add(algorithm);
		JLabel empty3 = new JLabel("");
		this.add(empty3);
		
		JLabel uAlgorithm = new JLabel("  Algorithm:");
		uAlgorithm.setFont(font);
		this.add(uAlgorithm);
		usedAlgorithm = new JLabel("--");
		usedAlgorithm.setFont(font);
		this.add(usedAlgorithm);
		
		JLabel colorLabel = new JLabel("  Number of Colors:");
		colorLabel.setFont(font);
		this.add(colorLabel);
		numberOfColors = new JLabel("--");
		numberOfColors.setFont(font);
		this.add(numberOfColors);
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
			numberOfColors.setText(String.valueOf(((SimpleGraph)model).getQuantityColors()));
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
	
	/**
	 * Used to set the algorithm from the controller
	 * @param s
	 */
	public void setAlgorithm(String s){
		usedAlgorithm.setText(s);
	}
	
	
	/**
	 * Sets up the Infos based on the current State of the graph
	 */
	public void paint(Graphics g){
		super.paint(g);
		setUpInfoLabels();
	}
}
