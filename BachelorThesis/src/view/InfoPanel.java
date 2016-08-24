package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;

import graphs.Graph;
import graphs.SimpleGraph;
/**
 * Displays information about the graph and the algorithm when 
 * no visualization is needed for the graph
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class InfoPanel extends JPanel {
	private Graph model;
	private JPanel algInformation;
	private JPanel graphInformation;
	private JLabel edges;
	private JLabel vertices;
	private JLabel vertexDegree;
	private JLabel density;
	private JLabel usedAlgorithm;
	private JLabel numberColors;
	private JLabel deviation;

	public InfoPanel(Graph model) {

		this.model = model;
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		this.setLayout(new GridLayout(0, 1, 0, 20));
		setUpBlanks();
		setUpInfoLabels();
	}

	/**
	 * Sets up Labels for information that is filled out later about the model
	 * graph.
	 */
	private void setUpBlanks() {
		graphInformation = new JPanel();
		graphInformation.setForeground(Color.BLACK);
		graphInformation.setBackground(Color.WHITE);
		graphInformation.setLayout(new GridLayout(0, 2));
		graphInformation.add(new JLabel("Graph"));
		graphInformation.add(new JLabel(""));
		graphInformation.add(new JLabel("Vertices:"));
		vertices = new JLabel("--");
		graphInformation.add(vertices);
		graphInformation.add(new JLabel("Edges:"));
		edges = new JLabel("--");
		graphInformation.add(edges);
		graphInformation.add(new JLabel("Max Vertex Degree:"));
		vertexDegree = new JLabel("--");
		graphInformation.add(vertexDegree);
		graphInformation.add(new JLabel("Density:"));
		density = new JLabel("--");
		graphInformation.add(density);
		this.add(graphInformation);

		algInformation = new JPanel();
		algInformation.setForeground(Color.BLACK);
		algInformation.setBackground(Color.WHITE);
		algInformation.setLayout(new GridLayout(0, 2, 0, 0));
		algInformation.add(new JLabel("Algorithm:"));
		usedAlgorithm = new JLabel("--");
		algInformation.add(usedAlgorithm);
		algInformation.add(new JLabel("Number Colors:"));
		numberColors = new JLabel("--");
		algInformation.add(numberColors);
		algInformation.add(new JLabel("Deviation from Vizing:"));
		deviation = new JLabel("--");
		algInformation.add(deviation);
		algInformation.add(new JLabel(""));
		algInformation.add(new JLabel(""));
		algInformation.add(new JLabel(""));
		algInformation.add(new JLabel(""));
		this.add(algInformation);
	}

	/**
	 * Fills in information about the graph like type, vertex number, maximum
	 * vertex degree and chromatic Index bounds.
	 */
	private void setUpInfoLabels() {
		if (model != null) {
			DecimalFormat f = new DecimalFormat("#0.00");
			vertices.setText(String.valueOf(model.getVertexNumber()));
			edges.setText(String.valueOf(model.getEdgeNumber()));
			vertexDegree.setText(String.valueOf(((SimpleGraph) model).calculateMaxVertexDegree()));
			density.setText(String.valueOf(f.format(getDensity())));
			numberColors.setText(String.valueOf(((SimpleGraph) model).getQuantityColors()));
			f = new DecimalFormat("#0");
			deviation.setText(String.valueOf(f.format(getDeviation()))+"%");
		}
	}

	/**
	 * Calculates the deviation from the optimal result
	 * 
	 * @return
	 */
	private double getDeviation() {
		double delta = model.calculateMaxVertexDegree();
		double colors = model.getQuantityColors();
		return ((colors - delta) / delta) * 100;
	}

	/**
	 * calculates the density of the graph
	 * 
	 * @return
	 */
	private double getDensity() {
		double n = model.getVertexNumber();
		double m = model.getEdgeNumber();
		return (double) (m / ((n * (n - 1)) / 2.0));
	}

	public void setModel(Graph model) {
		this.model = model;
		setUpInfoLabels();
	}

	/**
	 * Used to set the algorithm from the controller
	 * 
	 * @param s
	 */
	public void setAlgorithm(String s) {
		usedAlgorithm.setText(s);
	}

	/**
	 * Sets up the Infos based on the current State of the graph
	 */
	public void paint(Graphics g) {
		super.paint(g);
		setUpInfoLabels();
	}

}
