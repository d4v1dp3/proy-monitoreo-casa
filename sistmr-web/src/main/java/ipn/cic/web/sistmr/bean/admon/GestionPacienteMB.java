/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.CatalogoException;
import ipn.cic.sistmr.exception.PacienteException;
import ipn.cic.sistmr.modelo.EntEstadopaciente;
import ipn.cic.sistmr.modelo.EntGenero;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.sesion.CatalogoSBLocal;
import ipn.cic.web.sistmr.bean.vo.AntecedentesVO;
import ipn.cic.web.sistmr.bean.vo.PacienteVO;
import ipn.cic.web.sistmr.bean.vo.PersonaVO;
import ipn.cic.web.sistmr.bean.vo.UsuarioVO;
import ipn.cic.web.sistmr.delegate.GestionPacienteBDLocal;
import ipn.cic.web.sistmr.util.Mensaje;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
 * @author Iliac Huerta Trujillo
 * 
 */
@Named(value ="gestionPacienteMB")
@ViewScoped
public class GestionPacienteMB implements Serializable{
    private static final Logger logger = Logger.getLogger(GestionPacienteMB.class.getName());
    
    private PersonaVO datPersona;
    private UsuarioVO datUsuario;
    private PacienteVO datPaciente;
    
    private EntPaciente pacGuardado;
    
    private List<EntGenero> catGenero;
    private List<EntEstadopaciente> catEstado;
    private List<String> antecedentes;
    private String[] antecedentesSeleccionados;
    
    @EJB
    GestionPacienteBDLocal gstPac;
    @EJB
    UtilWebSBLocal utilWebSB;
    @EJB
    CatalogoSBLocal catalogoSB;
    
    @PostConstruct
    public void iniciaVO(){
        setDatUsuario(new UsuarioVO());
        datPaciente = new PacienteVO();
        datPersona = new PersonaVO();
        pacGuardado = null;
       
        antecedentes = new ArrayList<>();
        antecedentes.add("Diabetes");
        antecedentes.add("Cancer");
        antecedentes.add("Asma");
        antecedentes.add("VIH");
        antecedentes.add("HAS");
        antecedentes.add("EPOC");
        antecedentes.add("Embarazo");
        antecedentes.add("Artritis");
        antecedentes.add("Enf autoinmune");
                 
        try {
            setCatGenero((List<EntGenero>) catalogoSB.getCatalogo("EntGenero"));
        } catch (CatalogoException ex) {
            FacesMessage msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "No es posible recuperar catálogo de género :"+ex.getMessage(), 
                                                FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPas", msg);
        }
        
        try {
            setCatEstado((List<EntEstadopaciente>) catalogoSB.getCatalogo("EntEstadopaciente"));
        } catch (CatalogoException ex) {
            FacesMessage msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "No es posible recuperar catálogo de estadoPaciente :"+ex.getMessage(), 
                                                FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPas", msg);
        }
        logger.log(Level.INFO,"Categorias en Form AltaPaciente[Fin]");
    }
    
    public void guardarPaciente(){
        
        List<String> aList = Arrays.asList(antecedentesSeleccionados);
        
        AntecedentesVO datAntecedentes = new AntecedentesVO(aList.contains("Diabetes"),aList.contains("Cancer"),
        aList.contains("Asma"),aList.contains("VIH"),aList.contains("HAS"),aList.contains("EPOC"),aList.contains("Embarazo"),
        aList.contains("Artritis"),aList.contains("Enf autoinmune"));
       
        try{              
            pacGuardado = gstPac.guardarPacienteNuevo(datPaciente, datPersona, datAntecedentes, getDatUsuario());
        } catch (PacienteException ex) {
            FacesMessage msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "Error al intentar guardar médico :"+ex.getMessage(), 
                                                FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPas", msg);
            return;
        }
        FacesMessage msg=null;
        if (pacGuardado == null){
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Error",
                                                "Imposible guardar datos de Paciente, intente más tarde", 
                                                FacesMessage.SEVERITY_ERROR);
            cerrarDialogo(msg);
        }else{
            msg = Mensaje.getInstance()
                                     .getMensajeAdaptado("Exíto",
                                                "El registro de paciente se realizó correctamente : id="+this.pacGuardado.getIdPaciente(), 
                                                FacesMessage.SEVERITY_INFO);        
        }
        
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
    
    
    public List<EntEstadopaciente> getCatEstado() {
        return catEstado;
    }

    public void setCatEstado(List<EntEstadopaciente> catEstado) {
        this.catEstado = catEstado;
    }
    
    public void setDatUsuario(UsuarioVO datUsuario) {
        this.datUsuario = datUsuario;
    }
    
    /**
     * @return the datUsuario
     */
    public UsuarioVO getDatUsuario() {
        return datUsuario;
    }

    public PersonaVO getDatPersona() {
        return datPersona;
    }

    public void setDatPersona(PersonaVO datPersona) {
        this.datPersona = datPersona;
    }

     public PacienteVO getDatPaciente() {
        return datPaciente;
    }

    public void setDatPaciente(PacienteVO datPaciente) {
        this.datPaciente = datPaciente;
    }

    public EntPaciente getPacGuardado() {
        return pacGuardado;
    }

    public void setPacGuardado(EntPaciente pacGuardado) {
        this.pacGuardado = pacGuardado;
    }

    public List<EntGenero> getCatGenero() {
        return catGenero;
    }

    public void setCatGenero(List<EntGenero> catGenero) {
        this.catGenero = catGenero;
    } 

    public List<String> getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(List<String> antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String[] getAntecedentesSeleccionados() {
        return antecedentesSeleccionados;
    }

    public void setAntecedentesSeleccionados(String[] antecedentesSeleccionados) {
        this.antecedentesSeleccionados = antecedentesSeleccionados;
    }


    
    
   
}
