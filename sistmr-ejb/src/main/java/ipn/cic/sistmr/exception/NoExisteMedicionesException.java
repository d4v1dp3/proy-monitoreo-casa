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
public class NoExisteMedicionesException extends Exception{
    
    public NoExisteMedicionesException(String msg) {
        super(msg);
    }
    
    public NoExisteMedicionesException(String msg, Throwable anid) {
        super(msg, anid);
    }
}
