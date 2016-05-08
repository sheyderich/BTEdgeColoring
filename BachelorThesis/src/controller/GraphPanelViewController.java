package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import algorithms.AlgorithmKoenig;
import graphReader.GraphReader;
import graphs.BipartiteGraph;
import graphs.DrawableGraph;
import graphs.Graph;
import view.GraphPanelView;

/**
 * Controlls the Graph Panels by checking for mouse clicks on the import
 * and the start/next/last button and proceeds with the chosen algorithm when 
 * the button is pressed.  
 * @author Stephanie Heyderich
 * @version 29.04.2016
 */
public class GraphPanelViewController {
	
	private GraphPanelView graphPanelView;
	private DrawableGraph model;
	private AlgorithmKoenig algo;
	private boolean started = false; 
	private Dimension dim = new Dimension(800,500);
	private final String WRONG_FILE = "Please choose a .txt file with a representation of a graph. The file should be "
			+ "build like this: \n\nFirst line: Vertex Number (bipartite graph: "
			+ "[Number of vertices in Set 1],[Number of vertices in Set 2])"
			+ "\nNext n lines: Edges (representation: [edge u] [edge v])";
	
	public GraphPanelViewController(){
		
		model = null;
		graphPanelView = new GraphPanelView((int)dim.getWidth(), (int)dim.getHeight(),model);
		algo = new AlgorithmKoenig();
		
		ActionListener startAlgorithm = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!started){
					started = true; 
					graphPanelView.getStartButton().setText("Next Step");
				}
				algo.applyAlgorithmStepwise((BipartiteGraph)model);
				
			}
		};
		
		graphPanelView.getStartButton().addActionListener(startAlgorithm);
		
		ActionListener lastStep = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				algo.undoLastColoring((BipartiteGraph)model);
			}
		};
		
		graphPanelView.getLastButton().addActionListener(lastStep);
		
		ActionListener importFile = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				final JFileChooser fc = new JFileChooser("C:\\Users\\Shey\\workspace\\Git\\BachelorThesis");
//				final JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(graphPanelView);
				if(result == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					if(file.getName().matches(".*.txt")){
						try{
							GraphReader gr = new GraphReader(file.getName());
							Graph g = gr.buildGraphFromFile();
							setGraph((DrawableGraph)g);
						}catch(Exception ex){
							JOptionPane.showMessageDialog(graphPanelView,WRONG_FILE, "Error",  JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(graphPanelView,WRONG_FILE,  "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};
		
		graphPanelView.getImportButton().addActionListener(importFile);
	}
	
	/**
	 * Sets the Graph that is drawn to a new one
	 * @param model
	 */
	public void setGraph(DrawableGraph newModel){
		this.model = newModel;
		model.addObserver(graphPanelView);
		graphPanelView.setModel(newModel);
		graphPanelView.repaint();
		algo = new AlgorithmKoenig();
	}
	
	/**
	 * Returns the view
	 */
	public GraphPanelView getView(){
		return graphPanelView;
	}

}	
