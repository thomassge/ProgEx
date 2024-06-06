package Backend;

/**
 * The BookIsNotExtendableException class is thrown if a book lending cannot be extended.
 */
public class BookIsNotExtendableException extends RuntimeException {
    BookIsNotExtendableException(String message) {
        super(message);
    }
}
