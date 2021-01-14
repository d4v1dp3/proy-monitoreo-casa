/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.exception;

/**
 *
 * @author iliac
 */
public class RolException extends Exception {

    /**
     * Constructs an instance of <code>RolException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public RolException(String msg) {
        super(msg);
    }
    
    public RolException(String msg, Throwable anid) {
        super(msg, anid);
    }
}
