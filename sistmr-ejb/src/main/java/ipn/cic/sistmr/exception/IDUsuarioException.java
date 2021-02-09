/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.exception;

/**
 *
 * @author iliaco
 */
public class IDUsuarioException extends Exception {

    /**
     * Creates a new instance of <code>IDUsuarioException</code> without detail
     * message.
     */
    public IDUsuarioException() {
    }

    /**
     * Constructs an instance of <code>IDUsuarioException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IDUsuarioException(String msg) {
        super(msg);
    }
    
    public IDUsuarioException(String msg, Throwable anid) {
        super(msg,anid);
    }
}
