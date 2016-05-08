package controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import algorithms.AlgorithmKoenig;
import algorithms.EdgeColoringAlgorithm;
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
 * @version 08.05.2016
 */
public class GraphPanelViewController {
	
	private GraphPanelView graphPanelView;
	private DrawableGraph model;
	private EdgeColoringAlgorithm usedAlgorithm;
	private boolean started = false; 
	private Dimension dim = new Dimension(800,500);
	private final String WRONG_FILE = "Please choose a .txt file with a representation of a graph. The file should be "
			+ "build like this: \n\nFirst line: Vertex Number (bipartite graph: "
			+ "[Number of vertices in Set 1],[Number of vertices in Set 2])"
			+ "\nNext n lines: Edges (representation: [edge u] [edge v])";
	
	public GraphPanelViewController(){
		
		model = null;
		graphPanelView = new GraphPanelView((int)dim.getWidth(), (int)dim.getHeight(),model);
		
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
							setModel((DrawableGraph)g);
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
	public void setModel(DrawableGraph newModel){
		this.model = newModel;
		model.addObserver(graphPanelView);
		graphPanelView.setModel(newModel);
		graphPanelView.repaint();
		setActionListener();
		setAlgorithmsToUse(newModel);
//		usedAlgorithm = new AlgorithmKoenig();
	}
	
	/**
	 * Sets the action listener for the start and undo button
	 * as long as the model is not null
	 */
	private void setActionListener(){
		if(model != null){
			ActionListener startAlgorithm = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(!started){
						started = true; 
						graphPanelView.getStartButton().setText("Next Step");
					}
					usedAlgorithm.applyAlgorithmStepwise((BipartiteGraph)model);
					
				}
			};
			
			graphPanelView.getStartButton().addActionListener(startAlgorithm);
			
			ActionListener lastStep = new ActionListener(){
				public void actionPerformed(ActionEvent e){
					usedAlgorithm.undoLastColoring((BipartiteGraph)model);
				}
			};
			
			graphPanelView.getLastButton().addActionListener(lastStep);
		}
	}
	
	/**
	 * Sets the ComboBox with all algorithms that can be used
	 * and sets the used algorithm from the ComboBox
	 * as the globally used algorithm
	 * @param model
	 */
	private void setAlgorithmsToUse(DrawableGraph model){
		List<String> algorithms = new ArrayList<String>();
		algorithms.add("Greedy Algorithm");
		algorithms.add("Local Search Algorithm");
		algorithms.add("Line Graph Algorithm");
		if(model instanceof BipartiteGraph){
			algorithms.add(0, "König's Algorithm");
		}
		for(String s: algorithms){
			graphPanelView.getChooseAlgorithm().addItem(s);
		}
		//default algorithm
		usedAlgorithm = getAlgorithm(algorithms.get(0));
		
		ActionListener chooseAlg = new ActionListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
		        String algorithmName = (String)cb.getSelectedItem();
		        usedAlgorithm = getAlgorithm(algorithmName);
			}
		};
		graphPanelView.getChooseAlgorithm().addActionListener(chooseAlg);
		
	}
	
	/**
	 * Returns a new EdgeColoringAlgorithm based on the given name
	 * of an algorithm.
	 * Throws a RuntimeException if the algorithm is unknown
	 * @param algorithmName
	 * @return
	 */
	private EdgeColoringAlgorithm getAlgorithm(String algorithmName) {
		switch(algorithmName){
		case "König's Algorithm": return new AlgorithmKoenig();
		default: throw new RuntimeException("Not yet implemented");
		}
	}
	
	/**
	 * Returns the view
	 */
	public GraphPanelView getView(){
		return graphPanelView;
	}

}	
