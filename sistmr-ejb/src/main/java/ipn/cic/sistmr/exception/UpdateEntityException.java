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
public class UpdateEntityException extends Exception {


    /**
     * Constructs an instance of <code>UpdateEntityException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UpdateEntityException(String msg) {
        super(msg);
    }
    
        public UpdateEntityException(String msg, Throwable anid) {
        super(msg,anid);
    }
}
