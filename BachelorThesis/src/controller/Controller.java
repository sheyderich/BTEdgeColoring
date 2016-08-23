package controller;

import graphs.Graph;
import view.View;
/**
 * An interface for the two different controllers of the
 * programm 
 * @author Stephanie Heyderich
 */
public interface Controller {
	
	public View getView();
	public void setModel(Graph g); 

}
