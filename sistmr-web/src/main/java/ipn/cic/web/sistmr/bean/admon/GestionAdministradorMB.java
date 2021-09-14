/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.CatalogoException;
import ipn.cic.sistmr.exception.IDUsuarioException;
import ipn.cic.sistmr.modelo.EntGenero;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.CatalogoSBLocal;
import ipn.cic.web.sistmr.bean.vo.PersonaVO;
import ipn.cic.web.sistmr.bean.vo.UsuarioVO;
import ipn.cic.web.sistmr.delegate.GestionAdministradorBDLocal;
import ipn.cic.web.sistmr.util.Mensaje;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;

/**
 *
 * @author marcos
 */
@Named(value = "gestionAdministradorMB")
@ViewScoped
public class GestionAdministradorMB implements Serializable {

    private static final Logger logger = Logger.getLogger(GestionMedicoMB.class.getName());
    private UsuarioVO datUsuario;
    private PersonaVO datPersona;
    private EntUsuario entUsuario;
    private List<EntGenero> catGenero;

    @EJB
    GestionAdministradorBDLocal gstAdm;
    @EJB
    UtilWebSBLocal utilWebSB;
    @EJB
    CatalogoSBLocal catalogoSB;

    @PostConstruct
    public void iniciaVO() {
        datUsuario = new UsuarioVO();
        datPersona = new PersonaVO();

        try {
            setCatGenero((List<EntGenero>) catalogoSB.getCatalogo("EntGenero"));
        } catch (CatalogoException ex) {
            FacesMessage msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "No es posible recuperar catálogo de género :" + ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaAdministrador:msgAltaAdministrador", msg);
            return;
        }
    }

    public void guardarAdministrador() {
        logger.log(Level.INFO, "Inicia proceso de guardar administrador");
        entUsuario = null;
        try {
            logger.log(Level.INFO, "Guardando entidad administrador");
            entUsuario = gstAdm.guardarAdministradorNuevo(datPersona, datUsuario);
        } catch (IDUsuarioException ex) {
            logger.log(Level.INFO, "Error al guardar administrador: {0}", ex.getMessage());
            FacesMessage msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "El ID de usuario ya existe, CAMBIARLO",
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaAdministrador:msgAltaAdm", msg);
            return;
        }

        FacesMessage msg = null;
        if (entUsuario == null) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Imposible guardar datos del administrador, intentelo más tarde.",
                            FacesMessage.SEVERITY_ERROR);
            cerrarDialogo(msg);
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Administrador Registrado",
                            "El registro del administrador se realizó correctamente",
                            FacesMessage.SEVERITY_INFO);
        }

        cerrarDialogo(msg);
    }

    public void cerrarDialogo(){
        logger.log(Level.INFO,"Invocando cerrar dialogo.");
        cerrarDialogo(null);
    }
    
    private void cerrarDialogo(FacesMessage mensaje) {
        PrimeFaces.current().dialog().closeDynamic(mensaje);
    }

    public UsuarioVO getDatUsuario() {
        return datUsuario;
    }

    public void setDatUsuario(UsuarioVO datUsuario) {
        this.datUsuario = datUsuario;
    }

    public PersonaVO getDatPersona() {
        return datPersona;
    }

    public void setDatPersona(PersonaVO datPersona) {
        this.datPersona = datPersona;
    }

    public EntUsuario getEntUsuario() {
        return entUsuario;
    }

    public void setEntUsuario(EntUsuario entUsuario) {
        this.entUsuario = entUsuario;
    }

    public List<EntGenero> getCatGenero() {
        return catGenero;
    }

    public void setCatGenero(List<EntGenero> catGenero) {
        this.catGenero = catGenero;
    }

    public UtilWebSBLocal getUtilWebSB() {
        return utilWebSB;
    }

    public void setUtilWebSB(UtilWebSBLocal utilWebSB) {
        this.utilWebSB = utilWebSB;
    }

    public CatalogoSBLocal getCatalogoSB() {
        return catalogoSB;
    }

    public void setCatalogoSB(CatalogoSBLocal catalogoSB) {
        this.catalogoSB = catalogoSB;
    }

}
