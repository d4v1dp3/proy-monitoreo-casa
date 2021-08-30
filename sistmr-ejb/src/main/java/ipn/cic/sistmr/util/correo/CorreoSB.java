/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.util.correo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author marcos
 */
@Stateless
public class CorreoSB implements CorreoSBLocal {
    private static final Logger logger = Logger.getLogger(CorreoSB.class.getName());
    
    @Override
    public Boolean enviarCorreo(Session sesion, String correoDestinatario, String asunto, String cuerpo) {
        try {
            logger.log(Level.INFO, "Entrando a enviar correo ");
            Message m = new MimeMessage(sesion);
            m.setRecipient(Message.RecipientType.TO, new InternetAddress(correoDestinatario));
            m.setSubject(asunto);
            m.setText(cuerpo);
            Transport.send(m);
            logger.log(Level.INFO, "Correo enviado a: {0}", correoDestinatario);
            return Boolean.TRUE;
        } catch (MessagingException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return null;  
    }
}
