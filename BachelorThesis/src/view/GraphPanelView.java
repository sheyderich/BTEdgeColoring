package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import graphs.DrawableGraph;

/**
 * Contains a Graph panel and a graph info panel as well as a small menu for
 * the import and the start algorithm button.  
 * @author Stephanie Heyderich
 * @version 29.04.2016
 */
@SuppressWarnings("serial")
public class GraphPanelView extends JPanel implements Observer{
	
	private GraphPanel graphPanel;
	private GraphInfoPanel graphInfoPanel;
	private JButton startButton; 
	private JButton importButton;
	private Dimension dimension;
	// TO DO: SwitchBox for algorithms
	
	/**
	 * Constructs the view that holds the graph panel, the graph info panel and 
	 * a menu where the algorithm can be started or a graph imported
	 * @param width
	 * @param height
	 * @param model
	 */
	public GraphPanelView(int width, int height, DrawableGraph model){
		
		if(height < 1 || width < 1){
			throw new IllegalArgumentException("GraphPanelView has to be at least 1x1");
		}
		
		this.setLayout(new BorderLayout());
		dimension = new Dimension(width, height);
		graphPanel = new GraphPanel(width, height, model);
		graphPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		graphInfoPanel = new GraphInfoPanel(width/3, height, model);
		this.add(graphPanel, BorderLayout.CENTER);
		this.add(graphInfoPanel, BorderLayout.WEST);
		JPanel menu = new JPanel(new GridLayout(1,0));
		this.add(menu, BorderLayout.SOUTH);
		startButton = new JButton("Start Algorithm");
		importButton = new JButton("Import Graph");
		menu.add(importButton);
		menu.add(startButton);
	}
	
	/**
	 * Returns the start button
	 * @return
	 */
	public JButton getStartButton(){
		return startButton;
	}
	
	/**
	 * Returns the Import button
	 * @return
	 */
	public JButton getImportButton(){
		return importButton;
	}
	
	/**
	 * Sets the model to the given one and updates the graphInfoPanel as
	 * well as the graphPanel to draw the model.
	 * @param model
	 */
	public void setModel(DrawableGraph model) {
		graphPanel.setModel(model);
		graphInfoPanel = new GraphInfoPanel((int)(dimension.getWidth()/3), ((int) dimension.getHeight()), model);
		this.add(graphInfoPanel, BorderLayout.WEST);
	}
	
	/**
	 * Repaints the graphPanel when the model notifies it and  updates the 
	 * graphInfoPanel.
	 */
	@Override
	public void update(Observable o, Object arg) {
		graphPanel.repaint();
		System.out.println("hi");
	}
	
}
