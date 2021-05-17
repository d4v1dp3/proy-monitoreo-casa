/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.CaretaHospitalException;
import ipn.cic.sistmr.exception.CatalogoException;
import ipn.cic.sistmr.exception.GeneroException;
import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.exception.NoExisteCaretaException;
import ipn.cic.sistmr.exception.NoExistePacienteException;
import ipn.cic.sistmr.exception.NoExistePacienteMedicoException;
import ipn.cic.sistmr.exception.PacienteException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.exception.UsuarioException;
import ipn.cic.sistmr.modelo.EntAntecedentes;
import ipn.cic.sistmr.modelo.EntCareta;
import ipn.cic.sistmr.modelo.EntCaretaHospital;
import ipn.cic.sistmr.modelo.EntEstadopaciente;
import ipn.cic.sistmr.modelo.EntGenero;
import ipn.cic.sistmr.modelo.EntHospital;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPacienteMedico;
import ipn.cic.sistmr.modelo.EntPersona;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.AntecedentesSBLocal;
import ipn.cic.sistmr.sesion.BitacoraSBLocal;
import ipn.cic.sistmr.sesion.CaretaHospitalSBLocal;
import ipn.cic.sistmr.sesion.CaretaSBLocal;
import ipn.cic.sistmr.sesion.CatalogoSBLocal;
import ipn.cic.sistmr.sesion.GeneroSBLocal;
import ipn.cic.sistmr.sesion.MedicoSBLocal;
import ipn.cic.sistmr.sesion.PacienteMedicoSBLocal;
import ipn.cic.sistmr.sesion.PacienteSBLocal;
import ipn.cic.sistmr.sesion.PersonaSBLocal;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import ipn.cic.sistmr.util.Constantes;
import ipn.cic.web.sistmr.bean.vo.PacienteVO;
import ipn.cic.web.sistmr.bean.vo.PersonaVO;
import ipn.cic.web.sistmr.bean.vo.UsuarioVO;
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
 * ManageBean para carga de pacientes
 *
 * @author J.Perez
 */

