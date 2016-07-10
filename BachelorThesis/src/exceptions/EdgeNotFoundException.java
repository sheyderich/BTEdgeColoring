package exceptions;

/**
 * Throws an exception when the edge that is requested is
 * not an existing edge in the given graph. 
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class EdgeNotFoundException extends IllegalArgumentException {
	
	public EdgeNotFoundException(String message){
		super(message);
	}
}
