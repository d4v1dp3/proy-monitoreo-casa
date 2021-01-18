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
public class NoExisteHospitalException extends Exception{
    
    public NoExisteHospitalException(String msj){
        super(msj);
    }
    
    public NoExisteHospitalException(String msj, Throwable th){
        super(msj, th);
    }
    
}