@Named(value = "gestionPacientesMB")
@ViewScoped
public class GestionPacientesMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(GestionPacientesMB.class.getName());

    String nomSistema = "";
    
    @EJB
    private PacienteSBLocal pacienteSB;
    @EJB
    private UtilWebSBLocal utilWebSB;
    @EJB
    private AntecedentesSBLocal antecedentesSB;
    @EJB
    CatalogoSBLocal catalogoSB;
    @EJB
    CaretaHospitalSBLocal caretahospitalSB;
    @EJB
    BitacoraSBLocal bitacoraSB;
    @EJB
    UsuarioSBLocal usuarioSB;
    @EJB
    MedicoSBLocal medicoSB;
    @EJB
    CaretaSBLocal caretaSB;
    @EJB
    PersonaSBLocal personaSB;
    @EJB
    GeneroSBLocal generoSB;
    @EJB
    PacienteMedicoSBLocal pacienteMedicoSB;

    private List<EntPaciente> listaPacientes;
    private EntPaciente pacienteMostrar;
    private EntUsuario usuarioPac;
    private EntAntecedentes antecedentesPac;
    
    private EntPaciente pacEditar;
    private EntUsuario usrEditar;
    
    private PersonaVO pacientePersona;
    private PacienteVO pacienteEditar;
    private UsuarioVO pacienteUsuario;
    private List<EntGenero> catGenero;
    private List<EntEstadopaciente> catEstado;
    private List<EntHospital> listaHospital;
    private List<EntMedico> listaMedicos;
    private List<EntCaretaHospital> listCaretaHospital;
    private EntMedico medicoPaciente;
    
    private String diabetes = "";
    private String cancer= "";
    private String asma = "";
    private String vih = "";
    private String has = "";
    private String epoc = "";
    private String embarazo = "";
    private String artritis = "";
    private String enfautoinmune= "";
    private String noSerie = "";
    
    private short idgenero = 0;
    private Integer idhospital = 0;
    private short idcareta = 0;
    private Integer idmedico = 0;
    
    private ArrayList<String> descripcion;

    @PostConstruct
    public void cargaPacientes() {
        pacienteEditar = new PacienteVO();
        pacientePersona = new PersonaVO();
        pacienteUsuario = new UsuarioVO();
        descripcion = new ArrayList();
        
        descripcion.add("ERROR EN TOMA DE SIGNOS");
        descripcion.add("AVERÍA EN SENSOR");
        descripcion.add("RUPTURA DEL DISPOSITIVO");
        descripcion.add("OTRO");
        
        nomSistema = Constantes.getInstance().getString("NOMBRE_SISTEMA");
        
        FacesMessage msg=null;
        logger.log(Level.INFO,"Entra a cargar lista de medicos.");
        //Recuperar lista de Medicos       
        try {
            listaPacientes = pacienteSB.getPacientes();
            logger.log(Level.INFO,"Lista pacientes recuperada.");
        } catch (PacienteException ex) {        
            logger.log(Level.SEVERE,"Error al cargar listado de medicos.");
        }
        
        try {
            //Cargar Lista de Hospitales
            listaHospital = catalogoSB.getCatalogo("EntHospital");
            
            if(listaHospital.isEmpty()){
                msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Hospital",
                            "Se requiere información del Hospital para realizar el registro.",
                            FacesMessage.SEVERITY_ERROR);
                utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
            }
        } catch (CatalogoException ex) {
            logger.log(Level.SEVERE, "Imposible recuperar datos de Hospital :{0} ",ex.getMessage());
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "No es posible recuperar datos de Hospital:" + ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
        }
        
         try {
            //Cargar Lista de Dispositivos no asignados
            listCaretaHospital = caretahospitalSB.getCaretasNoAsignadas();
            
            if(listCaretaHospital.isEmpty()){
                msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Dispositivo",
                            "Se requiere de un dispositivo disponible para realizar el registro.",
                            FacesMessage.SEVERITY_ERROR);
                utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
            }
        } catch (CaretaHospitalException ex) {
            listCaretaHospital = new ArrayList();
            logger.log(Level.SEVERE, "Imposible recuperar datos de Dispositivos :{0} ",ex.getMessage());
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "No es posible recuperar lista de Dispositivos:" + ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
        }
         
         try {
            catGenero =  catalogoSB.getCatalogo("EntGenero");
        } catch (CatalogoException ex) {
            logger.log(Level.SEVERE, "Imposible recuperar datos de Genero :{0} ",ex.getMessage());
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "No es posible recuperar catálogo de género:" + ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frGestPacientes:msgsGP", msg);
        }
         
         try {
            //Cargar Lista de Medicos
            setListaMedicos((List<EntMedico>) medicoSB.getMedicos());

            if(listaMedicos.isEmpty()){
                msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Médico",
                            "Se requiere información de Médico para realizar el registro.",
                            FacesMessage.SEVERITY_ERROR);
                utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
            }
        } catch (MedicoException ex) {
            listaMedicos = new ArrayList(); 
            logger.log(Level.SEVERE, "Entidad de medico no encontrado:{0} ",ex.getMessage());
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
    
    
    public void altaPaciente(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 750);
        options.put("height", 640);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        PrimeFaces.current().dialog().openDynamic("usuarios/dialAltaPaciente", options, null);
    }
    
    public void retornoAltaPaciente(SelectEvent event){
        FacesMessage msg = null;

        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();
            utilWebSB.addMsg("frGestPacientes:msgsGP", msg);
            cargaPacientes();
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo",
                            "Diálogo cerrado sin aplicar cambios",
                            FacesMessage.SEVERITY_INFO);
            utilWebSB.addMsg("frGestPacientes:msgsGP", msg);
        }
    }
    
    public void cambioDispositivo() {
         FacesMessage msg = null;
         
        usuarioPac = usuarioSB.getUsuarioDePaciente(pacienteMostrar);
                        
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 650);
        options.put("height", 610);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        
        //Envio de Parametros
        Map<String, List<String>> parametros = new HashMap<>();

        List<String> idpaciente = new ArrayList<>();
        idpaciente.add(pacienteMostrar.getIdPaciente() + "");
        
        List<String> nombre = new ArrayList<>();
        nombre.add(pacienteMostrar.getIdPersona().getNombre()+ "");
        
        List<String> primerapellido = new ArrayList<>();
        primerapellido.add(pacienteMostrar.getIdPersona().getPrimerApellido()+ "");
        
        List<String> segundoapellido = new ArrayList<>();
        segundoapellido.add(pacienteMostrar.getIdPersona().getSegundoApellido()+ "");
        
        List<String> curp = new ArrayList<>();
        curp.add(pacienteMostrar.getIdPersona().getCurp()+ "");
        
        List<String> edad = new ArrayList<>();
        edad.add(pacienteMostrar.getIdPersona().getEdad()+ "");
        
   
        
        List<String> idcareta = new ArrayList<>();
        idcareta.add(pacienteMostrar.getIdCareta().getIdCareta()+ "");
        
        List<String> noserie = new ArrayList<>();
        noserie.add(pacienteMostrar.getIdCareta().getNoSerie()+ "");
        
       

        parametros.put("idPaciente", idpaciente);
        parametros.put("nombre", nombre);
        parametros.put("primerapellido", primerapellido);
        parametros.put("segundoapellido", segundoapellido);
        parametros.put("curp", curp);
        parametros.put("edad", edad);

        parametros.put("idcareta", idcareta);
        parametros.put("noserie", noserie);
        

        PrimeFaces.current().dialog().openDynamic("usuarios/dialCambioDispositivo", options, parametros);
    }
    
    public void guardarCambioDispositivo() {
        

        try {
            
            pacienteMostrar = pacienteSB.getPaciente(pacienteEditar.getIdPaciente());
            
            EntCareta careta = caretaSB.getCareta(idcareta);
            pacienteMostrar.setIdCareta(careta);
            
            logger.log(Level.INFO, "Careta asignada: {0}", careta.getNoSerie());
            
            pacienteMostrar = pacienteSB.updatePaciente(pacienteMostrar);
            
            //Registrar operacion en bitacora
//            Date fechaEntrada = new Date();//Fecha de hoy
//            EntUsuario usrAdmin = utilWebSB.getUsrAutenticado();
//            
//            EntUsuario paciente = usuarioSB.getUsuario(datUsuario.getIdUsuario());
//            EntBitacora evento = bitacoraSB.eventoRegistroDePaciente(fechaEntrada, usrAdmin, paciente);
            
//        } catch (BitacoraException ex) {
//            logger.log(Level.INFO, "ERROR: No se registro evento en bitacora.");
        } catch (NoExisteCaretaException ex) {
            logger.log(Level.INFO, "ERROR: No se no se recupero la entidad careta.");
        } catch (UpdateEntityException ex) {
            logger.log(Level.INFO, "ERROR: No se actualizo el paciente.");
        } catch (NoExistePacienteException ex) {
            logger.log(Level.INFO, "ERROR: Error al recuperar entidad paciente.");
        }
        
        FacesMessage msg = null;
        if (pacienteMostrar == null) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Imposible realizar cambio de dispositivo, intentelo más tarde.",
                            FacesMessage.SEVERITY_ERROR);
            cerrarDialogo(msg);
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Cambio de Dispositivo",
                            "El cambio de dispositivo se realizó correctamente: No. Serie=" + this.pacienteMostrar.getIdCareta().getNoSerie(),
                            FacesMessage.SEVERITY_INFO);
        }

        cerrarDialogo(msg);
    }

    public void retornoCambioDispositivo(SelectEvent event) {
        FacesMessage msg = null;

        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();
            cargaPacientes();
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo ",
                            "Diálogo cerrado sin aplicar cambios",
                            FacesMessage.SEVERITY_INFO);
        }
        
        utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
    }
    
    public void editarPaciente() {
         FacesMessage msg = null;
         
        usuarioPac = usuarioSB.getUsuarioDePaciente(pacienteMostrar);
        
        try {
            medicoPaciente = medicoSB.getMedicoDePaciente(pacienteMostrar);
        } catch (MedicoException ex) {
            logger.log(Level.SEVERE, "Imposible recuperar medico :{0} ",ex.getMessage());
        }

        antecedentesPac = pacienteMostrar.getEntAntecedentes();
                
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 750);
        options.put("height", 640);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        
        //Envio de Parametros
        Map<String, List<String>> parametros = new HashMap<>();

        List<String> idpaciente = new ArrayList<>();
        idpaciente.add(pacienteMostrar.getIdPaciente() + "");
        
        List<String> nombre = new ArrayList<>();
        nombre.add(pacienteMostrar.getIdPersona().getNombre()+ "");
        
        List<String> primerapellido = new ArrayList<>();
        primerapellido.add(pacienteMostrar.getIdPersona().getPrimerApellido()+ "");
        
        List<String> segundoapellido = new ArrayList<>();
        segundoapellido.add(pacienteMostrar.getIdPersona().getSegundoApellido()+ "");
        
        List<String> curp = new ArrayList<>();
        curp.add(pacienteMostrar.getIdPersona().getCurp()+ "");
        
        List<String> edad = new ArrayList<>();
        edad.add(pacienteMostrar.getIdPersona().getEdad()+ "");
        
        List<String> genero = new ArrayList<>();
        genero.add(pacienteMostrar.getIdPersona().getIdGenero().getIdGenero()+ "");
        
        List<String> dircalle = new ArrayList<>();
        dircalle.add(pacienteMostrar.getDirCalle()+ "");
        
        List<String> dirnumero = new ArrayList<>();
        dirnumero.add(pacienteMostrar.getDirNumero()+ "");
        
        List<String> dirinterior = new ArrayList<>();
        dirinterior.add(pacienteMostrar.getDirInterior()+ "");
        
        List<String> telcel = new ArrayList<>();
        telcel.add(pacienteMostrar.getTelCel()+ "");
        
        List<String> telfijo = new ArrayList<>();
        telfijo.add(pacienteMostrar.getTelFijo()+ "");
        
        List<String> idhospital = new ArrayList<>();
        telfijo.add(pacienteMostrar.getIdHospital()+ "");
        
        List<String> idmedico = new ArrayList<>();
        idmedico.add(medicoPaciente.getIdMedico()+ "");
        
        List<String> idcareta = new ArrayList<>();
        idcareta.add(pacienteMostrar.getIdCareta().getIdCareta()+ "");
        
        List<String> noserie = new ArrayList<>();
        noserie.add(pacienteMostrar.getIdCareta().getNoSerie()+ "");
        
        List<String> correo = new ArrayList<>();
        correo.add(usuarioPac.getEmail()+ "");
        
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

        parametros.put("idPaciente", idpaciente);
        parametros.put("nombre", nombre);
        parametros.put("primerapellido", primerapellido);
        parametros.put("segundoapellido", segundoapellido);
        parametros.put("curp", curp);
        parametros.put("edad", edad);
        parametros.put("idgenero", genero);
        
        parametros.put("dircalle", dircalle);
        parametros.put("dirnumero", dirnumero);
        parametros.put("dirinterior", dirinterior);
        parametros.put("telcel", telcel);
        parametros.put("telfijo", telfijo);
        parametros.put("idhospital", idhospital);
        parametros.put("idmedico", idmedico);
        parametros.put("idcareta", idcareta);
        parametros.put("correo", correo);
        parametros.put("noserie", noserie);
        
        parametros.put("diab", diabetes);
        parametros.put("canc", cancer);
        parametros.put("asma", asma);
        parametros.put("vih", vih);
        parametros.put("has", has);
        parametros.put("epoc", epoc);
        parametros.put("emba", embarazo);
        parametros.put("artr", artritis);
        parametros.put("enfa", enfau);

        PrimeFaces.current().dialog().openDynamic("usuarios/dialEditaPaciente", options, parametros);
    }
    
    public void guardarCambiosPaciente() {

        logger.log(Level.INFO, "Entrando a actualizar datos de paciente.");
        FacesMessage msg = null;

        try {
            pacEditar = pacienteSB.getPaciente(pacienteEditar.getIdPaciente());
            
            logger.log(Level.INFO, "Paciente recuperado {0}.", pacienteEditar.getIdPaciente());
            EntMedico medicoPac = medicoSB.getMedico(idmedico);
            
            EntPacienteMedico pacMed = pacienteMedicoSB.getEntPacienteMedico(pacEditar);
            pacMed.setEntMedico(medicoPac);
            pacMed = pacienteMedicoSB.updateEntPacienteMedico(pacMed);
            ArrayList<EntPacienteMedico> pacmedlist = new ArrayList();
            pacmedlist.add(pacMed);
            
            usrEditar = usuarioSB.getUsuarioDePaciente(pacEditar);
            
            //Cambios Paciente
            pacEditar.setDirCalle(pacienteEditar.getDirCalle());
            pacEditar.setDirInterior(pacienteEditar.getDirInterior());
            pacEditar.setDirNumero(pacienteEditar.getDirNumero());
            pacEditar.setTelCel(pacienteEditar.getTelCel());
            pacEditar.setTelFijo(pacienteEditar.getTelFijo());
            pacEditar.setEntPacienteMedicoList(pacmedlist);
            
            pacEditar = pacienteSB.updatePaciente(pacEditar);
            
            usrEditar = usuarioSB.updateUsuario(usrEditar);
            logger.log(Level.INFO, "Datos de usuario ctualizados correctamente {0}.", usrEditar.getIdUsuario());
            EntPersona persona = usuarioSB.getPersonaDeUsuario(usrEditar);
            
            //Cambios Persona
            persona.setNombre(pacientePersona.getNombre());
            persona.setPrimerApellido(pacientePersona.getPrimerApellido());
            persona.setSegundoApellido(pacientePersona.getSegundoApellido());
            persona.setCurp(pacientePersona.getCurp());
            persona.setEdad(pacientePersona.getEdad());
            EntGenero genero = generoSB.getGeneroID(idgenero);
            persona.setIdGenero(genero);

            
            persona = personaSB.updatePersona(persona);
            logger.log(Level.INFO, "Datos de usuario actualizado correctamente {0}.", persona.getIdPersona());
            
           
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Exíto",
                            "Datos actualizados correctamente. ",
                            FacesMessage.SEVERITY_INFO);

        } catch (UsuarioException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Imposible recuperar datos de usuario, intente más tarde",
                            FacesMessage.SEVERITY_ERROR);
            cerrarDialogo(msg);
        }catch (UpdateEntityException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Imposible actualizar datos de usuario, intente más tarde",
                            FacesMessage.SEVERITY_ERROR);
        } catch (GeneroException ex) {
             msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Error al actualizar genero, intente más tarde",
                            FacesMessage.SEVERITY_ERROR);
        } catch (NoExistePacienteException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Error al recuperar paciente, intente más tarde",
                            FacesMessage.SEVERITY_ERROR);
        } catch (MedicoException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Error al realizar cambio de medico, intente más tarde",
                            FacesMessage.SEVERITY_ERROR);
        } catch (NoExistePacienteMedicoException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Imposible actulizar relacion medico paciente, intente más tarde",
                            FacesMessage.SEVERITY_ERROR);
        }

        logger.log(Level.INFO, "Datos de psciente actualizados.");
        utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
        cerrarDialogo(msg);
    }

    public void retornoEditaPaciente(SelectEvent event) {
        FacesMessage msg = null;

        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();
            cargaPacientes();
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo ",
                            "Diálogo cerrado sin aplicar cambios",
                            FacesMessage.SEVERITY_INFO);
        }
        
        utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
    }
    
    
    public void cerrarDialogo(){
        logger.log(Level.INFO,"Invocando cerrar dialogo.");
        cerrarDialogo(null);
    }
    
    public void cerrarDialogo(FacesMessage mensaje) {
        PrimeFaces.current().dialog().closeDynamic(mensaje);
    }

    public List<EntPaciente> getListaPacientes() {
        return listaPacientes;
    }

    public void setListaPacientes(List<EntPaciente> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }

    public EntPaciente getPacienteMostrar() {
        return pacienteMostrar;
    }

    public void setPacienteMostrar(EntPaciente pacienteMostrar) {
        this.pacienteMostrar = pacienteMostrar;
    }

    public String getNomSistema() {
        return nomSistema;
    }

    public void setNomSistema(String nomSistema) {
        this.nomSistema = nomSistema;
    }

    public PacienteVO getPacienteEditar() {
        return pacienteEditar;
    }

    public void setPacienteEditar(PacienteVO pacienteEditar) {
        this.pacienteEditar = pacienteEditar;
    }

    public PersonaVO getPacientePersona() {
        return pacientePersona;
    }

    public void setPacientePersona(PersonaVO pacientePersona) {
        this.pacientePersona = pacientePersona;
    }

    public EntAntecedentes getAntecedentesPac() {
        return antecedentesPac;
    }

    public void setAntecedentesPac(EntAntecedentes antecedentesPac) {
        this.antecedentesPac = antecedentesPac;
    }

    public String getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    public String getCancer() {
        return cancer;
    }

    public void setCancer(String cancer) {
        this.cancer = cancer;
    }

    public String getAsma() {
        return asma;
    }

    public void setAsma(String asma) {
        this.asma = asma;
    }

    public String getVih() {
        return vih;
    }

    public void setVih(String vih) {
        this.vih = vih;
    }

    public String getHas() {
        return has;
    }

    public void setHas(String has) {
        this.has = has;
    }

    public String getEpoc() {
        return epoc;
    }

    public void setEpoc(String epoc) {
        this.epoc = epoc;
    }

    public String getEmbarazo() {
        return embarazo;
    }

    public void setEmbarazo(String embarazo) {
        this.embarazo = embarazo;
    }

    public String getArtritis() {
        return artritis;
    }

    public void setArtritis(String artritis) {
        this.artritis = artritis;
    }

    public String getEnfautoinmune() {
        return enfautoinmune;
    }

    public void setEnfautoinmune(String enfautoinmune) {
        this.enfautoinmune = enfautoinmune;
    }

    public List<EntGenero> getCatGenero() {
        return catGenero;
    }

    public void setCatGenero(List<EntGenero> catGenero) {
        this.catGenero = catGenero;
    }

    public EntUsuario getUsuarioPac() {
        return usuarioPac;
    }

    public void setUsuarioPac(EntUsuario usuarioPac) {
        this.usuarioPac = usuarioPac;
    }

    public UsuarioVO getPacienteUsuario() {
        return pacienteUsuario;
    }

    public void setPacienteUsuario(UsuarioVO pacienteUsuario) {
        this.pacienteUsuario = pacienteUsuario;
    }

    public List<EntEstadopaciente> getCatEstado() {
        return catEstado;
    }

    public void setCatEstado(List<EntEstadopaciente> catEstado) {
        this.catEstado = catEstado;
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

    public List<EntCaretaHospital> getListCaretaHospital() {
        return listCaretaHospital;
    }

    public void setListCaretaHospital(List<EntCaretaHospital> listCaretaHospital) {
        this.listCaretaHospital = listCaretaHospital;
    }

    public EntMedico getMedicoPaciente() {
        return medicoPaciente;
    }

    public void setMedicoPaciente(EntMedico medicoPaciente) {
        this.medicoPaciente = medicoPaciente;
    }

    public short getIdgenero() {
        return idgenero;
    }

    public void setIdgenero(short idgenero) {
        this.idgenero = idgenero;
    }

    public Integer getIdhospital() {
        return idhospital;
    }

    public void setIdhospital(Integer idhospital) {
        this.idhospital = idhospital;
    }

    public short getIdcareta() {
        return idcareta;
    }

    public void setIdcareta(short idcareta) {
        this.idcareta = idcareta;
    }

    public Integer getIdmedico() {
        return idmedico;
    }

    public void setIdmedico(Integer idmedico) {
        this.idmedico = idmedico;
    }

    public ArrayList<String> getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(ArrayList<String> descripcion) {
        this.descripcion = descripcion;
    }

    public String getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
    }
    
    
}
