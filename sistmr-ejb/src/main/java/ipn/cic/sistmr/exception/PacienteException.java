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
public class PacienteException extends Exception {

    /**
     * Constructs an instance of <code>PacienteException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PacienteException(String msg) {
        super(msg);
    }
    
    public PacienteException(String msg, Throwable anid) {
        super(msg,anid);
    }
}
