/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

import util.Taal;

/**
 *
 * @author Simon
 */
public class LaadWedstrijdException extends IllegalArgumentException {

    /**
     * Creates a new instance of <code>LaadWedstrijdException</code> without
     * detail message.
     */
    public LaadWedstrijdException() {
        super(Taal.getWoordUitBundle("nogGeenWedstrijden"));
    }

    /**
     * Constructs an instance of <code>LaadWedstrijdException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LaadWedstrijdException(String msg) {
        super(msg);
    }

    public LaadWedstrijdException(String message, Throwable cause) {
        super(message, cause);
    }

    public LaadWedstrijdException(Throwable cause) {
        super(cause);
    }
    
    
}
