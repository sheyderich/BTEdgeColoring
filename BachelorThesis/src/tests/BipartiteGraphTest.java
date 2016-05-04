package tests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import graphs.BipartiteGraph;

/**
 * Helps testing the BipartiteGraph class to assure every method
 * works as intended
 * @author Stephanie Heyderich
 * @version 25.04.2016
 */
public class BipartiteGraphTest {

	BipartiteGraph bg;
	
	/**
	 * Sets up a bipartite graph that is operated on
	 */
	@Before
	public void setUp(){
		bg = new BipartiteGraph(6);
		fillEdges();
	}
	
	/**
	 * Tests the calculation of the lower and upper bound
	 */
	@Test
	public void testBounds() {
		assertEquals(3, bg.calculateLBChromaticIndex());
		assertEquals(3, bg.calculateUBChromaticIndex());
		assertEquals(bg.calculateLBChromaticIndex(), bg.calculateUBChromaticIndex());
	}
	
	private void fillEdges() {
		bg.addEdge(0, 4);
		bg.addEdge(0, 5);
		bg.addEdge(1, 4);
		bg.addEdge(2, 4);
		bg.addEdge(2, 5);
		bg.addEdge(3, 5);
	}

}
