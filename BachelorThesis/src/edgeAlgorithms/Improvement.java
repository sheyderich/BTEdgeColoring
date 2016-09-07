package edgeAlgorithms;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import graphs.Graph;
import helper.TabuSearchStep;

/**
 * A tabu search algorithm. 
 * while violations against the graph coloring rules exist, critical edges are recolored. 
 * critical edges are edges, where the color is colored in a way that does not conform 
 * with the rules. 
 * @author Stephanie Heyderich
 */
public abstract class Improvement implements ColoringAlgorithms {

	protected int best;
	private int violations;
	private List<TabuSearchStep> tabuList = new ArrayList<TabuSearchStep>();
	private Graph currentColoring;
	private boolean init = true; 
	private final int WEIGHT = 2; 
	private Stack<TabuSearchStep> steps = new Stack<TabuSearchStep>(); 
	
	@Override
	public void applyAlgorithmComplete(Graph graph) {
		
		if(init){
			generateStartingSolution(graph);
			violations = evaluateColoring(graph);
			currentColoring = graph.copyWithColors();
			init = false; 
		}
		
		while(violations != 0){
			List<TabuSearchStep> candidates = createCandidates(currentColoring);
			Random rand = new Random(); 
			int cand_pos = rand.nextInt(candidates.size());
			TabuSearchStep next = candidates.get(cand_pos);
			candidates.clear();
			
			tabuList.add(next);
			
			next.setStep(currentColoring);
			int currentColors = currentColoring.getQuantityColors();
			int currentViolations = evaluateColoring(currentColoring);
			
			if(currentColors + currentViolations*WEIGHT < best + violations*WEIGHT){
				next.setStep(graph);
				graph.setLastStep(next.getNode1(), next.getNode2());
				steps.add(next);
				best = currentColors; 
				violations = currentViolations; 
			}
			
		}

	}

	@Override
	public void applyAlgorithmStepwise(Graph graph) {
		if(init){
			generateStartingSolution(graph);
			violations = evaluateColoring(graph);
			currentColoring = graph.copyWithColors();
			init = false; 
		}else if (violations != 0){
			
			List<TabuSearchStep> candidates = createCandidates(currentColoring); 
			Random rand = new Random(); 
			int cand_pos = rand.nextInt(candidates.size());
			TabuSearchStep next = candidates.get(cand_pos);
			candidates.clear();
			
			tabuList.add(next);
			
			next.setStep(currentColoring);
			int currentColors = currentColoring.getQuantityColors();
			int currentViolations = evaluateColoring(currentColoring);
			
			if(currentColors + currentViolations*WEIGHT < best + violations*WEIGHT){
				next.setStep(graph);
				graph.setLastStep(next.getNode1(), next.getNode2());
				steps.add(next);
				best = currentColors; 
				violations = currentViolations; 
			}
		}

	}

	@Override
	public void undoLastColoring(Graph graph) {
		if(!steps.isEmpty()){
			TabuSearchStep last = steps.pop();
			last.undo(graph);
		}else{
			graph.uncolor();
			init = true;
		}
		graph.removeLastStep();

	}

	@Override
	public void resetColoring(Graph graph) {
		steps.clear();
		init = true; 
		graph.uncolor();
	}

	/**
	 * Returns the number of invalid colored edges
	 * @param graph
	 * @return
	 */
	private int evaluateColoring(Graph graph) {
		int invalidEdges = 0; 
		for(int u = 0; u < graph.getVertexNumber(); u++){
			for(int v = u+1; v < graph.getVertexNumber(); v++){
				if(graph.isEdgeExistent(u, v)){
					if(!graph.isEdgeColoringValid(u, v)){
						invalidEdges++;
					}
				}
			}
		}
		return invalidEdges;
	}

	/**
	 * For every edge that is not validly colored a possible solution is
	 * created and added to the list of candidates
	 * @param currentColoring
	 * @return
	 */
	private List<TabuSearchStep> createCandidates(Graph currentColoring) {
		
		List<TabuSearchStep> candidates = new ArrayList<TabuSearchStep>(); 
		List<Point> edges = currentColoring.getEdges();
		
		for(Point p: edges){	
			int u = p.x;
			int v = p.y;
			if(!currentColoring.isEdgeColoringValid(u, v)){
				int color = getFirstValidColor(currentColoring,u,v);
				int colorBefore = currentColoring.getEdgeColor(u, v);
				candidates.add(new TabuSearchStep(u,v,color, colorBefore));
			}
		}
		return candidates;
	}

	/**
	 * returns the first valid color that can be used for an edge by checking 
	 * for the lowest color that has not been used on any edge at the two vertices
	 * @param currentColoring
	 * @param u
	 * @param v
	 * @return
	 */
	private int getFirstValidColor(Graph currentColoring, int u, int v) {
		List<Integer> colors = currentColoring.getColorsAtVertex(u);
		colors.addAll(currentColoring.getColorsAtVertex(v));
		for(int i = 1; i < Integer.MAX_VALUE; i++){
			if(!colors.contains(i)){
				return i; 
			}
		}
		// never reached
		return 0;
	}
	
	/**
	 * Colors all edges red as an initial solution
	 * @param graph
	 */
	public abstract void generateStartingSolution(Graph graph);
}
