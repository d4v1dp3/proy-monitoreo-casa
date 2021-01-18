/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.exception;

/**
 * Excepción lanzada al operar con la entidad EntHospital
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
public class HospitalException extends Exception {

    /**
     * Constructs an instance of <code>HospitalException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public HospitalException(String msg) {
        super(msg);
    }
    
    public HospitalException(String msg, Throwable anid) {
        super(msg,anid);
    }
    
}
