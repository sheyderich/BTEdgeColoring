package exceptions;

/**
 * Exception is used when the GraphReader is given a illegally 
 * formatted file where in one row instead of two nodes that
 * describe an edge, more nodes or less nodes than two are 
 * given. 
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class IllegalNumberEdgesException extends IllegalArgumentException {
	public IllegalNumberEdgesException(String message){
		super(message);
	}
}
