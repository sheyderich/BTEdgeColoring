package main;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import controller.GraphPanelViewController;
import graphReader.GraphReader;
import graphs.DrawableGraph;
import graphs.Graph;

/**
 * The starting point of the Applet. The applet can be used to comprehend
 * a number of edge coloring algorithms step by step. 
 * 
 * @author Stephanie Heyderich
 * @version 06.05.2016
 */
public class AppletMain {
	public static void main(String[] args) {

		JFrame frame = new JFrame("Edge Coloring Algorithms");
		GraphReader gr = new GraphReader("bigraph1.txt");
		Graph g = gr.buildGraphFromFile();
		GraphPanelViewController controller = new GraphPanelViewController();
		controller.setGraph((DrawableGraph)g);
		frame.add(controller.getView(), BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SwingUtilities.updateComponentTreeUI(frame);
		frame.pack();
		frame.setVisible(true);
		controller.getView().repaint();	
	}
}
