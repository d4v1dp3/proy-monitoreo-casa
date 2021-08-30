/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.comun;

import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import ipn.cic.sistmr.util.correo.CorreoSBLocal;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.mail.Session;

/**
 *
 * @author marcos
 */
@Named(value="recoverPasswordMB")
@ViewScoped
public class RecoverPasswordMB implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(RecoverPasswordMB.class.getName());
    
    private String email;
    
    @EJB
    private CorreoSBLocal correoSB;
    
    @EJB
    private UsuarioSBLocal usuarioSB;
         
    @Resource(name = "java:jboss/mail/sisMCasaMail")
    private Session mailSesion;
    
    private EntMedico medico;
    private EntUsuario usuario;
    
    @PostConstruct
    public void RecoverPasswordMB(){
    }
    
    public void recoverPassword(){
        String asunto = "Recuperación de contraseña";
        String mensaje = "Este correo fue generado de manera automática.\n"
                + "Contraseña: ";
        
        logger.log(Level.INFO,"Recuperando usuario de email: {0}",email);
        if((usuario = usuarioSB.getUsuariobyEmail(email))!=null){;
            logger.log(Level.INFO,"Usuario encontrado");
            String contrasenia = usuario.getContrasenia();
            correoSB.enviarCorreo(mailSesion,usuario.getEmail(),asunto,mensaje+contrasenia);
            enviado(); 
        }else{
            
            logger.log(Level.SEVERE,"Usuario no encontrado");
            error();
        }
        email=null;
    }

    public void enviado() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Correo enviado con la contraseña."));
    }
    
    public void error() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el correo."));
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }    
    
}
