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
public class NoExisteValoresRefException extends Exception{
    public NoExisteValoresRefException(String msg) {
        super(msg);
    }
    
    public NoExisteValoresRefException(String msg, Throwable anid) {
        super(msg, anid);
    }
}

