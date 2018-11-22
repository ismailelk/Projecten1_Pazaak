package exceptions;

public class KiesKaartException extends IllegalArgumentException {

    /**
     * Creates a new instance of <code>KiesKaartException</code> without detail
     * message.
     */
    public KiesKaartException() {
    }
    
    public KiesKaartException(String message, Throwable cause) {
        super(message, cause);
    }

    public KiesKaartException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an instance of <code>KiesKaartException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public KiesKaartException(String msg) {
        super(msg);
    }
}
