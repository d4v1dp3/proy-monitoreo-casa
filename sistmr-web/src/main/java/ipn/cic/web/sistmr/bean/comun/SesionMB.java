/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.comun;

import ipn.cic.sistmr.util.Constantes;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 * ManageBean para controlar cierre de sesion de usuario autenticado
 * 
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */

@Named(value="sesionMB")
@SessionScoped
public class SesionMB implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(SesionMB.class.getName());

    public SesionMB() {
        logger.log(Level.INFO, "Creando el SesionSB .. !!!!!: ");
    }

    /**
     * Invalida la sesion del usuario actualmente autenticado
     *
     * @return null
     */
    public String cerrarSesion() {
        Constantes constantes = Constantes.getInstance();
        logger.log(Level.INFO, "Entrando a cerrar sesion: ");
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession sesion = (HttpSession) contexto.getSession(false);
        sesion.invalidate();
        String contextoApp = constantes.getString("CONTEXTO_APLICACION");
        try {
            contexto.redirect(contextoApp);
        } catch (java.io.IOException f) {
            logger.log(Level.SEVERE, "IOException: {0}", f.getMessage());
        }
        return null;
    }

    public void buttonAction(ActionEvent actionEvent) {
        addMessage("Cerrando Sesion!!");
    }

    public void addMessage(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
