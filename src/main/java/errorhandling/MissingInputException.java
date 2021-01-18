package errorhandling;

/**
 *
 * @author groen
 */
public class MissingInputException extends Exception {

    public MissingInputException() {
        super("Missing input!");
    }

}
