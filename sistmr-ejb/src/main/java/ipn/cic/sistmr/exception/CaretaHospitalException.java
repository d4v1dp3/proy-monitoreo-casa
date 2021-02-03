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
public class CaretaHospitalException extends Exception{
    public CaretaHospitalException(String msg) {
        super(msg);
    }
    
    public CaretaHospitalException(String msg, Throwable anid) {
        super(msg,anid);
    }
}