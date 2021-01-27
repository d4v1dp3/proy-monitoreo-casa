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
public class AntecedentesException extends Exception{
    public AntecedentesException(String msg) {
        super(msg);
    }
    
    public AntecedentesException(String msg, Throwable anid) {
        super(msg,anid);
    }
}