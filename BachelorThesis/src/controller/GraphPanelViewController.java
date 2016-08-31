package controller;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import edgeAlgorithms.ColoringAlgorithms;
import edgeAlgorithmsConcrete.Greedy;
import edgeAlgorithmsConcrete.Koenig;
import edgeAlgorithmsConcrete.LineGraphDegreeDown;
import edgeAlgorithmsConcrete.LineGraphDegreeUp;
import edgeAlgorithmsConcrete.LineGraphGreedy;
import edgeAlgorithmsConcrete.OrderAPISearch;
import edgeAlgorithmsConcrete.OrderRANDOMSearch;
import edgeAlgorithmsConcrete.OrderSWAPSearch;
import edgeAlgorithmsConcrete.TabuSearchRandomStart;
import edgeAlgorithmsConcrete.TabuSearchUnicolorStart;
import graphReader.GraphReader;
import graphs.BipartiteGraph;
import graphs.DrawableGraph;
import graphs.Graph;
import view.BigGraphPanelView;
import view.GraphPanelView;
import view.View;

/**
 * Controlls the Graph Panels by checking for mouse clicks on the import
 * and the start/next/last button and proceeds with the chosen algorithm when 
 * the button is pressed.  
 * @author Stephanie Heyderich
 */
public class GraphPanelViewController extends Controller{
	
//	private View view;
//	private DrawableGraph model;
	private Frame frame; 
	private ColoringAlgorithms usedAlgorithm;
	private boolean started = false; 
	private Dimension dim = new Dimension(800,500);
	private List<String> algorithms = new ArrayList<String>(); 
	private final String WRONG_FILE = "Please choose a .txt file with a representation of a graph. The file should be "
			+ "build like this: \n\nFirst line: Vertex Number (bipartite graph: "
			+ "[Number of vertices in Set 1],[Number of vertices in Set 2])"
			+ "\nNext n lines: Edges (representation: [edge u] [edge v])";
	
	
	/**
	 * Creates a list of algorithms that can be used and sets up the 
	 * ActionListener
	 */
	public GraphPanelViewController(Frame frame){
		model = null;
		this.frame = frame; 
		this.view = new GraphPanelView((int)dim.getWidth(), (int)dim.getHeight(), (DrawableGraph)model);
		setUpAlgorithms(); 
		setUpImportFunction();
		setUpStartButton();
		setUpUndoButton();
		setUpFinalColoringButton();
		setUpResetButton();
	}
	
	/**
	 * Sets the Graph that is drawn to a new one and 
	 * repaints the view to display it. Furthermore, 
	 * the algorithms that can be used on the new model
	 * are determined and set to the Combo-Box.
	 * @param model
	 */
	public void setModel(Graph newModel){
		super.setModel(newModel);
		this.model = newModel;
		((DrawableGraph)model).addObserver(view);
		view.setModel(newModel);
		view.repaint();
		setAlgorithmsToUse((DrawableGraph)newModel);
	}
	
	/**
	 * 
	 */
	public void setView(View newView){
		frame.remove(view);
		this.view = newView;
		frame.add(view);
		setUpAlgorithms(); 
		setUpImportFunction();
		setUpStartButton();
		setUpUndoButton();
		setUpFinalColoringButton();
		setUpResetButton();
	}
	
	/**
	 * Needed to set the Line Graph as a new model without
	 * resetting the algorithm
	 * @param newModel
	 */
	public void setModelLineGraph(Graph newModel){
		this.model = newModel;
		((DrawableGraph)model).addObserver(view);
		view.setModel((Graph)newModel);
		view.repaint();
	}
	
	/**
	 * Sets the available algorithms into the list of algorithms
	 * Also determine the names that are displayed in the view 
	 * where the user can choose one of them 
	 */
	private void setUpAlgorithms(){
		algorithms.add("Greedy Algorithm");
		algorithms.add("Randomized Greedy");
		algorithms.add("LSA SWAP");
		algorithms.add("LSA API");
		algorithms.add("TSA Random Start");
		algorithms.add("TSA Unicolor Start");
		algorithms.add("LG Greedy");
		algorithms.add("LG Degrees Down");
		algorithms.add("LG Degrees Up");
	}
	
	/**
	 * Returns a new EdgeColoringAlgorithm based on the given name
	 * of an algorithm.
	 * Throws a RuntimeException if the algorithm is unknown
	 * @param algorithmName
	 * @return
	 */
	private ColoringAlgorithms getAlgorithm(String algorithmName) {
		switch(algorithmName){
		case "König's Algorithm": return new Koenig();
		case "Greedy Algorithm": return new Greedy(model.getEdges());
		case "Randomized Greedy": return new OrderRANDOMSearch();
		case "LSA SWAP": return new OrderSWAPSearch();
		case "LSA API": return new OrderAPISearch(); 
		case "TSA Random Start": return new TabuSearchRandomStart();
		case "TSA Unicolor Start": return new TabuSearchUnicolorStart();
		case "LG Greedy": return new LineGraphGreedy(this, (Graph)model);
		case "LG Degrees Down": return new LineGraphDegreeDown(this, (Graph)model);
		case "LG Degrees Up": return new LineGraphDegreeUp(this, (Graph)model);
		default: System.out.println("Not yet implemented");
		return new Greedy(model.getEdges());
		}
	}
	
