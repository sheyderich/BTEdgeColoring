package view;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import graphs.Graph;

/**
 * A marker Interface for the two views. 
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public abstract class View extends JPanel implements Observer{
	
	public abstract void setModel(Graph model);

	public abstract void setChooseAlgorithm(Object[] array);

	public abstract JComboBox<Object> getChooseAlgorithm();

	public abstract void setAlgorithm(String selectedItem);

	public abstract JButton getImportButton();

	public abstract JButton getStartButton();

	public abstract JButton getLastButton();

	public abstract JButton getCompleteButton();

	public abstract JButton getResetButton();
}
