package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import graphs.Graph;
/**
 * View of the display that is shown when no visualization is 
 * needed for the graph because it is too big. 
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class BigGraphPanelView extends View implements Observer {
	
	
	private InfoPanel info; 
	private JPanel buttons = new JPanel(); 
	private JButton importButton; 
	private JButton startButton; 
	private JComboBox<Object> chooseAlgorithm;
	private Graph model; 
	
	public BigGraphPanelView(int width, int height, Graph model){
		if(height < 1 || width < 1){
			throw new IllegalArgumentException("BigGraphPanelView has to be at least 1x1");
		}
		this.model = model;
		this.setLayout(new BorderLayout());
		info = new InfoPanel(model); 
		this.add(info, BorderLayout.CENTER);
		buttons.setLayout(new GridLayout(0,3));
		this.add(buttons, BorderLayout.SOUTH);
		importButton = new JButton("Import Graph");
		buttons.add(importButton);
		chooseAlgorithm = new JComboBox<Object>(); 
		buttons.add(chooseAlgorithm);
		startButton = new JButton("Start Algorithm");
		buttons.add(startButton);
	}
	
	public JButton getImportButton(){
		return importButton;
	}
	
	public JButton getStartButton(){
		return startButton; 
	}
	
	public void setModel(Graph model){
		info.setModel(model); 
		this.model = model; 
		this.repaint();
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if(model!=null){
			info.repaint();
		}
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
		buttons.remove(chooseAlgorithm);
		chooseAlgorithm = new JComboBox<Object>(algos);
		buttons.add(chooseAlgorithm, 0,0);
	}
	
	public void setAlgorithm(String s){
		info.setAlgorithm(s);
	}

	@Override
	public JButton getLastButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JButton getCompleteButton() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JButton getResetButton() {
		// TODO Auto-generated method stub
		return null;
	}

}
