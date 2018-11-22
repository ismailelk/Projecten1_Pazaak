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
public class KoopKaartException extends IllegalArgumentException{

    /**
     * Creates a new instance of <code>KoopKaartException</code> without detail
     * message.
     */
    public KoopKaartException() {
        super(Taal.getWoordUitBundle("eriseenfoutopgetredenbijhetkopenvandekaart"));
    }

    /**
     * Constructs an instance of <code>KoopKaartException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public KoopKaartException(String msg) {
        super(msg);
    }
}
