/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.exception;

/**
 *
 * @author J.Perez
 */
public class NoExistePacienteMedicoException extends Exception{
    
    public NoExistePacienteMedicoException(String msg) {
        super(msg);
    }
    
    public NoExistePacienteMedicoException(String msg, Throwable anid) {
        super(msg, anid);
    }
}
