package helper;

import java.awt.Point;
import java.util.List;
import graphs.Graph;

/**
 * This algorithm step describes an order for order based algorithms. 
 * instead of undoing the step, the order can be asked for.  
 * @author Stephanie Heyderich
 */
public class OrderStep implements AlgorithmStep {

	private List<Point> order; 
	
	public OrderStep(List<Point> order){
		this.order = order; 
	}
	
	@Override
	public void undo(Graph g) {
		// not used
	}
	
	/**
	 * Returns the order which this step of the algorithm was 
	 * used
	 * @return
	 */
	public List<Point> getOrder(){
		return order; 
	}

}
