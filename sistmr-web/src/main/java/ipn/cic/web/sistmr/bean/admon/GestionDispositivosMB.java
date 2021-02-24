/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.CaretaException;
import ipn.cic.sistmr.exception.CaretaHospitalException;
import ipn.cic.sistmr.exception.CatalogoException;
import ipn.cic.sistmr.exception.NoExisteHospitalException;
import ipn.cic.sistmr.exception.RemoveEntityException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.modelo.EntCareta;
import ipn.cic.sistmr.modelo.EntCaretaHospital;
import ipn.cic.sistmr.modelo.EntCaretaHospitalPK;
import ipn.cic.sistmr.modelo.EntHospital;
import ipn.cic.sistmr.sesion.CaretaHospitalSBLocal;
import ipn.cic.sistmr.sesion.CaretaSBLocal;
import ipn.cic.sistmr.sesion.CatalogoSBLocal;
import ipn.cic.sistmr.sesion.HospitalSBLocal;
import ipn.cic.web.sistmr.util.Mensaje;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    @EJB
    CatalogoSBLocal catalogoSB; 

    private List<EntCaretaHospital> caretashospital;//Caretas Asginadas
    private List<EntCaretaHospital> caretashospitalNA;//Caretas no Asignadas
    
    private EntCaretaHospital caretaHospitalEditar;
    private EntCaretaHospital caretaHospitalEliminar;
    
    private EntCareta caretaEditar;
    private EntCareta caretaEliminar;    
    private EntCareta caretaGuard;
    private List<EntHospital> listHospital;
    
    private EntCaretaHospital caretaHospital;
    private EntHospital entHospital;
    
    private String fechaManufactura = "";    
    private long idCareta = 0;
    private long noSerie = 0;
    private Integer idHosp=0;
    
    

    @PostConstruct
    public void cargarDispositivos() {
        caretaGuard = new EntCareta();
        caretaEditar = new EntCareta();
        caretaEliminar = new EntCareta();
        listHospital = new ArrayList();
        
//        caretaHospitalEditar = new EntCaretaHospital();
        
        caretaHospital = new EntCaretaHospital();
        
        try {
            //Cargar Lista de Hospitales
            setListHospital((List<EntHospital>) catalogoSB.getCatalogo("EntHospital"));
        } catch (CatalogoException ex) {
            Logger.getLogger(GestionDispositivosMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Cargar lista de caretas Asignadas a pacientes
        FacesMessage msg=null;       
        try {
            logger.log(Level.INFO,"Entra a cargar dispositivos.");
            caretashospital = caretahospitalSB.getCaretasAsignadas();            
            caretashospitalNA = caretahospitalSB.getCaretasNoAsignadas();
        } catch (CaretaHospitalException ex) {
            logger.log(Level.SEVERE, "Error en MB al cargar caretashopital : {0}", ex.getMessage());
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
        
        
        //Obtener fecha:
        Calendar fecha = new GregorianCalendar();
                                                     
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        
        
          
        //Guradar Careta en BD
        try{            
            logger.log(Level.INFO,"Guardando careta...");
            caretaGuard = caretaSB.guardaCareta(caretaGuard);
            logger.log(Level.INFO,"Asignando hospital a careta...");
            caretaHospital.setEntCareta(caretaGuard);            
            entHospital = hospitalSB.getHospital(idHosp);            
            caretaHospital.setEntHospital(entHospital);
            caretaHospital.setFechaAsignacion(anio+"-"+mes+"-"+dia);
            
            EntCaretaHospitalPK ech = new EntCaretaHospitalPK();
            ech.setIdCareta(caretaGuard.getIdCareta());
            ech.setIdHospital(entHospital.getIdHospital());
            
            caretaHospital.setEntCaretaHospitalPK(ech);
            
            logger.log(Level.INFO,"Fecha de asignacion: {0}", caretaHospital.getFechaAsignacion());
            logger.log(Level.INFO,"Guardando relacion careta hospital...{0}", caretaHospital.getFechaAsignacion());
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
        } catch (NoExisteHospitalException ex) {
            FacesMessage msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "Error al intentar recuperar datos hospital :"+ex.getMessage(), 
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
        options.put("width", 650);
        options.put("height", 580);
        options.put("dynamic", true);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        PrimeFaces.current().dialog().openDynamic("dispositivos/dialRegistrarDispositivo", options, null);
    }
    
    public void retornoRegistrarDispositivo(){
        cargarDispositivos();
    }
    
    
    
    public void editarDispositivo() {
    
        idCareta = caretaHospitalEditar.getEntCareta().getIdCareta();
        noSerie = caretaHospitalEditar.getEntCareta().getNoSerie();
        fechaManufactura = caretaHospitalEditar.getEntCareta().getFechaManufactura().toString();
              
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 650);
        options.put("height", 580);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        
        //Envio de Parametros
        Map<String, List<String>> parametros = new HashMap<>();
        
        List<String> idcareta = new ArrayList<>();
        idcareta.add(idCareta+"");
        
        List<String> nSerie = new ArrayList<>();
        nSerie.add(noSerie+"");
        
        List<String> fManufactura = new ArrayList<>();
        fManufactura.add(fechaManufactura);
        
        List<String> idHospit = new ArrayList<>();
        idHospit.add(caretaHospitalEditar.getEntHospital().getIdHospital().toString());
        
        parametros.put("idCareta", idcareta);
        parametros.put("noSerie", nSerie);
        parametros.put("fecManufac", fManufactura);    
        parametros.put("idHosp", idHospit);
        
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
    
    public void actualizarDispositivo() {
        FacesMessage msg = null;
        try {

            //Actualizar datos de careta
            caretaEditar.setIdCareta(idCareta);
            caretaEditar.setNoSerie(noSerie);
            caretaEditar.setFechaManufactura(fechaManufactura);

            caretaSB.updateCareta(caretaEditar);

            //Actualizacion de relacion careta hospital
//            caretaHospitalEditar.setEntCareta(caretaEditar);
//            entHospital = hospitalSB.getHospital(idHosp);
//            caretaHospitalEditar.setEntHospital(entHospital);

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

        idCareta = caretaHospitalEliminar.getEntCareta().getIdCareta();
        noSerie = caretaHospitalEliminar.getEntCareta().getNoSerie();
        logger.log(Level.INFO, "ID Dispositivo Seleccionado: {0}", idCareta);
        
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
        idcareta.add(idCareta+"");
        
        List<String> nSerie = new ArrayList<>();
        nSerie.add(noSerie+"");
        
        List<String> fManufactura = new ArrayList<>();
        fManufactura.add(caretaHospitalEliminar.getEntCareta().getFechaManufactura().toString());
                
        parametros.put("idCareta", idcareta);
        parametros.put("noSerie", nSerie);    
        parametros.put("fecManufac", fManufactura);        
        
        PrimeFaces.current().dialog().openDynamic("dispositivos/dialConfirmacion", options, parametros);
    }
    
    public void eliminarDispositivo(){
        FacesMessage msg = null;
        
        caretaEliminar.setIdCareta(idCareta);
        caretaEliminar.setNoSerie(noSerie);
        caretaEliminar.setFechaManufactura(fechaManufactura);
        
        try {
            caretaHospitalEliminar = caretahospitalSB.getCaretaHospital(caretaEliminar);
        } catch (CaretaHospitalException ex) {
            Logger.getLogger(GestionDispositivosMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        logger.log(Level.INFO, "Dispositivo Seleccionado: ID[{0}]",
                caretaEliminar.getIdCareta());
        logger.log(Level.INFO, "Relacion Seleccionada: FA[{0}]",
                caretaHospitalEliminar.getFechaAsignacion());
        
        try {
            caretahospitalSB.borrarCaretaHospital(caretaHospitalEliminar);
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

    public List<EntHospital> getListHospital() {
        return listHospital;
    }

    public void setListHospital(List<EntHospital> listHospital) {
        this.listHospital = listHospital;
    }

    public Integer getIdHosp() {
        return idHosp;
    }

    public void setIdHosp(Integer idHosp) {
        this.idHosp = idHosp;
    }

    public EntCaretaHospital getCaretaHospital() {
        return caretaHospital;
    }

    public void setCaretaHospital(EntCaretaHospital caretaHospital) {
        this.caretaHospital = caretaHospital;
    }

    public List<EntCaretaHospital> getCaretashospital() {
        return caretashospital;
    }

    public void setCaretashospital(List<EntCaretaHospital> caretashospital) {
        this.caretashospital = caretashospital;
    }

    public EntCaretaHospital getCaretaHospitalEditar() {
        return caretaHospitalEditar;
    }

    public void setCaretaHospitalEditar(EntCaretaHospital caretaHospitalEditar) {
        this.caretaHospitalEditar = caretaHospitalEditar;
    }

    public List<EntCaretaHospital> getCaretashospitalNA() {
        return caretashospitalNA;
    }

    public void setCaretashospitalNA(List<EntCaretaHospital> caretashospitalNA) {
        this.caretashospitalNA = caretashospitalNA;
    }

    public EntCaretaHospital getCaretaHospitalEliminar() {
        return caretaHospitalEliminar;
    }

    public void setCaretaHospitalEliminar(EntCaretaHospital caretaHospitalEliminar) {
        this.caretaHospitalEliminar = caretaHospitalEliminar;
    }
    
    

}


