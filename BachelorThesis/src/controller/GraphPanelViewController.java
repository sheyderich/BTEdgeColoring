package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import algorithms.AlgorithmKoenig;
import graphs.BipartiteGraph;
import graphs.DrawableGraph;
import view.GraphPanelView;

/**
 * Controlls the Graph Panels by checking for mouse clicks on the import
 * and the start button and proceeds with the chosen algorithm when 
 * the button is pressed.  
 * @author Stephanie Heyderich
 * @version 29.04.2016
 */
public class GraphPanelViewController {
	
	private GraphPanelView graphPanelView;
	private DrawableGraph model;
	
	public GraphPanelViewController(){
		
		model = null;
		graphPanelView = new GraphPanelView(800,500,model);
		
		ActionListener startAlgorithm = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				AlgorithmKoenig.applyKoenigAlgorithm((BipartiteGraph)model);
			}
		};
		
		graphPanelView.getStartButton().addActionListener(startAlgorithm);
	}
	
	/**
	 * Sets the Graph that is drawn to a new one
	 * @param model
	 */
	public void setGraph(DrawableGraph model){
		this.model = model;
		graphPanelView.setModel(model);
		model.addObserver(graphPanelView);
		graphPanelView.repaint();
	}
	
	/**
	 * Returns the view
	 */
	public GraphPanelView getView(){
		return graphPanelView;
	}

}	
