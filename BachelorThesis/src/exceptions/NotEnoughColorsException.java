package exceptions;


/**
 * An exception that is used when more than 20 colors are needed 
 * to color a graph. 
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class NotEnoughColorsException extends RuntimeException {
	public NotEnoughColorsException(String message){
		super(message);
	}
}
