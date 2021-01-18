/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.exception;

/**
 *  Exception generada en el trabajo con EntMedico.
 * @author Iliac Huerta Trujillo
 */
public class MedicoException extends Exception {

    /**
     * Constructs an instance of <code>MedicoException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public MedicoException(String msg) {
        super(msg);
    }
    
    public MedicoException(String msg, Throwable anid) {
        super(msg,anid);
    }
}
