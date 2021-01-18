/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.web.sistmr.util.Mensaje;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.jboss.logging.Logger;
import org.primefaces.PrimeFaces;

/**
 *ManageBean usado para la edición de usuarios, solo se permite la actualización
 * de datos generales de usuario y datos generales de persona.
 * 
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */

@Named(value="editaUsuarioMB")
@ViewScoped
public class EditaUsuarioMB implements Serializable{
    private static final Logger logger = Logger.getLogger(EditaUsuarioMB.class.getName());
    
    
    public void cerrarDialogo(){
        FacesMessage mensaje = Mensaje.getInstance()
                                      .getMensaje("CERRANDO_DIALOGO", "CERRANDO_CORRECTAMENTE",
                                                   FacesMessage.SEVERITY_INFO);
        PrimeFaces.current().dialog().closeDynamic(mensaje);
    }
}
