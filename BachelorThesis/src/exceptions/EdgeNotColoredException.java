package exceptions;

/**
 * Throws an Exception if the request to get the color
 * from an edge is made on a non-colored edge. 
* @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class EdgeNotColoredException extends RuntimeException{
	
	public EdgeNotColoredException(String message){
		super(message);
	}
	
}
