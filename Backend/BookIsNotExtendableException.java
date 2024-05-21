package Backend;

public class BookIsNotExtendableException extends RuntimeException {
    BookIsNotExtendableException(String message) {
        super(message);
    }
}
