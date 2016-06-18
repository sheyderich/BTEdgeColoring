package exceptions;

@SuppressWarnings("serial")
public class TooManyColorsException extends RuntimeException {
	
	public TooManyColorsException(String m){
		super(m);
	}
}
