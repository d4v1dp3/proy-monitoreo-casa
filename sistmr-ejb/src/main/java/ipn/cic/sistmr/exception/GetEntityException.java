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
public class GetEntityException extends Exception {

    /**
     * Constructs an instance of <code>GetEntityException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public GetEntityException(String msg) {
        super(msg);
    }
    
    public GetEntityException(String msg, Throwable anid) {
        super(msg,anid);
    }
}
