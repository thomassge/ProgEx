package Backend;

/**
 * The BookUnavailableException class is thrown when a book is not available.
 */
public class BookUnavailableException extends RuntimeException {
    BookUnavailableException(String message) {
        super(message);
    }
}
