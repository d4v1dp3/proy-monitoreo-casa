/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.exception;

/**
 *
 * @author J.PEREZ
 */
public class NoExisteCaretaException extends Exception {

    /**
     * Creates a new instance of <code>NoExisteCaretaException</code> without
     * detail message.
     */
    public NoExisteCaretaException() {
    }

    /**
     * Constructs an instance of <code>NoExisteCaretaException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoExisteCaretaException(String msg) {
        super(msg);
    }
}
