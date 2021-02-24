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
public class EstadoPacienteException extends Exception {

    /**
     * Constructs an instance of <code>EstadoPacienteException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EstadoPacienteException(String msg) {
        super(msg);
    }
    
    public EstadoPacienteException(String msg, Throwable anid) {
        super(msg, anid);
    }
}
