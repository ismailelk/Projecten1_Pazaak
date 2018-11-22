package exceptions;

import util.Taal;

/**
 *
 * @author Simon
 */
public class WedstrijdBestaatAlException extends IllegalArgumentException {

    /**
     * Creates a new instance of <code>WedstrijdBestaatAlException</code>
     * without detail message.
     */
    public WedstrijdBestaatAlException() {
        super(Taal.getWoordUitBundle("dewedstrijdbestaatal"));
    }

    /**
     * Constructs an instance of <code>WedstrijdBestaatAlException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public WedstrijdBestaatAlException(String msg) {
        super(msg);
    }
}
