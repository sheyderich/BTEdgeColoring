package main;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import controller.GraphPanelViewController;
import graphReader.GraphReader;
import graphs.DrawableGraph;
import graphs.Graph;

public class AppletMain {
	public static void main(String[] args) {

		JFrame frame = new JFrame("Edge Coloring Algorithms");
		GraphReader gr = new GraphReader("bigraph2.txt");
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
