package exceptions;

@SuppressWarnings("serial")
public class InvalidEdgeException extends IllegalArgumentException {
	public InvalidEdgeException(String message){
		super(message);
	}
}
