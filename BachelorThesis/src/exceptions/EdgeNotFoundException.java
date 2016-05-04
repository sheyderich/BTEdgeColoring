package exceptions;

/**
 * Throws an exception when the edge that is requested is
 * not an existing edge in the given graph. 
 * @author Stephanie Heyderich
 * @version 24.04.2016
 */
@SuppressWarnings("serial")
public class EdgeNotFoundException extends IllegalArgumentException {
	
	public EdgeNotFoundException(String message){
		super(message);
	}
}
