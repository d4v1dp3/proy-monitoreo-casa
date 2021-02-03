/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.exception;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
public class CaretaException extends Exception {

    /**
     * Constructs an instance of <code>CatalogosException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CaretaException(String msg) {
        super(msg);
    }
    
    public CaretaException(String msg, Throwable anid) {
        super(msg, anid);
    }
}
