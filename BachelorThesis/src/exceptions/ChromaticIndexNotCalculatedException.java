package exceptions;

/**
 * Throws an exception if the chromatic index cannot be determined
 * because the bounds are not equal
 * @author Stephanie Heyderich
 * @version 24.04.2016
 */
@SuppressWarnings("serial")
public class ChromaticIndexNotCalculatedException extends RuntimeException {

	public ChromaticIndexNotCalculatedException(String message) {
		super(message);
	}

}
