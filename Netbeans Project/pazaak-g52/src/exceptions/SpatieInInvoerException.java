/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Simon
 */
public class SpatieInInvoerException extends Exception {

    public SpatieInInvoerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpatieInInvoerException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new instance of <code>SpatieInInvoerException</code> without
     * detail message.
     */
    public SpatieInInvoerException() {
    }

    /**
     * Constructs an instance of <code>SpatieInInvoerException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SpatieInInvoerException(String msg) {
        super(msg);
    }
}
