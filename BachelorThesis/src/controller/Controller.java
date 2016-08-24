package controller;

import javax.swing.JOptionPane;

import graphs.DrawableGraph;
import graphs.Graph;
import view.BigGraphPanelView;
import view.GraphPanelView;
import view.View;
/**
 * An interface for the two different controllers of the
 * programm 
 * @author Stephanie Heyderich
 */
public abstract class Controller {
	
	protected View view; 
	protected Graph model; 
	
	public abstract View getView();
	public abstract void setView(View v); 
	
	/**
	 * Checks what view is possible for the graph that is chosen. If the maximum vertex degree is <20
	 * the visualization based view is offered, if the maximum vertex degree is > 20 the text based
	 * view is offered.  
	 * @param g
	 */
	public void setModel(Graph g){
		
		if(view instanceof GraphPanelView && g.calculateMaxVertexDegree() > 20){
			Object[] options = {"YES", "NO", "CANCEL"};
			int n = JOptionPane.showOptionDialog(null, "You chose a graph that has a maximal vertex degree of > 20. A visualization is not possible. \nDo you want to switch "
					+ "to the text-based view?", "", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
			if(n == 0){
				setView(new BigGraphPanelView(5,5, model)); 
			}else if(n==1){
				setView(new GraphPanelView(800,500,(DrawableGraph)model)); 
			}
		}
		
		if(view instanceof BigGraphPanelView && g.calculateMaxVertexDegree() < 20){
			Object[] options = {"YES", "NO", "CANCEL"};
			int n = JOptionPane.showOptionDialog(null, "You chose a graph that has a maximal vertex degree of < 20. A visualization is possible. \nDo you want to switch "
					+ "to the other view?", "", 
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
			if(n == 0){
				setView(new GraphPanelView(800,500, (DrawableGraph)model)); 
			}else if(n==1){
				setView(new BigGraphPanelView(800,500,model)); 
			}
		}
		
	}

}
