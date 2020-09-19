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
public class SaveEntityException extends Exception {


    /**
     * Constructs an instance of <code>SaveEntityException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public SaveEntityException(String msg) {
        super(msg);
    }
    
    public SaveEntityException(String msg, Throwable anid) {
        super(msg,anid);
    }
}
