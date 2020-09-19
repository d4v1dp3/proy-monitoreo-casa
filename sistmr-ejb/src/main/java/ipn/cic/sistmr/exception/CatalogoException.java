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
public class CatalogoException extends Exception {

    /**
     * Constructs an instance of <code>CatalogosException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CatalogoException(String msg) {
        super(msg);
    }
    
    public CatalogoException(String msg, Throwable anid) {
        super(msg, anid);
    }
}
