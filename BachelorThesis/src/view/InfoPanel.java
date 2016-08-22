package view;

import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import graphs.Graph;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel {
	private Graph model; 
	private int m; 
	private int n; 
	private int delta; 
	private String algName = new String(); 
	private int colors = 0; 
	private double dev; 
	
	public InfoPanel(Graph theModel){
		this.setLayout(new GridLayout(0,2));
		model = theModel; 
		setUpInfos();
		setUpGraphInfo(); 
		setUpAlgInfo(); 
	}
	
	private void setUpInfos(){
		if(model != null){
			m = model.getEdgeNumber();
			n = model.getVertexNumber();
			delta = model.calculateMaxVertexDegree();
		}
	}

	private void setUpGraphInfo() {
		if(model != null){
			this.add(new JLabel("Graph"));
			this.add(new JLabel(""));
			this.add(new JLabel("Vertices:"));
			this.add(new JLabel(n+""));
			this.add(new JLabel("Edges:"));
			this.add(new JLabel(m+""));
			this.add(new JLabel("Max Vertex Degree:"));
			this.add(new JLabel(delta+""));
			this.add(new JLabel("Density:"));
			this.add(new JLabel((m/((n*(n-1))/2))+""));
		}
	}
	
	private void setUpAllAlgInfo(){
		setColors(model.getQuantityColors());
		setDeviation();
	}
	
	private void setUpAlgInfo() {
		this.add(new JLabel("Algorithm:"));
		this.add(new JLabel(algName));
		this.add(new JLabel("Number Colors:"));
		this.add(new JLabel(colors+""));
		this.add(new JLabel("Deviation from Vizing:"));
		this.add(new JLabel(dev + ""));
	}
	
	public void setAlgName(String name){
		algName = name; 
		setUpAlgInfo(); 
	}
	
	public void setColors(int colors){
		this.colors = colors; 
		setUpAlgInfo(); 
	}
	
	public void setDeviation(){
		dev = (colors - delta)/delta; 
		dev *= 100; 
	}
	
	public void setModel(Graph model){
		this.model = model; 
		setUpInfos(); 
		setUpGraphInfo(); 
		setUpAllAlgInfo(); 
		setUpAlgInfo(); 
	}
	
	public void paint(Graphics g){
		super.paint(g);
		setUpAlgInfo(); 
	}
	
}
