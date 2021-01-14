/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.exception;

/**
 * Excepción lanzada al operar con MedidasVO
 * @author J.Perez
 */
public class MedidasException extends Exception{
    
    /**
     * Constructs an instance of <code>MedidasException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public MedidasException(String msg) {
        super(msg);
    }
    
    public MedidasException(String msg, Throwable anid) {
        super(msg,anid);
    }
}
