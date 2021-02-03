/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.exception.ValoresReferenciaException;
import ipn.cic.sistmr.modelo.EntValoresReferencia;
import ipn.cic.sistmr.sesion.ValoresReferenciaSBLocal;
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
 * @author J.Perez
 */
@Named(value="valoresReferenciaMB")
@ViewScoped
public class ValoresReferenciaMB implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ValoresReferenciaMB.class.getName());
    
    @EJB
    private ValoresReferenciaSBLocal valoresreferenciaSB;
    
    @EJB
    private UtilWebSBLocal utilWebSB;

    private String nomSistema;
    private EntValoresReferencia valrefEnt;
    boolean existeVref;

    @PostConstruct
    public void iniciaMB(){
        
        valrefEnt = new EntValoresReferencia();
        valrefEnt.setIdValref(Short.parseShort("1"));
        nomSistema = Constantes.getInstance().getString("NOMBRE_SISTEMA");
        
        try {
            existeVref = valoresreferenciaSB.existenVref();
            if(existeVref){
                logger.log(Level.INFO,"Cargando valores de referencia..."); //*  
                cargarValoresDeReferencia();  
            }            
        } catch (ValoresReferenciaException ex) {
            logger.log(Level.SEVERE,"Error en MB al obtener valores de referencia : {0}",ex.getMessage());
            FacesMessage msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "No es posible consultar valores de referencia.", 
                                                FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmValRef:msgsVR", msg);
        }
    }
    
    public void guardaValoresReferencia(){
        FacesMessage msg = null;

        try {
            valrefEnt = valoresreferenciaSB.guardaValoresReferencia(valrefEnt);
            logger.log(Level.INFO,"Datos de hospital guardados.");
        } catch (ValoresReferenciaException ex) {
            logger.log(Level.SEVERE,"Error en MB al guardar valores de referencia : {0}",ex.getMessage());
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error al guardar",
                                                "Error al intentar guardar valores de referencia, intentelo más tarde.", 
                                                FacesMessage.SEVERITY_ERROR);
        }
       
        if(msg==null){
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Éxito",
                                                "Los valores de referencia se guardaron con éxito", 
                                                FacesMessage.SEVERITY_INFO);
        }
        utilWebSB.addMsg("frmValRef:msgsVR", msg);       
    }
    
    public void cargarValoresDeReferencia(){
        try{
            valrefEnt = valoresreferenciaSB.getValoresReferencia();
            logger.log(Level.INFO,"\tDatos primer hospital recuperados.");        
        }catch(ValoresReferenciaException ex){            
            logger.log(Level.SEVERE,"Error al recuperar los valores de referencia : {0}",ex.getMessage());           
            FacesMessage msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("No existen valores de referencia.",
                                                "No hay valores de referencia: capture informacion.", 
                                                FacesMessage.SEVERITY_WARN);
            utilWebSB.addMsg("frmValRef:msgsVR", msg);
        }
    }
    
    public void updateValoresDeReferencia(){        
        FacesMessage msg = null;;
        try {
            valrefEnt = valoresreferenciaSB.updateValoresReferencia(valrefEnt);            
        } catch (UpdateEntityException ex) {
            logger.log(Level.SEVERE,"Error en MB al actualizar valores de referencia : {0}",ex.getMessage());
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error al actualiar!",
                                                "Error al intentar actualizar valores de referencia, intentelo más tarde.", 
                                                FacesMessage.SEVERITY_ERROR);
        }
       
        if(msg==null){
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Operacion Exitosa!",
                                                "Valores de referencia actualizados correctamente.", 
                                                FacesMessage.SEVERITY_INFO);
        }
        utilWebSB.addMsg("frmValRef:msgsVR", msg);    
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
    
    public EntValoresReferencia getValrefEnt() {
        return valrefEnt;
    }

    public void setValrefEnt(EntValoresReferencia valrefEnt) {
        this.valrefEnt = valrefEnt;
    }

    public boolean isExisteVref() {
        return existeVref;
    }

    public void setExisteVref(boolean existeVref) {
        this.existeVref = existeVref;
    }
 
    
}

