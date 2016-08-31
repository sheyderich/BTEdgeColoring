package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import graphs.DrawableGraph;
import graphs.Graph;
import graphs.LineGraph;

/**
 * Contains a Graph panel and a graph info panel as well as a small menu for
 * the import and the start algorithm button.  
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class GraphPanelView extends View implements Observer{
	
	private JFrame lgFrame; 
	private GraphPanel lgPanel; 
	private GraphPanel graphPanel;
	private GraphInfoPanel graphInfoPanel;
	private JButton startButton; 
	private JButton importButton;
	private JButton lastButton;
	private JButton completeButton;
	private JButton resetButton;
	private JComboBox<Object> chooseAlgorithm;
	private JPanel menu;
	private DrawableGraph model;
	
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
		this.model = model;
		this.setLayout(new BorderLayout());
		this.lgFrame = new JFrame(); 
		this.lgPanel = new GraphPanel(width,height, model);
		lgPanel.setPreferredSize(new Dimension(width, height));
		lgFrame.add(lgPanel);
		lgFrame.setSize(new Dimension(width, height));
		lgFrame.setLocationRelativeTo(this);
		lgFrame.setVisible(false);
		graphPanel = new GraphPanel(width, height, model);
		graphInfoPanel = new GraphInfoPanel(width/3, height, model);
		setUpLayout(); 
		setUpButton(); 
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
	 * Returns the last button
	 * @return
	 */
	public JButton getLastButton(){
		return lastButton;
	}
	
	/**
	 * Returns the complete button
	 * @return
	 */
	public JButton getCompleteButton(){
		return completeButton;
	}
	
	/**
	 * Returns the reset button
	 * @return
	 */
	public JButton getResetButton(){
		return resetButton;
	}
	
	/**
	 * Returns the ComboBox that chooses the algorithm
	 * @return
	 */
	public JComboBox<Object> getChooseAlgorithm(){
		return chooseAlgorithm;
	}
	
	/**
	 * Sets the algorithms that can be used by
	 * taking the algorithm list from the controller
	 * and adding it to the JComboBox
	 * @param algos
	 */
	public void setChooseAlgorithm(Object[] algos){
		menu.remove(chooseAlgorithm);
		chooseAlgorithm = new JComboBox<Object>(algos);
		menu.add(chooseAlgorithm, 0,0);
	}
	
	/**
	 * Sets the model to the given one and updates the graphInfoPanel as
	 * well as the graphPanel to draw the model.
	 * @param model
	 */
	public void setModel(Graph newModel) {
		graphPanel.setModel((DrawableGraph)newModel);
		graphInfoPanel.setModel((DrawableGraph)newModel);
		this.model = (DrawableGraph)newModel;
		if(newModel instanceof LineGraph){
			lgFrame.setVisible(true);
			lgPanel.setModel((DrawableGraph)((LineGraph)newModel).getOriginal());
		}else{
			lgFrame.setVisible(false);
		}
		this.repaint();
	}
	
	/**
	 * Repaints the graphPanel when the model notifies it and  updates the 
	 * graphInfoPanel.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(model != null){
			if(model.getQuantityColors() > 20){
				JOptionPane.showMessageDialog(this, "There are only 20 colors. Your graph uses too many.",  "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		graphPanel.repaint();
		graphInfoPanel.repaint();
		lgPanel.repaint();
	}
	
	/**
	 * Set the algorithm in the graphInfoPanel 
	 * from the controller. 
	 * @param s
	 */
	public void setAlgorithm(String s){
		graphInfoPanel.setAlgorithm(s);
	}
	
	/**
	 * Sets up Layouts. 
	 */
	private void setUpLayout(){
		this.add(graphPanel, BorderLayout.CENTER);
		this.add(graphInfoPanel, BorderLayout.WEST);
		menu = new JPanel(new GridLayout(1,0));
		this.add(menu, BorderLayout.SOUTH);
	}
	
	/**
	 * Sets all the button in the Panel View. 
	 */
	private void setUpButton(){
		startButton = new JButton("Start Algorithm");
		importButton = new JButton("Import Graph");
		lastButton = new JButton("Undo Last Coloring");
		completeButton = new JButton("Final Coloring");
		resetButton = new JButton ("Reset Graph");
		chooseAlgorithm = new JComboBox<Object>();
		menu.add(chooseAlgorithm);
		menu.add(importButton);
		menu.add(startButton);
		menu.add(lastButton);
		menu.add(completeButton);
		menu.add(resetButton);
	}
	
}
