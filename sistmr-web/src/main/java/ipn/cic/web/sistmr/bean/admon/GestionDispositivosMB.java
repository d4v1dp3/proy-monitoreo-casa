/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.CaretaException;
import ipn.cic.sistmr.exception.CaretaHospitalException;
import ipn.cic.sistmr.exception.NoExisteCaretaException;
import ipn.cic.sistmr.exception.NoExisteHospitalException;
import ipn.cic.sistmr.exception.RemoveEntityException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.modelo.EntCareta;
import ipn.cic.sistmr.modelo.EntCaretaHospital;
import ipn.cic.sistmr.modelo.EntHospital;
import ipn.cic.sistmr.sesion.CaretaHospitalSBLocal;
import ipn.cic.sistmr.sesion.CaretaSBLocal;
import ipn.cic.sistmr.sesion.HospitalSBLocal;
import ipn.cic.web.sistmr.util.Mensaje;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.ArrayList;
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
 *
 * @author J.Perez
 */
@Named(value = "gestionDispositivosMB")
@ViewScoped
public class GestionDispositivosMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(GestionUsuariosMB.class.getName());

    @EJB
    private CaretaSBLocal caretaSB;
    @EJB
    private CaretaHospitalSBLocal caretahospitalSB;
    @EJB
    private HospitalSBLocal hospitalSB;
    @EJB
    private UtilWebSBLocal utilWebSB;

    private List<EntCareta> caretas;//Caretas Asginadas
    private List<EntCareta> caretasNA;//Caretas no Asignadas
    
    private EntCareta caretaEditar;
    private EntCareta caretaEliminar;    
    private EntCareta caretaGuard;
    
    private EntCaretaHospital caretaHospital;
    private EntHospital entHospital;
    
    private String fechaManufactura = "";    
    private long idCareta = 0;
    private long noSerie = 0;
    

    @PostConstruct
    public void cargarDispositivos() {
        caretaGuard = new EntCareta();
        caretaEditar = new EntCareta();
        caretaEliminar = new EntCareta();
        
        caretaHospital = new EntCaretaHospital();
        try {
            entHospital = hospitalSB.getPrimerHospital();
        } catch (NoExisteHospitalException ex) {
            Logger.getLogger(GestionDispositivosMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        FacesMessage msg=null;
        try {
            logger.log(Level.INFO,"Entra a cargar dispositivos.");
            caretas = caretaSB.getCaretas();
        } catch (NoExisteCaretaException ex) {
            logger.log(Level.SEVERE, "Error en MB al cargar caretas : {0}", ex.getMessage());
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error dispositivos:",
                            "Error al intentar recuperar caretas intente más tarde.",
                            FacesMessage.SEVERITY_ERROR);
        }
        
        if(msg==null){
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Éxito:",
                            "Dispositivos cargados correctamente",
                            FacesMessage.SEVERITY_INFO);
        }
        //utilWebSB.addMsg("frGestDispositivos:msgsGD", msg);
//        PrimeFaces.current().ajax().update("frGestDispositivos:msgsGD");
    }
    
    public void guardarDispositivo(){
        logger.log(Level.INFO,"Entrando a Guardar Dispositivo.");
        
        caretaGuard.setFechaManufactura(fechaManufactura);
        caretaGuard.setNoSerie(noSerie);
        
        try{            
            caretaGuard = caretaSB.guardaCareta(caretaGuard);
            
            caretaHospital.setEntCareta(caretaGuard);
            
            //entHospital = hospitalSB.getPrimerHospital();
            
            caretaHospital.setEntHospital(entHospital);
            
            caretaHospital = caretahospitalSB.guardaCaretaHospital(caretaHospital);
            
        } catch (CaretaException ex) {
            FacesMessage msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "Error al intentar guardar careta :"+ex.getMessage(), 
                                                FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmRegDispositivo:msgRegDisp", msg);
            cerrarDialogo(msg);
        } catch (CaretaHospitalException ex) {
            FacesMessage msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "Error al intentar guardar caretahospital :"+ex.getMessage(), 
                                                FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmRegDispositivo:msgRegDisp", msg);
            cerrarDialogo(msg);
        }
        
        FacesMessage msg=null;
        if (caretaGuard == null){
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "Imposible guardar datos de dispositivo, intente más tarde", 
                                                FacesMessage.SEVERITY_ERROR);
            cerrarDialogo(msg);
        }else{
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Exíto",
                                                "El registro de careta se realizó correctamente : id="+this.caretaGuard.getIdCareta(), 
                                                FacesMessage.SEVERITY_INFO);
        }
        utilWebSB.addMsg("frmRegDispositivo:msgRegDisp", msg);        
        cerrarDialogo(msg);
    }
    
    public void guardarCambiosDispositivo(){
        logger.log(Level.INFO,"Entrando a actualizar dispositivo.");        
        caretaGuard.setFechaManufactura(fechaManufactura);
        try{            
            caretaGuard = caretaSB.guardaCareta(caretaGuard);
        } catch (CaretaException ex) {
            FacesMessage msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "Error al intentar guardar careta :"+ex.getMessage(), 
                                                FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmRegDispositivo:msgRegDisp", msg);
            return;
        }
        FacesMessage msg=null;
        if (caretaGuard == null){
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "Imposible guardar datos de dispositivo, intente más tarde", 
                                                FacesMessage.SEVERITY_ERROR);
            cerrarDialogo(msg);
        }else{
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Exíto",
                                                "El registro de careta se realizó correctamente : id="+this.caretaGuard.getIdCareta(), 
                                                FacesMessage.SEVERITY_INFO);
        }
        utilWebSB.addMsg("frmRegDispositivo:msgRegDisp", msg);        
        cerrarDialogo(msg);
    }
    
    
    public void cerrarDialogo(){
        FacesMessage mensaje = Mensaje.getInstance()
                                      .getMensaje("CERRANDO_DIALOGO", "CERRANDO_CORRECTAMENTE",
                                                   FacesMessage.SEVERITY_INFO);
        cerrarDialogo(mensaje);
    }
    
    public void cerrarDialogo(FacesMessage mensaje){
        PrimeFaces.current().dialog().closeDynamic(mensaje);
    }
    
    public void registrarDispositivo(){        
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 580);
        options.put("height", 500);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        PrimeFaces.current().dialog().openDynamic("dispositivos/dialRegistrarDispositivo", options, null);
    }
    
    public void retornoRegistrarDispositivo(){
        cargarDispositivos();
    }
    
    
    
    public void editarDispositivo() {
    
        idCareta = caretaEditar.getIdCareta();
        noSerie = caretaEditar.getNoSerie();
        fechaManufactura = caretaEditar.getFechaManufactura().toString();
        
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 580);
        options.put("height", 500);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        
        //Envio de Parametros
        Map<String, List<String>> parametros = new HashMap<>();
        
        List<String> idcareta = new ArrayList<>();
        idcareta.add(caretaEditar.getIdCareta()+"");
        
        List<String> nSerie = new ArrayList<>();
        nSerie.add(caretaEditar.getNoSerie()+"");
        
        List<String> fManufactura = new ArrayList<>();
        fManufactura.add(caretaEditar.getFechaManufactura().toString());
        
        parametros.put("idCareta", idcareta);
        parametros.put("noSerie", nSerie);
        parametros.put("fecManufac", fManufactura);        
        
        logger.log(Level.INFO, "ID Dispositivo Seleccionado: {0}", caretaEditar.getIdCareta());
        PrimeFaces.current().dialog().openDynamic("dispositivos/dialEditarDispositivo", options, parametros);
    }
    
    

    public void retornoEditarDispositivo(SelectEvent event) {
        FacesMessage msg = null;

        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();

        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo ",
                            "Diálogo cerrado sin aplicar cambios",
                            FacesMessage.SEVERITY_INFO);
        }
        
        utilWebSB.addMsg("frGestDispositivos:msgsGD", msg);
        cargarDispositivos();
    }
    
    public void actualizarDispositivo(){
        FacesMessage msg = null;
        try {         
            caretaEditar.setIdCareta(idCareta);
            caretaEditar.setNoSerie(noSerie);
            caretaEditar.setFechaManufactura(fechaManufactura);
            
            caretaSB.updateCareta(caretaEditar);         
            
            
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo ",
                            "Datos de dispositivo actualizados correctamente.",
                            FacesMessage.SEVERITY_INFO);
            
            
            cerrarDialogo(msg);
        } catch (UpdateEntityException ex) {
            Logger.getLogger(GestionDispositivosMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void confirmarBaja() {

        idCareta = caretaEliminar.getIdCareta();
        noSerie = caretaEliminar.getNoSerie();
        logger.log(Level.INFO, "ID Dispositivo Seleccionado: {0}", caretaEliminar.getIdCareta());
        
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 350);
        options.put("height", 150);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        
        //Envio de Parametros
        Map<String, List<String>> parametros = new HashMap<>();
        
        List<String> idcareta = new ArrayList<>();
        idcareta.add(caretaEliminar.getIdCareta()+"");
        
        List<String> nSerie = new ArrayList<>();
        nSerie.add(caretaEliminar.getNoSerie()+"");
        
        List<String> fManufactura = new ArrayList<>();
        fManufactura.add(caretaEliminar.getFechaManufactura().toString());
                
        parametros.put("idCareta", idcareta);
        parametros.put("noSerie", nSerie);    
        parametros.put("fecManufac", fManufactura);        
        
        PrimeFaces.current().dialog().openDynamic("dispositivos/dialConfirmacion", options, parametros);
    }
    
    public void eliminarDispositivo(){
        caretaEliminar.setIdCareta(idCareta);
        caretaEliminar.setNoSerie(noSerie);
        caretaEliminar.setFechaManufactura(fechaManufactura);
        
        FacesMessage msg = null;
        logger.log(Level.INFO, "Dispositivo Seleccionado: ID[{0}]",
                caretaEliminar.getIdCareta());
        
        //AQUI DEBERIA ABRIR UNA ADVERTENCIA 
        
        try {
            boolean borrado = caretaSB.borrarCareta(caretaEliminar);
            if(borrado){
                logger.log(Level.INFO, "Dispositivo eliminado.");
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo ",
                            "Dispositivo eliminado correctamente.",
                            FacesMessage.SEVERITY_INFO);
            }
            
            cerrarDialogo(msg);
            
        } catch (RemoveEntityException ex) {
            Logger.getLogger(GestionDispositivosMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void retornoEliminarDispositivo(SelectEvent event){
        FacesMessage msg = null;

        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();

        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo ",
                            "Dispositivo eliminado.",
                            FacesMessage.SEVERITY_INFO);
        }
        
        utilWebSB.addMsg("frGestDispositivos:msgsGD", msg);
        cargarDispositivos();
    }

    public CaretaSBLocal getCaretaSB() {
        return caretaSB;
    }

    public void setCaretaSB(CaretaSBLocal caretaSB) {
        this.caretaSB = caretaSB;
    }

    public List<EntCareta> getCaretas() {
        return caretas;
    }

    public void setCaretas(List<EntCareta> caretas) {
        this.caretas = caretas;
    }

    public EntCareta getCaretaEditar() {
        return caretaEditar;
    }

    public void setCaretaEditar(EntCareta caretaEditar) {
        this.caretaEditar = caretaEditar;
    }

    public EntCareta getCaretaGuard() {
        return caretaGuard;
    }

    public void setCaretaGuard(EntCareta caretaGuard) {
        this.caretaGuard = caretaGuard;
    }

    public String getFechaManufactura() {
        return fechaManufactura;
    }

    public void setFechaManufactura(String fechaManufantura) {
        this.fechaManufactura = fechaManufantura;
    }

    public EntCareta getCaretaEliminar() {
        return caretaEliminar;
    }

    public void setCaretaEliminar(EntCareta caretaEliminar) {
        this.caretaEliminar = caretaEliminar;
    }

    public long getIdCareta() {
        return idCareta;
    }

    public void setIdCareta(long idCareta) {
        this.idCareta = idCareta;
    }

    public List<EntCareta> getCaretasNA() {
        return caretasNA;
    }

    public void setCaretasNA(List<EntCareta> caretasNA) {
        this.caretasNA = caretasNA;
    }

    public long getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(long noSerie) {
        this.noSerie = noSerie;
    }

    public EntHospital getEntHospital() {
        return entHospital;
    }

    public void setEntHospital(EntHospital entHospital) {
        this.entHospital = entHospital;
    }
    
    

}

