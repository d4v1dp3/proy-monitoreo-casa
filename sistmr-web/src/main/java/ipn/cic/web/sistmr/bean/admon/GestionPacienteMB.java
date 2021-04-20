/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.CaretaHospitalException;
import ipn.cic.sistmr.exception.CatalogoException;
import ipn.cic.sistmr.exception.IDUsuarioException;
import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.exception.PacienteException;
import ipn.cic.sistmr.modelo.EntCaretaHospital;
import ipn.cic.sistmr.modelo.EntEstadopaciente;
import ipn.cic.sistmr.modelo.EntGenero;
import ipn.cic.sistmr.modelo.EntHospital;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPersona;
import ipn.cic.sistmr.sesion.CaretaHospitalSBLocal;
import ipn.cic.sistmr.sesion.CatalogoSBLocal;
import ipn.cic.sistmr.sesion.MedicoSBLocal;
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
@Named(value = "gestionPacienteMB")
@ViewScoped
public class GestionPacienteMB implements Serializable {

    private static final Logger logger = Logger.getLogger(GestionPacienteMB.class.getName());

    private PersonaVO datPersona;
    private UsuarioVO datUsuario;
    private PacienteVO datPaciente;

    private EntPaciente pacGuardado;
    private List<EntGenero> catGenero;
    private List<EntEstadopaciente> catEstado;
    private List<String> antecedentes;
    private String[] antecedentesSeleccionados;
    private List<EntHospital> listaHospital;
    private List<EntMedico> listaMedicos;
    private List<EntCaretaHospital> listCaretaHospital;
    private List<EntPersona> personaMedico;
    
    @EJB
    GestionPacienteBDLocal gstPac;
    @EJB
    UtilWebSBLocal utilWebSB;
    @EJB
    CatalogoSBLocal catalogoSB;
    @EJB
    MedicoSBLocal medicoSB;
    @EJB
    CaretaHospitalSBLocal caretahospitalSB;

    public void iniciaVO() {
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
        
        listaHospital = new ArrayList();
        personaMedico = new ArrayList();
        
        try {
            //Cargar Lista de Hospitales
            listaHospital = catalogoSB.getCatalogo("EntHospital");
            
            if(listaHospital.isEmpty()){
                FacesMessage msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Hospital",
                            "Se requiere información del Hospital para realizar el registro.",
                            FacesMessage.SEVERITY_ERROR);
                utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
            }
        } catch (CatalogoException ex) {
            logger.log(Level.SEVERE, "Imposible recuperar datos de Hospital :{0} ",ex.getMessage());
            FacesMessage msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "No es posible recuperar datos de Hospital:" + ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
        }
        

        try {
            //Cargar Lista de Dispositivos no asignados
            listCaretaHospital = caretahospitalSB.getCaretasNoAsignadas();
            
            if(listCaretaHospital.isEmpty()){
                FacesMessage msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Dispositivo",
                            "Se requiere de un dispositivo disponible para realizar el registro.",
                            FacesMessage.SEVERITY_ERROR);
                utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
            }
        } catch (CaretaHospitalException ex) {
            listCaretaHospital = new ArrayList();
            logger.log(Level.SEVERE, "Imposible recuperar datos de Dispositivos :{0} ",ex.getMessage());
            FacesMessage msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "No es posible recuperar lista de Dispositivos:" + ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
        }

        try {
            setCatGenero((List<EntGenero>) catalogoSB.getCatalogo("EntGenero"));
        } catch (CatalogoException ex) {
            logger.log(Level.SEVERE, "Imposible recuperar datos de Genero :{0} ",ex.getMessage());
            FacesMessage msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "No es posible recuperar catálogo de género:" + ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
        }
        
               
        try {
            //Cargar Lista de Medicos
            setListaMedicos((List<EntMedico>) medicoSB.getMedicos());

            if(listaMedicos.isEmpty()){
                FacesMessage msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Médico",
                            "Se requiere información de Médico para realizar el registro.",
                            FacesMessage.SEVERITY_ERROR);
                utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
            }
        } catch (MedicoException ex) {
            listaMedicos = new ArrayList(); 
            logger.log(Level.SEVERE, "Entidad de medico no encontrado:{0} ",ex.getMessage());
        }

        try {
            setCatEstado((List<EntEstadopaciente>) catalogoSB.getCatalogo("EntEstadopaciente"));
        } catch (CatalogoException ex) {
            FacesMessage msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "No es posible recuperar catálogo de estadoPaciente :" + ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
        }
        logger.log(Level.INFO, "Formulario desplegado correctamente.");
    }

    public void guardarPaciente() {

        List<String> aList = Arrays.asList(antecedentesSeleccionados);

        AntecedentesVO datAntecedentes = new AntecedentesVO(aList.contains("Diabetes"), aList.contains("Cancer"),
                aList.contains("Asma"), aList.contains("VIH"), aList.contains("HAS"), aList.contains("EPOC"), aList.contains("Embarazo"),
                aList.contains("Artritis"), aList.contains("Enf autoinmune"));

        try {
            pacGuardado = gstPac.guardarPacienteNuevo(datPaciente, datPersona, datAntecedentes, getDatUsuario());
        } catch (PacienteException ex) {
            FacesMessage msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Error al intentar guardar médico :" + ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
            return;
        } catch (IDUsuarioException ex) {
            FacesMessage msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "El ID de Paciente ya existe. CAMBIARLO",
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
            return;
        }
        FacesMessage msg = null;
        if (pacGuardado == null) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Imposible guardar datos de Paciente, intentelo más tarde.",
                            FacesMessage.SEVERITY_ERROR);
            cerrarDialogo(msg);
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Paciente Registrado",
                            "El registro de paciente se realizó correctamente: Id=" + this.pacGuardado.getIdPaciente(),
                            FacesMessage.SEVERITY_INFO);
        }

        cerrarDialogo(msg);
    }

    public void cerrarDialogo() {
//        FacesMessage mensaje = Mensaje.getInstance()
//                .getMensaje("CERRANDO_DIALOGO", "CERRANDO_CORRECTAMENTE",
//                        FacesMessage.SEVERITY_INFO);
        cerrarDialogo(null);
    }

    public void cerrarDialogo(FacesMessage mensaje) {
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

    public List<EntHospital> getListHospital() {
        return listaHospital;
    }

    public void setListHospital(List<EntHospital> listHospital) {
        this.listaHospital = listHospital;
    }

    public List<EntCaretaHospital> getListCaretaHospital() {
        return listCaretaHospital;
    }

    public void setListCaretaHospital(List<EntCaretaHospital> listCaretaHospital) {
        this.listCaretaHospital = listCaretaHospital;
    }

    public List<EntHospital> getListaHospital() {
        return listaHospital;
    }

    public void setListaHospital(List<EntHospital> listaHospital) {
        this.listaHospital = listaHospital;
    }

    public List<EntMedico> getListaMedicos() {
        return listaMedicos;
    }

    public void setListaMedicos(List<EntMedico> listaMedicos) {
        this.listaMedicos = listaMedicos;
    }

    public List<EntPersona> getPersonaMedico() {
        return personaMedico;
    }

    public void setPersonaMedico(List<EntPersona> personaMedico) {
        this.personaMedico = personaMedico;
    }
   
}
