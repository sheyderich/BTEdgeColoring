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

import edgeAlgorithms.ColoringAlgorithm;
import edgeAlgorithms.Greedy;
import edgeAlgorithms.Koenig;
import edgeAlgorithms.LineGraphAlgorithms;
import edgeAlgorithms.LineGraphGreedy;
import edgeAlgorithms.LocalSearchGreedy;
import edgeAlgorithms.RandomSearchGreedy;
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
	private ColoringAlgorithm usedAlgorithm;
	private boolean started = false; 
	private Dimension dim = new Dimension(800,500);
	private final String WRONG_FILE = "Please choose a .txt file with a representation of a graph. The file should be "
			+ "build like this: \n\nFirst line: Vertex Number (bipartite graph: "
			+ "[Number of vertices in Set 1],[Number of vertices in Set 2])"
			+ "\nNext n lines: Edges (representation: [edge u] [edge v])";
	private List<String> algorithms = new ArrayList<String>(); 
	
	public GraphPanelViewController(){
		
		model = null;
		graphPanelView = new GraphPanelView((int)dim.getWidth(), (int)dim.getHeight(),model);
		
		algorithms.add("Greedy Algorithm");
		algorithms.add("Randomized Search Algorithm Greedy");
		algorithms.add("Local Search Algorithm Greedy");
		algorithms.add("Line Graph Greedy");
		
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
							ex.printStackTrace();
							JOptionPane.showMessageDialog(graphPanelView,WRONG_FILE, "Error",  JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(graphPanelView,WRONG_FILE,  "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		};
		
		graphPanelView.getImportButton().addActionListener(importFile);
		
		ActionListener startAlgorithm = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!started){
					started = true; 
					graphPanelView.getStartButton().setText("Next Step");
				}
				usedAlgorithm.applyAlgorithmStepwise((Graph)model);
			}
		};
		
		graphPanelView.getStartButton().addActionListener(startAlgorithm);
		
		ActionListener lastStep = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				usedAlgorithm.undoLastColoring((Graph)model);
			}
		};
		
		graphPanelView.getLastButton().addActionListener(lastStep);
		
		ActionListener completeAlg = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				usedAlgorithm.applyAlgorithmComplete((Graph)model);
			}
		};
		
		graphPanelView.getCompleteButton().addActionListener(completeAlg);
		
		ActionListener resetAlg = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				usedAlgorithm.resetColoring((Graph)model);
			}
		};
		
		graphPanelView.getResetButton().addActionListener(resetAlg);

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
		setAlgorithmsToUse(newModel);
	}
	
	/**
	 * Sets the ComboBox with all algorithms that can be used
	 * and sets the used algorithm from the ComboBox
	 * as the globally used algorithm
	 * @param model
	 */
	private void setAlgorithmsToUse(DrawableGraph model){
		
		if(model instanceof BipartiteGraph){
			if(!algorithms.get(0).equals("König's Algorithm"))
					algorithms.add(0, "König's Algorithm");
		}else{
			if(algorithms.get(0).equals("König's Algorithm")){
				algorithms.remove(0);
			}
		}
		
		graphPanelView.setChooseAlgorithm(algorithms.toArray());
		JComboBox<Object> cb = graphPanelView.getChooseAlgorithm();
		
		
		//default algorithm
		usedAlgorithm = getAlgorithm((String)cb.getSelectedItem());
		graphPanelView.setAlgorithm((String)cb.getSelectedItem());
		
		ActionListener chooseAlg = new ActionListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
		        String algorithmName = (String)cb.getSelectedItem();
		        usedAlgorithm = getAlgorithm(algorithmName);
		        ((Graph)getModel()).uncolor();
		        graphPanelView.setAlgorithm(algorithmName);
			}
		};
		cb.addActionListener(chooseAlg);
		
	}
	
	/**
	 * Returns a new EdgeColoringAlgorithm based on the given name
	 * of an algorithm.
	 * Throws a RuntimeException if the algorithm is unknown
	 * @param algorithmName
	 * @return
	 */
	private ColoringAlgorithm getAlgorithm(String algorithmName) {
		switch(algorithmName){
		case "König's Algorithm": return new Koenig();
		case "Greedy Algorithm": return new Greedy(model.getEdges());
		case "Randomized Search Algorithm Greedy": return new RandomSearchGreedy();
		case "Local Search Algorithm Greedy": return new LocalSearchGreedy();
		case "Line Graph Greedy": return new LineGraphGreedy(this, (Graph)model);
		default: System.out.println("Not yet implemented");
		return new Greedy(model.getEdges());
		}
	}
	
	/**
	 * Needed to stay with the LineGraphAlgorithm 
	 * @param alg
	 */
	public void setLineGraphAlgorithm(LineGraphAlgorithms alg){
		this.usedAlgorithm = alg; 
        graphPanelView.setAlgorithm("Line Graph Greedy");
	}
	
	/**
	 * Returns the view
	 */
	public GraphPanelView getView(){
		return graphPanelView;
	}
	
	/**
	 * Returns the model
	 * @return
	 */
	private DrawableGraph getModel(){
		return model;
	}

}	
