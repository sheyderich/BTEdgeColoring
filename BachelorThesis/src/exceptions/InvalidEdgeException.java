package exceptions;

/**
 * This exception is used when it is tried
 * to read an edge that does not exist in the
 * current graph. 
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class InvalidEdgeException extends IllegalArgumentException {
	public InvalidEdgeException(String message){
		super(message);
	}
}
