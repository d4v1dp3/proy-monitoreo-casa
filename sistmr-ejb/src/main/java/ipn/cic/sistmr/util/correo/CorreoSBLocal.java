/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.util.correo;

import javax.ejb.Local;
import javax.mail.Session;

/**
 *
 * @author marcos
 */
@Local
public interface CorreoSBLocal {
    public Boolean enviarCorreo(Session sesion, String correoDestinatario, String asunto, String cuerpo);
}
