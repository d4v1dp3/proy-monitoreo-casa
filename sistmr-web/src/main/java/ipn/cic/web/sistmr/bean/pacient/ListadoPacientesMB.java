/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.pacient;

import ipn.cic.sistmr.exception.BitacoraException;
import ipn.cic.sistmr.exception.CatalogoException;
import ipn.cic.sistmr.exception.EstadoPacienteException;
import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.exception.NoExistePacienteException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.modelo.EntBitacora;
import ipn.cic.sistmr.modelo.EntEstadopaciente;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.BitacoraSBLocal;
import ipn.cic.sistmr.sesion.CatalogoSBLocal;
import ipn.cic.sistmr.sesion.EstadoPacienteSBLocal;
import ipn.cic.sistmr.sesion.MedicoSBLocal;
import ipn.cic.sistmr.sesion.PacienteSBLocal;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import ipn.cic.web.sistmr.bean.vo.PacienteVO;
import ipn.cic.web.sistmr.bean.vo.PersonaVO;
import ipn.cic.web.sistmr.util.Mensaje;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

@Named(value = "listadoPacientesMB")
@ViewScoped
public class ListadoPacientesMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ListadoPacientesMB.class.getName());

    @EJB
    private PacienteSBLocal pacienteSB;
    @EJB
    private EstadoPacienteSBLocal estadopacienteSB;
    @EJB
    private MedicoSBLocal medicoSB;
    @EJB
    private UtilWebSBLocal utilWebSB;
    @EJB
    private CatalogoSBLocal catalogoSB;
    @EJB
    private BitacoraSBLocal bitacoraSB;
    @EJB
    private UsuarioSBLocal usuarioSB;

    private EntMedico medico;
    private List<EntPaciente> pacientesComp;
    
    private EntPaciente pacienteMostrar;
    private EntPaciente pacienteEditar;
    private List<EntEstadopaciente> catEstado;

    private PersonaVO persona = new PersonaVO();
    private PacienteVO paciente = new PacienteVO();

    private List<EntPaciente> pacientesMonitoreados;
    private List<EntPaciente> altasPacientes;
    private List<EntPaciente> decesosPacientes;

    @PostConstruct
    public void cargaPacientes() {
        FacesMessage msg = null;

        altasPacientes = new ArrayList();
        decesosPacientes = new ArrayList();
        pacientesMonitoreados = new ArrayList();

        logger.log(Level.INFO, "Entra a cargar lista de pacientes de medico.");

        try {
            //Recuperar Entidad de Medico             
            EntUsuario usrMedico = utilWebSB.getUsrAutenticado();
            logger.log(Level.INFO, "\tUsuario encontrado: {0}", usrMedico.getIdPersona());
            medico = medicoSB.getMedico(usrMedico.getIdPersona());
        } catch (MedicoException ex) {
            logger.log(Level.SEVERE, "\tError al recuperar medico.");
        }

        try {
            //Recuperar y catalogar Pacientes del Medico
            pacientesComp = medicoSB.getListaPaciente(medico);

            for (int i=0; i< pacientesComp.size();i++) {
                EntPaciente pac = pacientesComp.get(i);
                
                if (pac.getIdEstadopaciente().getIdEstadopaciente() == 4) 
                    altasPacientes.add(pac);
                else if (pac.getIdEstadopaciente().getIdEstadopaciente() == 3) 
                    decesosPacientes.add(pac);
                else
                    pacientesMonitoreados.add(pac);
            }
           
            
        } catch (MedicoException ex) {
            pacientesComp = new ArrayList();
            logger.log(Level.SEVERE, "\tError al recuperar pacientes de medico.");
        }

        try {
            setCatEstado((List<EntEstadopaciente>) catalogoSB.getCatalogo("EntEstadopaciente"));
        } catch (CatalogoException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "No es posible recuperar catálogo de estadoPaciente :" + ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR);
        }

