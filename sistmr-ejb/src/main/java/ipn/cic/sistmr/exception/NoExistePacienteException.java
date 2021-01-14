/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.exception;

/**
 *
 * @author J.PEREZ
 */
public class NoExistePacienteException extends Exception {
     public NoExistePacienteException(String msj){
        super(msj);
    }
    
    public NoExistePacienteException(String msj, Throwable th){
        super(msj, th);
    }
    
}
