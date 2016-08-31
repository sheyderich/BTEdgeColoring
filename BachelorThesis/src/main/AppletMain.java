package main;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

//import controller.BigGraphPanelViewController;
import controller.Controller;
import controller.GraphPanelViewController;
import graphReader.GraphReader;
import graphs.Graph;

/**
 * The starting point of the Applet. The applet can be used to comprehend
 * a number of edge coloring algorithms step by step. 
 * 
 * @author Stephanie Heyderich
 */
public class AppletMain {
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Edge Coloring Algorithms");
		Controller controller = new GraphPanelViewController(frame);
		GraphReader gr = new GraphReader("VisualizationGraphs/bigraph1.txt");
		Graph g = gr.buildGraphFromFile();
		controller.setModel(g);
		frame.add(controller.getView(), BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingUtilities.updateComponentTreeUI(frame);
		frame.pack();
		frame.setVisible(true);
//		frame.setResizable(false);
		controller.getView().repaint();
	}
}
