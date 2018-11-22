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
public class MenukeuzeBuitenInterval extends IllegalArgumentException {

    /**
     * Creates a new instance of <code>MenukeuzeBuitenInterval</code> without
     * detail message.
     */
    public MenukeuzeBuitenInterval() {
        super(Taal.getWoordUitBundle("Demenukeuzeligtbuitenhetinterval"));
    }

    /**
     * Constructs an instance of <code>MenukeuzeBuitenInterval</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
       
    public MenukeuzeBuitenInterval(String msg) {
        super(msg);
    }
    public MenukeuzeBuitenInterval(String message, Throwable cause)
    {
        super(message, cause);
    }

    public MenukeuzeBuitenInterval(Throwable cause)
    {
        super(cause);
    }

}
