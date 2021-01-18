/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.exception;

/**
 *
 * @author marcos
 */
public class NoExistePacienteDashException extends Exception {
    
    public NoExistePacienteDashException(String msg) {
        super(msg);
    }
    
    public NoExistePacienteDashException(String msg, Throwable anid) {
        super(msg, anid);
    }
}
