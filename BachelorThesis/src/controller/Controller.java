package controller;

import graphs.Graph;
import view.View;

public interface Controller {
	
	public View getView();
	public void setModel(Graph g); 

}
