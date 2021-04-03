/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.exception;

/**
 *
 * @author J.Perez
 */
public class BitacoraException extends Exception {

    /**
     * Constructs an instance of <code>CatalogosException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BitacoraException(String msg) {
        super(msg);
    }
    
    public BitacoraException(String msg, Throwable anid) {
        super(msg, anid);
    }
}
