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
public class NoExisteEstadoPacException extends Exception{
    public NoExisteEstadoPacException(String msg) {
        super(msg);
    }
    
    public NoExisteEstadoPacException(String msg, Throwable anid) {
        super(msg, anid);
    }
}
