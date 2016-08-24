package randomGraphGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Random;

public class RandomGraphGenerator {
	
	int[][] graph; 
	double p;
	Random rand; 
	
	public RandomGraphGenerator(int vertices, double probability){
		if(probability > 1 || probability < 0){
			throw new IllegalArgumentException(); 
		}
		p = probability; 
		rand = new Random(); 
		graph = new int[vertices][vertices]; 
	}
	
	public void createGraph(){
		for(int i = 0; i < graph.length; i++){
			for(int j = 0; j < graph.length; j++){
				if(i==j){
					graph[i][j] = -1;
				}
				if(graph[i][j] == 0){
					double q = rand.nextDouble();
					if(p > q){
						graph[i][j] = 1; 
						graph[j][i] = 1; 
					}else{
						graph[i][j] = -1; 
						graph[j][i] = -1; 
					}
				}
			}
		}
	}
	
	public void print (){
		for(int i = 0; i < graph.length; i++){
			for(int j = 0; j < graph.length; j++){
				
				System.out.printf("%5d", graph[i][j]);
				
			}
			System.out.println();
		}
	}
	
	public void writeInFile(){
		String name = graph.length + "_" + p + ".txt";
		File f = new File(name);
		try (Writer writer = new BufferedWriter(new FileWriter(f))) {
			writer.write(graph.length + "\n");
			for(int i = 0; i < graph.length; i++){
				for(int j = 0; j < graph.length; j++){
					if(graph[i][j] != 0 && graph[i][j] != -1){
						writer.write((i+1) + " " + (j+1) + "\n");
					}
				}
			}
		} catch(Exception e){
			System.out.println("Sorry");
		}
		
	}
	
	public boolean testCohesion(){
		boolean[] nodes = new boolean[graph.length];
		Arrays.fill(nodes, false);
		

		dfs(0, nodes);
	
		
		for(int i = 0; i < nodes.length; i++){
			if(nodes[i] == false){
				return false; 
			}
		}
		return true; 
	}
	
	private void dfs(int i, boolean[] nodes) {
		nodes[i] = true; 
		for(int j = 0; j < graph.length; j++){
			if(graph[i][j] == 1){
				if(nodes[j] == false){
					dfs(j, nodes);
				}
			}
		}
	}
	
}
