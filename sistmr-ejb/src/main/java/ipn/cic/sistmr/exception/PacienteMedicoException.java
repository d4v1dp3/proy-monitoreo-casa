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
public class PacienteMedicoException extends Exception {

    /**
     * Constructs an instance of <code>PacienteMedicoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PacienteMedicoException(String msg) {
        super(msg);
    }
    
    public PacienteMedicoException(String msg, Throwable anid) {
        super(msg, anid);
    }
    
}
