package exceptions;

/**
 * Throws an Exception if the color that is requested 
 * for an edge is invalid because it is < 1. 
 * @author Stephanie Heyderich
 * @version 24.04.2016
 */
@SuppressWarnings("serial")
public class InvalidColorException extends IllegalArgumentException {
	public InvalidColorException (String message){
		super(message);
	}
}
