package exceptions;

@SuppressWarnings("serial")
public class NotEnoughColorsException extends RuntimeException {


	public NotEnoughColorsException(String message){
		super(message);
	}
}
