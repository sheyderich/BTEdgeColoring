package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import graphs.Graph;

@SuppressWarnings("serial")
public class BigGraphPanelView extends View implements Observer {
	
	private Graph model; 
	private InfoPanel info; 
	private JButton importButton; 
	private JComboBox<Object> chooseAlgorithm;
	private JPanel buttons = new JPanel(); 
	
	public BigGraphPanelView(int width, int height, Graph model){
		if(height < 1 || width < 1){
			throw new IllegalArgumentException("BigGraphPanelView has to be at least 1x1");
		}
		this.model = model;
		this.setLayout(new BorderLayout());
		info = new InfoPanel(model); 
		this.add(info, BorderLayout.CENTER);
		buttons.setLayout(new GridLayout(0,2));
		this.add(buttons, BorderLayout.SOUTH);
		importButton = new JButton("Import Graph");
		buttons.add(importButton);
		chooseAlgorithm = new JComboBox<Object>(); 
		buttons.add(chooseAlgorithm); 
	}
	
	public JButton getImportButton(){
		return importButton;
	}
	
	public void setModel(Graph model){
		info.setModel(model); 
		
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
		info.setAlgName(s);
	}

}
