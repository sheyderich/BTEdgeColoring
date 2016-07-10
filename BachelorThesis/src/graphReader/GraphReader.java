package graphReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import exceptions.IllegalGraphTypeException;
import exceptions.IllegalNumberEdgesException;
import graphs.BipartiteGraph;
import graphs.Graph;
import graphs.SimpleGraph;

/**
 * Reads a graph from a properly formatted file. 
 * the graphs can be simple graphs or bipartite graphs. 
 * multigraphs are not possible. 
 * @author Stephanie Heyderich
 */
public class GraphReader {
	
	private String filename;
	private int graphType; 
	private int vertexCount1;
	private int vertexCount2;
	private String edges;
	private String usage;
	
	/**
	 * Creates a GraphReader and tries to open the corresponding
	 * File to the given filename
	 * @param file
	 */
	public GraphReader(String file){
		filename = file;
		fillUsage();
		openFile();
	}
	
	/**
	 * Tries to open the file that was given to the GraphReader, 
	 * parses VertexCount and GraphType, reads the edges into a String 
	 * and closes the File.
	 */
	private void openFile(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(filename));

			String firstLine = reader.readLine().trim();
			if(firstLine.matches("[1-9]\\d*,[1-9]\\d*")){
				graphType = 1;
				String[] split_type = firstLine.split(",");
				vertexCount1 = Integer.parseInt(split_type[0]);
				vertexCount2 = Integer.parseInt(split_type[1]);
			}else if (firstLine.matches("\\d*")){
				graphType = 2;
				vertexCount1 = Integer.parseInt(firstLine);
			} else {
				reader.close();
				throw new IllegalGraphTypeException("Not a supported format for a graph type " + usage);
			}

			
			String actualLine = reader.readLine().trim();
			edges = new String();
			while(actualLine != null){
				edges += actualLine.trim() + "\n";
				actualLine = reader.readLine();
			}
			
			reader.close();
			
		} catch (NumberFormatException nfe) {
			System.err.println("Not a valid input file: You may check the vertex number.");
			System.out.println(usage);
		} catch (FileNotFoundException fnfe){
			System.err.println("File could not be found.");
			fnfe.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Creates a new Graph from the given File with the type 
	 * corresponding to the type given in the file
	 * @return
	 */
	public Graph buildGraphFromFile(){
		Graph g = null;
		g = getNewGraph(graphType);
		fillEdges(g);
		return g;
	}
	
	/**
	 * Returns a new Graph of the type that is requested 
	 * @param type
	 * @return
	 * @throws IllegalGraphTypeException
	 */
	private Graph getNewGraph(int type){
		switch(graphType){
			case 1: return new BipartiteGraph(vertexCount1, vertexCount2);
			case 2: return new SimpleGraph(vertexCount1);
			default:
				System.out.println(usage);
				throw new IllegalGraphTypeException ("Graph type " + type + " not implemented\n");
		}
	}
	
	/**
	 * Takes the graph and fills it with the given edges from the
	 * file, reduces the edge names by 1 to start the edge
	 * count by 0. 
	 * @param g
	 */
	private void fillEdges(Graph g){
		String[] edgeArray = edges.split("\n");
		for(int i = 0; i < edgeArray.length; i++){
			String[] vertices = edgeArray[i].split(" ");
			if(vertices.length > 2){
				System.out.println(usage);
				throw new IllegalNumberEdgesException("The file is not properly formated \n");
			}
			int u = Integer.parseInt(vertices[0]);
			int v = Integer.parseInt(vertices[1]);
			g.addEdge(u-1, v-1);
		}
		
	}
	
	/**
	 * Provides a description about the structure of a graph file 
	 */
	private void fillUsage(){
		usage = new String();
		usage = "How to represent the graph in a file: \n";
		usage += "  1. line: Number of vertices of the graph (bipartite: 2,2 for a B(2,2))";
		usage += "  2. line ff: a tuple of vertices that are connected by an edge, eg: 1 2\n";
		usage += "              it is important to note, that the first vertex has the number 1, not 0";
		
	}
}
