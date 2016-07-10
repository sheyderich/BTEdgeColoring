package exceptions;

/**
 * Throws an Exception if the color that is requested 
 * for an edge is invalid because it is < 1. 
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class InvalidColorException extends IllegalArgumentException {
	public InvalidColorException (String message){
		super(message);
	}
}
