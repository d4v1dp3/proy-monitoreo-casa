/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.sesion.MedicoSBLocal;
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

@Named(value = "listadoMedicosMB")
@ViewScoped
public class ListadoMedicosMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ListadoMedicosMB.class.getName());

    @EJB
    private MedicoSBLocal medicoSB;
    @EJB
    private UtilWebSBLocal utilWebSB;

    private List<EntMedico> listadoMedicos;
    private EntMedico medicoMostrar;

    @PostConstruct
    public void cargaMedicos() {
        FacesMessage msg=null;
        logger.log(Level.INFO,"Entra a cargar lista de medicos.");
        //Recuperar lista de Medicos       
        try {
            listadoMedicos = medicoSB.getMedicos();
//            for(int i=0; i<listadoMedicos.size(); i++){
//                logger.log(Level.INFO,"Num Pacientes: {0}.",listadoMedicos.get(i).getEntPacienteMedicoList());
//            }
            logger.log(Level.INFO,"Lista medicos recuperada.");
        } catch (MedicoException ex) {
            logger.log(Level.SEVERE,"Error al cargar listado de medicos.");
        }        
        
        if(msg==null){
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Éxito:",
                            "Listado de medicos cargado correctamente",
                            FacesMessage.SEVERITY_INFO);
        }
//        utilWebSB.addMsg("frGestPacientes:msgsGP", msg);
//        PrimeFaces.current().ajax().update("frGestPacientes:msgsGP");
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
        FacesMessage msg = null;

        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();
            utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
            cargaMedicos();
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo",
                            "Diálogo cerrado sin aplicar cambios",
                            FacesMessage.SEVERITY_INFO);
            utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
        }
    }
 

    /**
     * @return the listadoMedicos
     */
    public List<EntMedico> getListadoMedicos() {
        return listadoMedicos;
    }

    /**
     * @param listadoMedicos
     */
    public void setListadoMedicos(List<EntMedico> listadoMedicos) {
        this.listadoMedicos = listadoMedicos;
    }

    
    /**
     * @return the pacienteMostrar
     */
    public EntMedico getMedicoMostrar() {
        return medicoMostrar;
    }

    /**
     * @param medicoMostrar
     */
    public void setMedicoMostrar(EntMedico medicoMostrar) {
        this.medicoMostrar = medicoMostrar;
    }

}
