/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import util.Taal;

/**
 *
 * @author Renaat
 */
public class KiesSpelerException extends IllegalArgumentException {

    /**
     * Creates a new instance of <code>KiesSpelerException</code> without detail
     * message.
     */
    public KiesSpelerException() {
        super(Taal.getWoordUitBundle("Degekozenspelerisnietgeldig"));
    }

    /**
     * Constructs an instance of <code>KiesSpelerException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public KiesSpelerException(String msg) {
        super(msg);
    }

    public KiesSpelerException(String message, Throwable cause) {
        super(message, cause);
    }

    public KiesSpelerException(Throwable cause) {
        super(cause);
    }
    
}
