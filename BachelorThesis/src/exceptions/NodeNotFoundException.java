package exceptions;

/**
 * Exception for the case that an invalid
 * node is tried to be accessed 
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class NodeNotFoundException extends RuntimeException {
	public NodeNotFoundException(String m){
		super(m);
	}
}
