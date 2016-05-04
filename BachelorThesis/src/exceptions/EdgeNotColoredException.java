package exceptions;

/**
 * Throws an Exception if the request to get the color
 * from an edge is made on a non-colored edge. 
* @author Stephanie Heyderich
 * @version 24.04.2016
 */
@SuppressWarnings("serial")
public class EdgeNotColoredException extends RuntimeException{
	
	public EdgeNotColoredException(String message){
		super(message);
	}
	
}
