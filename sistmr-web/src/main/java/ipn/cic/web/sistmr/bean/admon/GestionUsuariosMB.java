/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.UsuarioException;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import ipn.cic.web.sistmr.util.Mensaje;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 * ManageBean que se utiliza para carga de usuarios en el sistema
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Named(value = "gestionUsuariosMB")
@ViewScoped
public class GestionUsuariosMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(GestionUsuariosMB.class.getName());

    @EJB
    private UsuarioSBLocal usuarioSB;
    @EJB
    private UtilWebSBLocal utilWebSB;

    private List<EntUsuario> usuariosComp;
    private EntUsuario usuarioEditar;

    @PostConstruct
    public void cargaUsuarios() {
        FacesMessage msg=null;
        try {
            logger.log(Level.INFO,"Entra a cargar usuarios.");
            usuariosComp = usuarioSB.getUsuarios();
        } catch (UsuarioException ex) {
            logger.log(Level.SEVERE, "Error en MB al guardar hospital : {0}", ex.getMessage());
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error usuarios:",
                            "Error al intentar recuperar los usuarios intente más tarde",
                            FacesMessage.SEVERITY_ERROR);
        }
        
        if(msg==null){
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Éxito:",
                            "Usuarios cargados correctamente",
                            FacesMessage.SEVERITY_INFO);
        }
        utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
        //PrimeFaces.current().ajax().update("frGestUsuarios:msgsGU");
    }
    
    public void altaMedico(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 700);
        options.put("height", 640);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        PrimeFaces.current().dialog().openDynamic("usuarios/dialAltaMedico", options, null);
    }
    
    public void retornoAltaMedico(SelectEvent event){
        retornoEditaUsuario(event);        
    }
    
    
    public void altaPaciente(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 700);
        options.put("height", 640);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        PrimeFaces.current().dialog().openDynamic("usuarios/dialAltaPaciente", options, null);
    }
    
    public void retornoAltaPaciente(){
        
    }
    
    public void editarUsuario() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 640);
        options.put("height", 340);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        PrimeFaces.current().dialog().openDynamic("usuarios/dialEditaUsuario", options, null);
    }

    public void retornoEditaUsuario(SelectEvent event) {
        FacesMessage msg = null;

        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();

        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo ",
                            "Diálogo cerrado sin aplicar cambios",
                            FacesMessage.SEVERITY_INFO);
        }
        cargaUsuarios();
        utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
    }

    /**
     * @return the usuariosComp
     */
    public List<EntUsuario> getUsuariosComp() {
        return usuariosComp;
    }

    /**
     * @param usuariosComp the usuariosComp to set
     */
    public void setUsuariosComp(List<EntUsuario> usuariosComp) {
        this.usuariosComp = usuariosComp;
    }

    /**
     * @return the usuarioEditar
     */
    public EntUsuario getUsuarioEditar() {
        return usuarioEditar;
    }

    /**
     * @param usuarioEditar the usuarioEditar to set
     */
    public void setUsuarioEditar(EntUsuario usuarioEditar) {
        this.usuarioEditar = usuarioEditar;
    }

}
