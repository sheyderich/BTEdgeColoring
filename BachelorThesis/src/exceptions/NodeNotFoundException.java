package exceptions;

@SuppressWarnings("serial")
public class NodeNotFoundException extends RuntimeException {
	public NodeNotFoundException(String m){
		super(m);
	}
}
