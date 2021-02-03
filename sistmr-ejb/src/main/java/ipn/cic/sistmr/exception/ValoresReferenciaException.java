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
public class ValoresReferenciaException extends Exception{
    public ValoresReferenciaException(String msg) {
        super(msg);
    }
    
    public ValoresReferenciaException(String msg, Throwable anid) {
        super(msg,anid);
    }
}