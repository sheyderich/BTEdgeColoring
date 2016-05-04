package tests;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import exceptions.ChromaticIndexNotCalculatedException;
import exceptions.EdgeNotColoredException;
import exceptions.EdgeNotFoundException;
import graphs.SimpleGraph;

/**
 * Helps testing the SimpleGraph class to assure every method
 * works as intended
 * @author Stephanie Heyderich
 * @version 25.04.2016
 *
 */
public class SimpleGraphTest {
	
	SimpleGraph sg;
	String adjacencyMatrix;
	int vertexNumber;
	
	/**
	 * Sets up a simple graph that is operated on
	 */
	@Before
	public void setUp(){
		vertexNumber = 5; 
		sg = new SimpleGraph(vertexNumber);
		fillGraphWithEdges();
		adjacencyMatrix = new String();
		fillMatrixWithEdges();
		sg.setEdgeColor(0, 1, 1);
	}

	/**
	 * Tests the construction and the print method of the 
	 * SimpleGraph class
	 */
	@Test
	public void testBuildGraph() {
		assertEquals(adjacencyMatrix, sg.toString());
		assertFalse(new String("-2").equals(sg.toString()));
	}
	
	/**
	 * Tests the internally saved vertex number of the graph
	 */
	@Test
	public void testVertexNumber(){
		assertEquals(vertexNumber, sg.getVertexNumber());
		assertFalse(8 == sg.getVertexNumber());
	}
	
	/**
	 * Tests whether an edge exists
	 */
	@Test
	public void testIsEdgeExistent(){
		assertTrue(sg.isEdgeExistent(0, 1));
		assertFalse(sg.isEdgeExistent(1, 4));
	}
	
	/**
	 * Tests the getting of the edge color
	 */
	@Test
	public void testToGetEdgeColor(){
		assertEquals(1, sg.getEdgeColor(0, 1));
		assertFalse(2 == sg.getEdgeColor(0, 1));
		try{
			sg.getEdgeColor(0, 2);
			fail();
		}catch(EdgeNotFoundException e){
			try{
				sg.getEdgeColor(0, 4);
				fail();
			}catch(EdgeNotColoredException f){
				
			}
		}
	}
	
	/**
	 * Tests the setting of an edge color
	 */
	@Test
	public void testToColorEdges(){
		sg.setEdgeColor(0, 3, 2);
		assertEquals(2, sg.getEdgeColor(0, 3));
		assertFalse(1 == sg.getEdgeColor(0, 3));
	}
	
	/**
	 * Tests the removing of the color on an edge
	 */
	@Test
	public void testRemoveColor(){
		try{
			sg.removeEdgeColor(0, 1);
			sg.getEdgeColor(0, 1);
			fail();
		}catch(EdgeNotColoredException e){
			
		}
		
	}
	
	/**
	 * Tests whether the edge coloring is valid
	 */
	@Test
	public void testEdgeColoringValid(){
		assertTrue(sg.isEdgeColoringValid(0, 1));
		sg.setEdgeColor(1, 2, 1);
		assertFalse(sg.isEdgeColoringValid(0, 1));
	}
	
	/**
	 * Tests whether the graph coloring is valid
	 */
	@Test
	public void testGraphColoringValid(){
		sg.setEdgeColor(0, 1, 1);
		sg.setEdgeColor(0, 3, 2);
		sg.setEdgeColor(0, 4, 3);
		sg.setEdgeColor(1, 2, 2);
		sg.setEdgeColor(1, 3, 3);
		sg.setEdgeColor(3, 4, 1);
		assertTrue(sg.isGraphColoringValid());
		sg.setEdgeColor(3, 4, 3);
		assertFalse(sg.isGraphColoringValid());
	}
	
	/**
	 * Tests the calculation of the MaxVertexDegree, the Bounds
	 * and the chromatic Index
	 */
	@Test
	public void testCalculations(){
		assertEquals(3, sg.calculateMaxVertexDegree());
		assertFalse(4 == sg.calculateMaxVertexDegree());
		assertEquals(3, sg.calculateLBChromaticIndex());
		assertFalse(4 == sg.calculateLBChromaticIndex());
		assertEquals(4, sg.calculateUBChromaticIndex());
		assertFalse(3 == sg.calculateUBChromaticIndex());
	}
	
	/**
	 * Tests the chromatic Index calculations
	 */
	@Test
	public void testChromaticIndex(){
		assertFalse(sg.isChromaticIndexDeterminedByBounds());
		try{
			sg.getChromaticIndex();
			fail();
		}catch(ChromaticIndexNotCalculatedException e){
			
		}
	}
	
	/**
	 * Tests the number of used colors
	 */
	@Test
	public void testColorsSet(){
		assertEquals(1, sg.getQuantityColors());
	}

	/**
	 * Tests the creation of the neighbor-list
	 */
	@Test
	public void testGetNeighbors(){
		List<Integer> neigh = sg.getNeighbors(0);
		for(Integer i: neigh){
			boolean valid = false; 
			if (i == 1 || i == 3 || i == 4){
				valid = true;
			}
			assertTrue(valid);
		}
	}
	
	/**
	 * Fills the test graph with edges
	 */
	private void fillGraphWithEdges() {
		sg.addEdge(0, 1);
		sg.addEdge(0, 3);
		sg.addEdge(0, 4);
		sg.addEdge(1, 2);
		sg.addEdge(1, 3);
		sg.addEdge(3, 4);
	}
	
	/**
	 * Fills the adjacency Matrix string with the corresponding edges
	 */
	private void fillMatrixWithEdges() {
		adjacencyMatrix += String.format("%4d%4d%4d%4d%4d\n", 0,1,0,-2,-2);
		adjacencyMatrix += String.format("%4d%4d%4d%4d%4d\n", 1,0,-2,-2,0);
		adjacencyMatrix += String.format("%4d%4d%4d%4d%4d\n", 0,-2,0,0,0);
		adjacencyMatrix += String.format("%4d%4d%4d%4d%4d\n", -2,-2,0,0,-2);
		adjacencyMatrix += String.format("%4d%4d%4d%4d%4d\n", -2,0,0,-2,0);
	}
	
	

}
