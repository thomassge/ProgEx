package Backend;

public class BookUnavailableException extends RuntimeException {
    BookUnavailableException(String message) {
        super(message);
    }
}
