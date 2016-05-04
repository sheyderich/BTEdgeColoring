package graphs;

public class BipartiteGraph extends SimpleGraph{
	
	/**
	 * Create empty adjacency matrix for the bipartite graph
	 * @param vertexNumber
	 */
	public BipartiteGraph(int vertexNumber) {
		super(vertexNumber);
	}

	/**
	 * Using Koenig's Theorem for bipartite Graphs to calculate the 
	 * lower bound for the chromatic index
	 */
	@Override
	public int calculateLBChromaticIndex() {
		if (lbChromaticIndex == UNDEFINED){
			calculateMaxVertexDegree();
			lbChromaticIndex = maxVertexDegree; 
		}
		return lbChromaticIndex;
	}

	/**
	 * Using Koenig's Theorem for bipartite Graphs to calculate the 
	 * upper bound for the chromatic index
	 */
	@Override
	public int calculateUBChromaticIndex() {
		if (ubChromaticIndex == UNDEFINED){
			calculateMaxVertexDegree();
			ubChromaticIndex = maxVertexDegree; 
		}
		return ubChromaticIndex;
	}
	
	@Override
	public String getType(){
		return "Bipartite graph";
	}
}
