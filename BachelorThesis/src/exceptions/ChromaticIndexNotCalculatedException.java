package exceptions;

/**
 * Throws an exception if the chromatic index cannot be determined
 * because the bounds are not equal
 * @author Stephanie Heyderich
 */
@SuppressWarnings("serial")
public class ChromaticIndexNotCalculatedException extends RuntimeException {

	public ChromaticIndexNotCalculatedException(String message) {
		super(message);
	}

}