	/**
	 * Sets the ComboBox with all algorithms that can be used
	 * and sets the used algorithm from the ComboBox
	 * as the globally used algorithm
	 * @param model
	 */
	private void setAlgorithmsToUse(DrawableGraph model){
		checkBipartite(model);
		configureComboBox();
		addActionListener();
	}
	
	/**
	 * Adds the Algorithm of König to the list of applicable 
	 * Algorithms if the graph is a bipartite graph
	 * @param model
	 */
	private void checkBipartite(DrawableGraph model){
		if(model instanceof BipartiteGraph){
			if(!algorithms.get(0).equals("König's Algorithm"))
					algorithms.add(0, "König's Algorithm");
		}else{
			if(algorithms.get(0).equals("König's Algorithm")){
				algorithms.remove(0);
			}
		}
	}
	
	/**
	 * Fills the ComboBox with the applicable algorithms and 
	 * selects a default algorithm for the given model if the
	 * user does not select one
	 */
	private void configureComboBox(){
		view.setChooseAlgorithm(algorithms.toArray());
		JComboBox<Object> cb = view.getChooseAlgorithm();
		usedAlgorithm = getAlgorithm((String)cb.getSelectedItem());
		view.setAlgorithm((String)cb.getSelectedItem());
	}
	
	/**
	 * Determines what happens if the JComboBox is used and 
	 * an algorithm is selected. 
	 * The source of the action event is used to create a new
	 * algorithm based on the user's choice and the algorithm is 
	 * set. The model is uncolored to prevent algorithm mixes. 
	 */
	private void addActionListener(){
		ActionListener chooseAlg = new ActionListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox<String> cb = (JComboBox<String>)e.getSource();
		        String algorithmName = (String)cb.getSelectedItem();
		        usedAlgorithm = getAlgorithm(algorithmName);
		        ((Graph)getModel()).uncolor();
		        view.setAlgorithm(algorithmName);
			}
		};
		JComboBox<Object> cb = view.getChooseAlgorithm();
		cb.addActionListener(chooseAlg);
	}
	
	/**
	 * Returns the view
	 */
	public View getView(){
		return view;
	}
	
	/**
	 * Returns the model
	 * @return
	 */
	private Graph getModel(){
		return model;
	}
	
	/**
	 * Creates a dialog for choosing a file to import a graph from. 
	 * It is checked whether the file has the right format and a dialog
	 * is set up otherwise, showing an error message. 
	 */
	private void setUpImportFunction(){
		ActionListener importFile = new ActionListener(){
			
			public void actionPerformed(ActionEvent e){
				final JFileChooser fc = new JFileChooser("C:\\Users\\Shey\\git\\BTEdgeColoring\\BachelorThesis");
//				final JFileChooser fc = new JFileChooser();
				int result = fc.showOpenDialog(view);
				if(result == JFileChooser.APPROVE_OPTION){
					File file = fc.getSelectedFile();
					if(file.getName().matches(".*.txt")){
						try{
							GraphReader gr = new GraphReader(file.getName());
							Graph g = gr.buildGraphFromFile();
							setModel(g);
						}catch(Exception ex){
							ex.printStackTrace();
							JOptionPane.showMessageDialog(view,WRONG_FILE, "Error",  JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(view,WRONG_FILE,  "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			
		};
		view.getImportButton().addActionListener(importFile);
	}
	
	/**
	 * Sets up the start button and sets the text on it
	 * to "next step". 
	 */
	private void setUpStartButton(){
		ActionListener startAlgorithm = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				if(view instanceof GraphPanelView){
					if(!started){
						started = true; 
						view.getStartButton().setText("Next Step");
					}
					usedAlgorithm.applyAlgorithmStepwise((Graph)model);
				}
				
				if(view instanceof BigGraphPanelView){
					usedAlgorithm.applyAlgorithmComplete((Graph)model);
				}
			}
		};
		
		view.getStartButton().addActionListener(startAlgorithm);
	}
	
	/**
	 * Sets up a button to undo actions. 
	 */
	private void setUpUndoButton(){
		ActionListener lastStep = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				usedAlgorithm.undoLastColoring((Graph)model);
			}
		};
		JButton last = view.getLastButton(); 
		if(last != null)
			view.getLastButton().addActionListener(lastStep);
	}
	
	/**
	 * Sets up a button to complete the algorithm with
	 * one step.
	 */
	private void setUpFinalColoringButton(){
		ActionListener completeAlg = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				usedAlgorithm.applyAlgorithmComplete((Graph)model);
			}
		};
		
		JButton complete = view.getCompleteButton(); 
		if(complete != null)
			view.getCompleteButton().addActionListener(completeAlg);
	}
	
	/**
	 * Sets up a button to reset the algorithm and
	 * uncolor the graph. 
	 */
	private void setUpResetButton(){
		ActionListener resetAlg = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				usedAlgorithm.resetColoring((Graph)model);
			}
		};
		JButton reset = view.getResetButton(); 
		if(reset != null)
			view.getResetButton().addActionListener(resetAlg);
	}

}	
