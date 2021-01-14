/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.HospitalException;
import ipn.cic.sistmr.exception.NoExisteHospitalException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.modelo.EntHospital;
import ipn.cic.sistmr.sesion.HospitalSBLocal;
import ipn.cic.sistmr.util.Constantes;
import ipn.cic.web.sistmr.util.Mensaje;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;


/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Named(value="gestionHospitalMB")
@ViewScoped
public class GestionHospitalMB implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(GestionHospitalMB.class.getName());
    
    @EJB
    private HospitalSBLocal hospitalSB;
    
    @EJB
    private UtilWebSBLocal utilWebSB;
    
    private Boolean existeHosp;
    
    private String nomSistema;
    private EntHospital hospEnt;
    private Float lat;
    private Float lon;
    
    
    
    
    @PostConstruct
    public void iniciaMB(){
        
        hospEnt = new EntHospital();
        lat = lon = 0.0f;
        nomSistema = Constantes.getInstance().getString("NOMBRE_SISTEMA");
        try {
            existeHosp = hospitalSB.existeHospital();
            if(existeHosp){
                logger.log(Level.INFO,"Cargando datos primer hospital..."); //*  
                cargarPrimerHospital();  
            }            
        } catch (HospitalException ex) {
            logger.log(Level.SEVERE,"Error en MB al guardar hospital : {0}",ex.getMessage());
            FacesMessage msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "No es posible consultar datos de hospital.", 
                                                FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaMedico:msgAltaMed", msg);
        }
    }
    
    public void guardaHospital(){
        FacesMessage msg = null;
        String ug = lat.toString()+","+lon.toString();
        hospEnt.setUbicacionGeo(ug);
        try {
            hospEnt = hospitalSB.guardaHospital(hospEnt);
            logger.log(Level.INFO,"Datos de hospital guardados.");
        } catch (HospitalException ex) {
            logger.log(Level.SEVERE,"Error en MB al guardar hospital : {0}",ex.getMessage());
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error al guardar",
                                                "Error al intentar guardar datos de hospital intentelo más tarde.", 
                                                FacesMessage.SEVERITY_ERROR);
        }
       
        if(msg==null){
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Éxito",
                                                "Los datos de hospital se guardaron con éxito", 
                                                FacesMessage.SEVERITY_INFO);
        }
        utilWebSB.addMsg("frmGuardaHosp:msgs", msg);       
    }
    
    public void cargarPrimerHospital(){
        try{
            hospEnt = hospitalSB.getPrimerHospital();
            logger.log(Level.INFO,"\tDatos primer hospital recuperados.");        
        }catch(NoExisteHospitalException ex){            
            logger.log(Level.SEVERE,"Error al recuperar hospital : {0}",ex.getMessage());           
            FacesMessage msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("No existen datos del hospital",
                                                "No hay datos del hospital: capture informacion.", 
                                                FacesMessage.SEVERITY_WARN);
            utilWebSB.addMsg("frmGuardaHosp:msgs", msg);
        }
    }
    
    public void updateHospital(){        
        FacesMessage msg = null;
        String ug = lat.toString()+","+lon.toString();
        hospEnt.setUbicacionGeo(ug);
        try {
            hospEnt = hospitalSB.updateHospital(hospEnt);            
        } catch (UpdateEntityException ex) {
            logger.log(Level.SEVERE,"Error en MB al actualizar hospital : {0}",ex.getMessage());
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error al actualiar!",
                                                "Error al intentar actualizar datos de hospital intentelo más tarde.", 
                                                FacesMessage.SEVERITY_ERROR);
        }
       
        if(msg==null){
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Operacion Exitosa!",
                                                "Los datos de hospital se actualizaron correctamente.", 
                                                FacesMessage.SEVERITY_INFO);
        }
        utilWebSB.addMsg("frmExisteHosp:msgsEH", msg);    
    }
    
    /**
     * @return the nomSistema
     */
    public String getNomSistema() {
        return nomSistema;
    }

    /**
     * @param nomSistema the nomSistema to set
     */
    public void setNomSistema(String nomSistema) {
        this.nomSistema = nomSistema;
    }
    
    
    /**
     * @return the hospEnt
     */
    public EntHospital getHospEnt() {
        return hospEnt;
    }

    /**
     * @param hospEnt the hospEnt to set
     */
    public void setHospEnt(EntHospital hospEnt) {
        this.hospEnt = hospEnt;
    }

    /**
     * @return the lat
     */
    public Float getLat() {
        return lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(Float lat) {
        this.lat = lat;
    }

    /**
     * @return the lon
     */
    public Float getLon() {
        return lon;
    }

    /**
     * @param lon the lon to set
     */
    public void setLon(Float lon) {
        this.lon = lon;
    }
    
    /**
     * @return the existeHosp
     */
    public Boolean getExisteHosp() {
        return existeHosp;
    }

    /**
     * @param existeHosp the existeHosp to set
     */
    public void setExisteHosp(Boolean existeHosp) {
        this.existeHosp = existeHosp;
    }
    
}
