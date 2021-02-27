/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.pacient;

import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.exception.NoExisteHospitalException;
import ipn.cic.sistmr.exception.NoExistePacienteException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.modelo.EntAntecedentes;
import ipn.cic.sistmr.modelo.EntCareta;
import ipn.cic.sistmr.modelo.EntHospital;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntMedidas;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.HospitalSBLocal;
import ipn.cic.sistmr.sesion.MedicoSBLocal;
import ipn.cic.sistmr.sesion.PacienteSBLocal;
import ipn.cic.web.sistmr.util.Mensaje;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
@Named(value = "inicioPacienteMB")
@ViewScoped
public class InicioPacienteMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(InicioPacienteMB.class.getName());

    @EJB
    private PacienteSBLocal pacienteSB;
    @EJB
    private MedicoSBLocal medicoSB;
    @EJB
    private UtilWebSBLocal utilWebSB;
    @EJB
    private HospitalSBLocal hospitalSB;

    private EntPaciente paciente;

    private List<EntMedidas> medidas;
    private EntMedico medicoPac;
    private EntHospital hospital;
    private EntAntecedentes antecedentes;
    private EntCareta careta;
    private Boolean masculino = true;

    @PostConstruct
    public void cargaPaciente() {
        FacesMessage msg = null;

        //Recuperar Entidad de Paciente        
        try {
            logger.log(Level.INFO, "Entra a cargar usuario.");
            EntUsuario usrPaciente = utilWebSB.getUsrAutenticado();
            logger.log(Level.INFO, "Usuario encontrado: {0}", usrPaciente.getIdPersona());

            paciente = pacienteSB.getPaciente(usrPaciente.getIdPersona());
            logger.log(Level.INFO, "Paciente recuperado: {0}", paciente.getIdPaciente());

//            if(usrPaciente.getIdPersona().getIdGenero().getIdGenero()==1)
//                masculino=false;
            antecedentes = paciente.getEntAntecedentes();
            logger.log(Level.INFO, "Recuperando Antecedentes: {0}", paciente.getEntAntecedentes().getIdPaciente());

            careta = pacienteSB.getCaretaDePaciente(paciente);
            logger.log(Level.INFO, "Recuperando careta: {0}", careta.getIdCareta());

            //Recuperar medico del paciente
            medicoPac = medicoSB.getMedicoDePaciente(paciente);
            logger.log(Level.INFO, "Medico recuperado en Inicio: {0}", medicoPac.getCedulaProf());

            hospital = hospitalSB.getPrimerHospital();

        } catch (NoExistePacienteException ex) {
            logger.log(Level.SEVERE, "Error al cargar paciente.");
        } catch (MedicoException ex) {
            logger.log(Level.SEVERE, "Error al cargar medico del paciente.");
        } catch (NoExisteHospitalException ex) {
            logger.log(Level.SEVERE, "Error al cargar hospital.");
        }

        if (msg == null) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Éxito:",
                            "Pacientes cargados correctamente",
                            FacesMessage.SEVERITY_INFO);
        }
//        utilWebSB.addMsg("frGestPacientes:msgsIP", msg);
//        PrimeFaces.current().ajax().update("frIPacientes:msgsIP");
    }

    public void mostrarDashboard() {
        logger.log(Level.INFO, "Paciente: Abre dashboard.");
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
        valNombre.add(paciente.getIdPersona().getNombre());

        List<String> valPrimerAp = new ArrayList<>();
        valPrimerAp.add(paciente.getIdPersona().getPrimerApellido());

        List<String> valSegundoAp = new ArrayList<>();
        valSegundoAp.add(paciente.getIdPersona().getSegundoApellido());

        List<String> valId = new ArrayList<>();
        valId.add(paciente.getIdPaciente().toString());

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

        PrimeFaces.current().dialog().openDynamic("dialDashboardPaciente", options, parametros);
    }

    public void retornoMostrarDashboard(SelectEvent event) {

    }

    public void guardarCambiosPaciente() {
        FacesMessage msg = null;

        logger.log(Level.INFO, "Guardando cambios paciente...");
        
        try {
            pacienteSB.updatePaciente(paciente);
            
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Exito",
                            "Datos Actualizados",
                            FacesMessage.SEVERITY_INFO);
        } catch (UpdateEntityException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error.",
                            "Intentelo mas tarde.",
                            FacesMessage.SEVERITY_INFO);
        }         

        utilWebSB.addMsg("frIDatos:msgsID", msg);
    }

    public void mostrarDatosMedico() {
        logger.log(Level.INFO, "Abre datos de medico.");

        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 440);
        options.put("height", 550);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        //Envio de Parametros del Medico
        Map<String, List<String>> parametros = new HashMap<>();

        List<String> valNombre = new ArrayList<>();
        valNombre.add(medicoPac.getIdPersona().getNombre());

        List<String> valPrimerAp = new ArrayList<>();
        valPrimerAp.add(medicoPac.getIdPersona().getPrimerApellido());

        List<String> valSegundoAp = new ArrayList<>();
        valSegundoAp.add(medicoPac.getIdPersona().getSegundoApellido());

        List<String> valCorreo = new ArrayList<>();
        valCorreo.add(medicoPac.getEmail());

        List<String> valTel = new ArrayList<>();
        valTel.add(medicoPac.getCelular());

        List<String> valCedula = new ArrayList<>();
        valTel.add(medicoPac.getCedulaProf());

        parametros.put("medNombre", valNombre);
        parametros.put("medPrimerAp", valPrimerAp);
        parametros.put("medSegundoAp", valSegundoAp);
        parametros.put("medEmail", valCorreo);
        parametros.put("medTel", valTel);
        parametros.put("medCedula", valCedula);

        PrimeFaces.current().dialog().openDynamic("dialDatosMedicoPaciente", options, parametros);
    }

    public void retornoMostrarDatosMedico() {
        FacesMessage msg = null;

    }

    public void cerrarDialogo() {
        logger.log(Level.INFO, "Invocando cerrar dialogo.");
        FacesMessage mensaje = Mensaje.getInstance()
                .getMensaje("CERRANDO_DIALOGO", "CERRANDO_CORRECTAMENTE",
                        FacesMessage.SEVERITY_INFO);
        cerrarDialogo(mensaje);
    }

    private void cerrarDialogo(FacesMessage mensaje) {
        PrimeFaces.current().dialog().closeDynamic(mensaje);
    }

    public EntPaciente getPaciente() {
        return paciente;
    }

    public void setPaciente(EntPaciente paciente) {
        this.paciente = paciente;
    }

    public EntMedico getMedicoPac() {
        return medicoPac;
    }

    public void setMedicoPac(EntMedico medicoPac) {
        this.medicoPac = medicoPac;
    }

    public EntHospital getHospital() {
        return hospital;
    }

    public void setHospital(EntHospital hospital) {
        this.hospital = hospital;
    }

    public EntAntecedentes getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(EntAntecedentes antecedentes) {
        this.antecedentes = antecedentes;
    }

    public EntCareta getCareta() {
        return careta;
    }

    public void setCareta(EntCareta careta) {
        this.careta = careta;
    }

    public Boolean getMasculino() {
        return masculino;
    }

    public void setMasculino(Boolean masculino) {
        this.masculino = masculino;
    }

}