//        if(msg==null){
//            msg = Mensaje.getInstance()
//                    .getMensajeAdaptado("Éxito:",
//                            "Pacientes cargados correctamente",
//                            FacesMessage.SEVERITY_INFO);
//        }
//        utilWebSB.addMsg("frGestPacientes:msgsGP", msg);
//        PrimeFaces.current().ajax().update("frGestPacientes:msgsGP");

        logger.log(Level.INFO, "\tDatos recuperados correctamente.");
    }

    public void mostrarDashboard() {
        logger.log(Level.INFO, "Abre dashboard de un paciente.");
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 890);
        options.put("height", 640);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        //Envio de Parametros
        Map<String, List<String>> parametros = new HashMap<>();

        List<String> valNombre = new ArrayList<>();
        valNombre.add(pacienteMostrar.getIdPersona().getNombre());

        List<String> valPrimerAp = new ArrayList<>();
        valPrimerAp.add(pacienteMostrar.getIdPersona().getPrimerApellido());

        List<String> valSegundoAp = new ArrayList<>();
        valSegundoAp.add(pacienteMostrar.getIdPersona().getSegundoApellido());

        List<String> valId = new ArrayList<>();
        valId.add(pacienteMostrar.getIdPaciente().toString());

        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<String> valFechaHist = new ArrayList<>();
        LocalDate fechaActual = LocalDate.now();
        valFechaHist.add(fechaActual.format(formatoFecha));

        logger.log(Level.INFO, "PrimerAp: {0}", valPrimerAp.get(0));
        logger.log(Level.INFO, "SegundoAp: {0}", valSegundoAp.get(0));
        logger.log(Level.INFO, "Fecha Actual: {0}", valFechaHist.get(0));

        parametros.put("pacNombre", valNombre);
        parametros.put("pacPrimerAp", valPrimerAp);
        parametros.put("pacSegundoAp", valSegundoAp);
        parametros.put("pacId", valId);
        parametros.put("pacfechaHist", valFechaHist);

        PrimeFaces.current().dialog().openDynamic("pacientes/dialDashboardPaciente", options, parametros);
    }

    public void retornoMuestraDashboard(SelectEvent event) {
        FacesMessage msg = null;
        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo ",
                            "Diálogo cerrado sin aplicar cambios.",
                            FacesMessage.SEVERITY_INFO);
        }
        utilWebSB.addMsg("frGestPacientes:msgsGP", msg);
    }

    public void mostrarAntecedentes() {
        logger.log(Level.INFO, "Abre antecedentes del paciente.");
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 540);
        options.put("height", 500);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        //Envio de Parametros
        Map<String, List<String>> parametros = new HashMap<>();

        List<String> valNombre = new ArrayList<>();
        valNombre.add(pacienteMostrar.getIdPersona().getNombre());

        List<String> valPrimerAp = new ArrayList<>();
        valPrimerAp.add(pacienteMostrar.getIdPersona().getPrimerApellido());

        List<String> valSegundoAp = new ArrayList<>();
        valSegundoAp.add(pacienteMostrar.getIdPersona().getSegundoApellido());

        List<String> curp = new ArrayList<>();
        curp.add(pacienteMostrar.getIdPersona().getCurp());

        List<String> edad = new ArrayList<>();
        edad.add(pacienteMostrar.getIdPersona().getEdad() + "");

        List<String> diabetes = new ArrayList<>();
        if (pacienteMostrar.getEntAntecedentes().getDiabetes()) {
            diabetes.add("true");
        } else {
            diabetes.add("false");
        }

        List<String> cancer = new ArrayList<>();
        if (pacienteMostrar.getEntAntecedentes().getCancer()) {
            cancer.add("true");
        } else {
            cancer.add("false");
        }

        List<String> asma = new ArrayList<>();
        if (pacienteMostrar.getEntAntecedentes().getAsma()) {
            asma.add("true");
        } else {
            asma.add("false");
        }

        List<String> vih = new ArrayList<>();
        if (pacienteMostrar.getEntAntecedentes().getVih()) {
            vih.add("true");
        } else {
            vih.add("false");
        }

        List<String> has = new ArrayList<>();
        if (pacienteMostrar.getEntAntecedentes().getHas()) {
            has.add("true");
        } else {
            has.add("false");
        }

        List<String> epoc = new ArrayList<>();
        if (pacienteMostrar.getEntAntecedentes().getEpoc()) {
            epoc.add("true");
        } else {
            epoc.add("false");
        }

        List<String> embarazo = new ArrayList<>();
        if (pacienteMostrar.getEntAntecedentes().getEmbarazo()) {
            embarazo.add("true");
        } else {
            embarazo.add("false");
        }

        List<String> artritis = new ArrayList<>();
        if (pacienteMostrar.getEntAntecedentes().getArtritis()) {
            artritis.add("true");
        } else {
            artritis.add("false");
        }

        List<String> enfau = new ArrayList<>();
        if (pacienteMostrar.getEntAntecedentes().getEnfautoinmune()) {
            enfau.add("true");
        } else {
            enfau.add("false");
        }

        logger.log(Level.INFO, "PrimerAp: {0}", valPrimerAp.get(0));
        logger.log(Level.INFO, "SegundoAp: {0}", valSegundoAp.get(0));

        parametros.put("pacNombre", valNombre);
        parametros.put("pacPrimerAp", valPrimerAp);
        parametros.put("pacSegundoAp", valSegundoAp);
        parametros.put("diab", diabetes);
        parametros.put("canc", cancer);
        parametros.put("asma", asma);
        parametros.put("vih", vih);
        parametros.put("has", has);
        parametros.put("epoc", epoc);
        parametros.put("emba", embarazo);
        parametros.put("artr", artritis);
        parametros.put("enfa", enfau);
        parametros.put("curp", curp);
        parametros.put("edad", edad);

        PrimeFaces.current().dialog().openDynamic("pacientes/dialAntecedentesPaciente", options, parametros);
    }

    public void retornoMostrarAntecedentes(SelectEvent event) {
        FacesMessage msg = null;
        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo ",
                            "Diálogo cerrado sin aplicar cambios.",
                            FacesMessage.SEVERITY_INFO);
        }
        utilWebSB.addMsg("frGestPacientes:msgsGP", msg);
    }

    public void editarPaciente() {
        logger.log(Level.INFO, "Abre dialogo edita paciente.");
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 400);
        options.put("height", 450);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        //Envio de Parametros
        Map<String, List<String>> parametros = new HashMap<>();

        List<String> idpac = new ArrayList<>();
        idpac.add(pacienteEditar.getIdPaciente() + "");

        List<String> valNombre = new ArrayList<>();
        valNombre.add(pacienteEditar.getIdPersona().getNombre());

        List<String> valPrimerAp = new ArrayList<>();
        valPrimerAp.add(pacienteEditar.getIdPersona().getPrimerApellido());

        List<String> valSegundoAp = new ArrayList<>();
        valSegundoAp.add(pacienteEditar.getIdPersona().getSegundoApellido());

        List<String> curp = new ArrayList<>();
        curp.add(pacienteEditar.getIdPersona().getCurp());

        List<String> edad = new ArrayList<>();
        edad.add(pacienteEditar.getIdPersona().getEdad() + "");

        parametros.put("idpac", idpac);
        parametros.put("nombre", valNombre);
        parametros.put("primerapellido", valPrimerAp);
        parametros.put("segundoapellido", valSegundoAp);
        parametros.put("curp", curp);
        parametros.put("edad", edad);

        PrimeFaces.current().dialog().openDynamic("pacientes/dialEditaPaciente", options, parametros);
    }

    public void retornoEditarPaciente(SelectEvent event) {
        FacesMessage msg = null;
        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo ",
                            "Diálogo cerrado sin aplicar cambios.",
                            FacesMessage.SEVERITY_INFO);
        }
        utilWebSB.addMsg("frGestPacientes:msgsGP", msg);
        cargaPacientes();
    }

    public void guardarCambiosPaciente() {

        logger.log(Level.INFO, "Entrando a guardar cambios en paciente.");
        FacesMessage msg = null;

        try {

            logger.log(Level.INFO, "Buscando paciente id={0}.", paciente.getIdPaciente());
            pacienteEditar = pacienteSB.getPaciente(paciente.getIdPaciente());
            logger.log(Level.INFO, "Entidad Paciente recuperado {0}.", pacienteEditar.getIdPaciente());

            logger.log(Level.INFO, "Estado a actualizar={0}.", paciente.getIdEstadopaciente());
            EntEstadopaciente estadopac = estadopacienteSB.getEstadoPaciente(paciente.getIdEstadopaciente());

            pacienteEditar.setIdEstadopaciente(estadopac);

            pacienteEditar = pacienteSB.updatePaciente(pacienteEditar);

            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Estado Actualizado",
                            "El estado del paciente se ha actualizado correctamente. ",
                            FacesMessage.SEVERITY_INFO);
            
             //Registrar operacion en bitacora
            Date fechaEntrada = new Date();//Fecha de hoy
            EntUsuario usrMed = utilWebSB.getUsrAutenticado();
            EntUsuario paciente = usuarioSB.getUsuarioDePaciente(pacienteEditar);
            if(estadopac.getIdEstadopaciente()==4){
                EntBitacora evento = bitacoraSB.eventoAltaDePaciente(fechaEntrada, usrMed, paciente);
            }
            if(estadopac.getIdEstadopaciente()==3){
                EntBitacora evento = bitacoraSB.eventoDecesoDePaciente(fechaEntrada, usrMed, paciente);
            }
            

        } catch (NoExistePacienteException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Imposible recuperar datos del paciente. ",
                            FacesMessage.SEVERITY_ERROR);
        } catch (UpdateEntityException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Imposible actualizar datos del paciente. ",
                            FacesMessage.SEVERITY_ERROR);
        } catch (EstadoPacienteException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Imposible recuperar estado de paciente. ",
                            FacesMessage.SEVERITY_ERROR);
        } catch (BitacoraException ex) {
            logger.log(Level.INFO, "ERROR: No se registro evento en bitacora.");
        }

        utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);

        cerrarDialogo(msg);
    }

    public void cerrarDialogo() {
        FacesMessage mensaje = Mensaje.getInstance()
                .getMensaje("CERRANDO_DIALOGO", "CERRANDO_CORRECTAMENTE",
                        FacesMessage.SEVERITY_INFO);
        PrimeFaces.current().dialog().closeDynamic(mensaje);
    }

    public void cerrarDialogo(FacesMessage mensaje) {
        PrimeFaces.current().dialog().closeDynamic(mensaje);
    }

    /**
     * @return the pacientesComp
     */
    public List<EntPaciente> getPacientesComp() {
        return pacientesComp;
    }

    /**
     * @param usuariosComp the pacientesComp to set
     */
    public void setPacientesComp(List<EntPaciente> usuariosComp) {
        this.pacientesComp = usuariosComp;
    }

    /**
     * @return the pacienteMostrar
     */
    public EntPaciente getPacienteMostrar() {
        return pacienteMostrar;
    }

    /**
     * @param pacienteMostrar the pacienteMostrar to set
     */
    public void setPacienteMostrar(EntPaciente pacienteMostrar) {
        this.pacienteMostrar = pacienteMostrar;
    }

    public EntPaciente getPacienteEditar() {
        return pacienteEditar;
    }

    public void setPacienteEditar(EntPaciente pacienteEditar) {
        this.pacienteEditar = pacienteEditar;
    }

    public PersonaVO getPersona() {
        return persona;
    }

    public void setPersona(PersonaVO persona) {
        this.persona = persona;
    }

    public PacienteVO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteVO paciente) {
        this.paciente = paciente;
    }

    public List<EntEstadopaciente> getCatEstado() {
        return catEstado;
    }

    public void setCatEstado(List<EntEstadopaciente> catEstado) {
        this.catEstado = catEstado;
    }

    public List<EntPaciente> getAltasPacientes() {
        return altasPacientes;
    }

    public void setAltasPacientes(List<EntPaciente> AltasPacientes) {
        this.altasPacientes = AltasPacientes;
    }

    public List<EntPaciente> getDecesosPacientes() {
        return decesosPacientes;
    }

    public void setDecesosPacientes(List<EntPaciente> DecesosPacientes) {
        this.decesosPacientes = DecesosPacientes;
    }

    public List<EntPaciente> getPacientesMonitoreados() {
        return pacientesMonitoreados;
    }

    public void setPacientesMonitoreados(List<EntPaciente> pacientesMonitoreados) {
        this.pacientesMonitoreados = pacientesMonitoreados;
    }
}