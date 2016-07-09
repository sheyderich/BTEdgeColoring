package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import edgeAlgorithms.Koenig;
import exceptions.NoFreeColorException;
import graphs.BipartiteGraph;

/**
 * Helps testing the AlgorithmKoenig class to assure every method
 * works as intended
 * @author Stephanie Heyderich
 * @version 25.04.2016
 *
 */
public class KoenigTest {
	
	BipartiteGraph bg; 
	
	/**
	 * Sets up the BipartiteGraph that is tested on
	 */
	@Before
	public void setUp(){
		bg = new BipartiteGraph(3,3);
		fillGraph();
	}
	
	/**
	 * Tests whether the method getFreeColor cann return a free color
	 */
	@Test
	public void testFreeColor(){
		assertEquals(1, Koenig.testFreeColor(bg, 0));
		bg.setEdgeColor(0, 3, 1);
		bg.setEdgeColor(1, 3, 2);
		bg.setEdgeColor(2, 3, 3);
		try{
			Koenig.testFreeColor(bg, 3);
		}catch(NoFreeColorException e){
			
		}
	}
	
	/**
	 * Tests whether an alternating path is found
	 */
	@Test
	public void testAlternatingPath(){
		paintPath();
		List<Integer> path = Koenig.testFindAlternatingPath(bg, 0, 1, 2);
		assertEquals(4, path.size());
		for(Integer i: path){
			if(i != 0 && i != 3 && i != 2 && i != 5){
				fail();
			}
		}
		assertTrue(Koenig.testFindAlternatingPath(bg, 0, 3, 2).size() == 1);
	}
	
	/**
	 * Tests whether an Vertex with a specified color is a neighbor to a given vertex
	 */
	@Test
	public void testGetNeighborWithColor(){
		paintPath();
		assertEquals(3, Koenig.testGetNeighboringVertexWithColor(bg, 0, 1));
		assertEquals(-1, Koenig.testGetNeighboringVertexWithColor(bg, 0, 2));
	}
	
	/**
	 * Tests whether a path with a sequence of colors is switched in order after calling the 
	 * switchColorOnPath method
	 */
	@Test
	public void testSwitchColor(){
		paintPath();
		String exp = "2 1 2";
		List<Integer> path = getPath(); 

		Koenig.testSwitchColorOnPath(bg, path, 1, 2);
		String act = bg.getEdgeColor(path.get(0), path.get(1)) + " ";
		act += bg.getEdgeColor(path.get(1), path.get(2)) + " ";
		act += bg.getEdgeColor(path.get(2), path.get(3)) + "";
		
		assertEquals(exp, act);
		
	}

	/**
	 * Fills the Graph that is tested on with edges
	 */
	private void fillGraph() {
		bg.addEdge(0, 3);
		bg.addEdge(0, 4);
		bg.addEdge(0, 5);
		bg.addEdge(1, 3);
		bg.addEdge(1, 4);
		bg.addEdge(1, 5);
		bg.addEdge(2, 3);
		bg.addEdge(2, 4);
		bg.addEdge(2, 5);
	}
	
	/**
	 * Fills and paints a path in the graph that is used to determine whether
	 * the algorithm finds the right paths 
	 */
	private void paintPath() {
		bg.setEdgeColor(0, 3, 1);
		bg.setEdgeColor(3, 2, 2);
		bg.setEdgeColor(2, 5, 1);
	}
	
	/**
	 * Creates a list of neighbors that have a alternating colored path between them
	 * @return
	 */
	private List<Integer> getPath() {
		List<Integer> path = new ArrayList<Integer>(); 
		path.add(new Integer(0));
		path.add(new Integer(3));
		path.add(new Integer(2));
		path.add(new Integer(5));
		return path;
	}
}
