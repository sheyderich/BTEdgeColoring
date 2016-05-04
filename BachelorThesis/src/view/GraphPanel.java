package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import graphs.DrawableGraph;

/**
 * Displays a white Panel where the graph is drawn upon.  
 * @author Stephanie Heyderich
 * @version 29.04.2016
 */
@SuppressWarnings("serial")
public class GraphPanel extends JPanel {
	
	private DrawableGraph model;
	private Dimension dimension;
	
	/**
	 * Sets up a graph panel where the graph will be drawn upon. Based on given 
	 * parameters a dimension for the panel is used and the colors are set. 
	 * @param width
	 * @param height
	 * @param model
	 */
	public GraphPanel(int width, int height, DrawableGraph model){
		if (width <= 0 || height <= 0)
			throw new IllegalArgumentException("Panel has to be at least 1x1 Pixel.");
		this.model = model;
		this.dimension = new Dimension(width,height);
		setPreferredSize(dimension);
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
	}
	
	/**
	 * Draws the graph on the graphics context g
	 */
	public void paint(Graphics g){
		super.paint(g);
		System.out.println("vor here");
		if(model != null){
			System.out.println("here");
			model.paintGraph(g, this.getSize(dimension));
		}
	}
	
	/**
	 * Sets the internal model to another one when 
	 * a new graph is imported
	 * @param model
	 */
	public void setModel(DrawableGraph model) {
		this.model = model;
	}
	
}
