package exceptions;

/**
 * Throws an Exception if at runtime it is determined that there is no 
 * free color for an edge to be colored in. 
 * 
 * @author Stephanie Heyderich
 * @version 24.04.2016
 */
@SuppressWarnings("serial")
public class NoFreeColorException extends RuntimeException {
	public NoFreeColorException(String message){
		super(message);
	}
}
