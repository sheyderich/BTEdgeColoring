package exceptions;

/**
 * Throws an exception if the graph type that is requested is
 * not an implemented type
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class IllegalGraphTypeException extends IllegalArgumentException {
	
	public IllegalGraphTypeException(String message){
		super(message);
	}
	
	
}
