/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipn.cic.web.sistmr.delegate;

import ipn.cic.sistmr.exception.AntecedentesException;
import ipn.cic.sistmr.exception.CatalogoException;
import ipn.cic.sistmr.exception.GeneroException;
import ipn.cic.sistmr.exception.IDUsuarioException;
import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.exception.NoExisteCaretaException;
import ipn.cic.sistmr.exception.NoExisteHospitalException;
import ipn.cic.sistmr.exception.PacienteException;
import ipn.cic.sistmr.exception.PacienteMedicoException;
import ipn.cic.sistmr.exception.RolException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.modelo.EntAntecedentes;
import ipn.cic.sistmr.modelo.EntCareta;
import ipn.cic.sistmr.modelo.EntEstadopaciente;
import ipn.cic.sistmr.modelo.EntGenero;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPacienteMedico;
import ipn.cic.sistmr.modelo.EntPersona;
import ipn.cic.sistmr.modelo.EntRol;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.AntecedentesSBLocal;
import ipn.cic.sistmr.sesion.CaretaSBLocal;
import ipn.cic.sistmr.sesion.CatalogoSBLocal;
import ipn.cic.sistmr.sesion.EstadoPacienteSBLocal;
import ipn.cic.sistmr.sesion.GeneroSBLocal;
import ipn.cic.sistmr.sesion.HospitalSBLocal;
import ipn.cic.sistmr.sesion.MedicoSBLocal;
import ipn.cic.sistmr.sesion.PacienteMedicoSBLocal;
import ipn.cic.sistmr.sesion.PacienteSBLocal;
import ipn.cic.sistmr.sesion.PersonaSBLocal;
import ipn.cic.sistmr.sesion.RolSBLocal;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import ipn.cic.sistmr.util.Constantes;
import ipn.cic.web.sistmr.bean.vo.AntecedentesVO;
import ipn.cic.web.sistmr.bean.vo.PacienteVO;
import ipn.cic.web.sistmr.bean.vo.PersonaVO;
import ipn.cic.web.sistmr.bean.vo.UsuarioVO;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 *
 * @author J.PEREZ
 */
@Stateless
@PermitAll
@SecurityDomain("other")
@TransactionManagement(value = TransactionManagementType.CONTAINER)
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
public class GestionPacienteBD implements GestionPacienteBDLocal {

    private static final Logger logger = Logger.getLogger(GestionMedicoBD.class.getName());

    private List<EntEstadopaciente> catEstado;
    @EJB
    private PersonaSBLocal personaSB;
    @EJB
    private UsuarioSBLocal usuarioSB;
    @EJB
    private PacienteSBLocal pacienteSB;
    @EJB
    private MedicoSBLocal medicoSB;
    @EJB
    private PacienteMedicoSBLocal pacientemedicoSB;
    @EJB
    private CaretaSBLocal caretaSB;
    @EJB
    private EstadoPacienteSBLocal estadopacSB;
    @EJB
    private GeneroSBLocal generoSB;
    @EJB
    private RolSBLocal rolSB;
    @EJB
    private HospitalSBLocal hospitalSB;
    @EJB
    private AntecedentesSBLocal antecedentesSB;
    @EJB
    CatalogoSBLocal catalogoSB;
    @Resource
    private EJBContext ejbContext;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public EntPaciente guardarPacienteNuevo(PacienteVO paciente, PersonaVO persona, AntecedentesVO antecedentes, UsuarioVO usuario) throws PacienteException, IDUsuarioException {

        EntPersona entPersona = new EntPersona();
        EntPaciente entPac = new EntPaciente();

        entPersona.setNombre(persona.getNombre().toUpperCase());
        entPersona.setPrimerApellido(persona.getPrimerApellido().toUpperCase());
        entPersona.setSegundoApellido(persona.getSegundoApellido().toUpperCase());
        entPersona.setCurp(persona.getCurp().toUpperCase());
        entPersona.setEdad(persona.getEdad());

        try {
            EntGenero genero = generoSB.getGeneroID(persona.getIdGenero().shortValue());
            entPersona.setIdGenero(genero);
            entPersona = personaSB.savePersona(entPersona);

            // Creando la Entidad USUARIO
            EntUsuario entUsuario = new EntUsuario();
            entUsuario.setIdUsuario(usuario.getIdUsuario());
            entUsuario.setContrasenia(usuario.getContrasenia());
            entUsuario.setIdPersona(entPersona);
            entUsuario.setActivo(usuario.getActivo());
            Short pacRol = new Integer(Constantes.getInstance().getInt("ROL_PACIENTE")).shortValue();

            EntRol rolPaciente = rolSB.getRolId(pacRol);
            logger.log(Level.INFO, "Rol asignado: {0}", rolPaciente.getDescripcion());
            entUsuario.getEntRolList().add(rolPaciente);
            entUsuario = usuarioSB.saveUsuario(entUsuario);

            logger.log(Level.INFO, "Creando Entidad Paciente[1]");
            entPac.setIdPersona(entPersona);
            entPac.setDirCalle(paciente.getDirCalle());
            entPac.setDirNumero(paciente.getDirNumero());
            entPac.setDirInterior(paciente.getDirInterior());
            entPac.setTelFijo(paciente.getTelFijo());
            entPac.setTelCel(paciente.getTelCel());
            
            EntCareta careta = new EntCareta();
            careta = caretaSB.getCareta(paciente.getIdCareta());
            
            entPac.setIdCareta(careta);
            
            logger.log(Level.INFO, "Recuperando Estado Paciente {0}...", paciente.getIdEstadopaciente());
            catEstado = (List<EntEstadopaciente>) catalogoSB.getCatalogo("EntEstadopaciente");
            EntEstadopaciente estadopac = catEstado.get(paciente.getIdEstadopaciente()-1);
            logger.log(Level.INFO, "Estado Paciente recuperado con {0}", estadopac.getDescripcion());
            
            entPac.setIdEstadopaciente(estadopac);

            entPac.setIdHospital(hospitalSB.getPrimerHospital());
            logger.log(Level.INFO, "Salvando paciente...");
            entPac = pacienteSB.guardaPaciente(entPac);
            logger.log(Level.INFO, "Paciente salvado con id={0}", entPac.getIdPaciente());
            logger.log(Level.INFO, "Creando Entidad Antecedente...");
            EntAntecedentes entAnt = new EntAntecedentes();
            entAnt.setDiabetes(antecedentes.getDiabetes());
            entAnt.setCancer(antecedentes.getCancer());
            entAnt.setIdPaciente(entPac.getIdPaciente());
            entAnt.setAsma(antecedentes.getAsma());
            entAnt.setVih(antecedentes.getVih());
            entAnt.setHas(antecedentes.getHas());
            entAnt.setEpoc(antecedentes.getEpoc());
            entAnt.setEmbarazo(antecedentes.getEmbarazo());
            entAnt.setArtritis(antecedentes.getArtritis());
            entAnt.setEnfautoinmune(antecedentes.getEnfautoinmune());
            entAnt.setFecha(Calendar.getInstance().getTime());
            entAnt.setEntPaciente(entPac);

            entAnt = antecedentesSB.guardaAntecedentes(entAnt);
            
            EntMedico entMedico = null;
            logger.log(Level.INFO, "Recuperando Medico con id={0}...", paciente.getIdMedico());
            entMedico = medicoSB.getMedico(paciente.getIdMedico().intValue());
            logger.log(Level.INFO, "Entidad Medico recuperada con cedula={0}", entMedico.getCedulaProf());

            
            EntPacienteMedico pacmed = new EntPacienteMedico(entPac.getIdPaciente(), entMedico.getIdMedico());
            pacmed.setEntMedico(entMedico);
            pacmed.setEntPaciente(entPac);
            //Obtener fecha:
            Date fecha = new Date();
            pacmed.setFechaInicio(fecha);

            pacmed = pacientemedicoSB.guardaEntPacienteMedico(pacmed);
            logger.log(Level.INFO, "Entidad PacienteMedico salvada:{0}", pacmed.getFechaInicio());

        } catch (GeneroException ex) {
            logger.log(Level.SEVERE, "Error al obtener EntGenero con id : {0}", persona.getIdGenero());
            logger.log(Level.SEVERE, "Error al obtener EntGenero: {0}", ex.getMessage());
            ejbContext.setRollbackOnly();
            throw new PacienteException("Imposible asignar genero ", ex);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE, "Error al persistir EntPersona : {0}", ex.getMessage());
            ejbContext.setRollbackOnly();
            throw new PacienteException("Imposible salvar Persona para Paciente ", ex);
        } catch (NoExisteCaretaException ex) {
            Logger.getLogger(GestionPacienteBD.class.getName()).log(Level.SEVERE, null, ex);
            ejbContext.setRollbackOnly();
        } catch (NoExisteHospitalException ex) {
            logger.log(Level.SEVERE, "Error al ObtenerDatos de hospital : {0}", ex.getMessage());
            ejbContext.setRollbackOnly();
            throw new PacienteException("Error al consultar datos de hospital ", ex);
        } catch (AntecedentesException ex) {
            logger.log(Level.SEVERE, "Error al salvar entidad en AntecedentesSB: {0}", ex.getMessage());
            ejbContext.setRollbackOnly();
        } catch (PacienteException ex) {
            logger.log(Level.SEVERE, "Error al intentar salvar entidad paciente : {0}", ex.getMessage());
            ejbContext.setRollbackOnly();
            throw new PacienteException("Error al salvar entidad en PacienteSB", ex);
        } catch (MedicoException ex) {
            logger.log(Level.SEVERE, "Error al recuperar medico: {0}", ex.getMessage());
            ejbContext.setRollbackOnly();
        } catch (PacienteMedicoException ex) {
            logger.log(Level.SEVERE, "Error al salvar entidad pacientemedico: {0}", ex.getMessage());
            ejbContext.setRollbackOnly();
        } catch (RolException ex) {
            Logger.getLogger(GestionPacienteBD.class.getName()).log(Level.SEVERE, null, ex);
            ejbContext.setRollbackOnly();
        } catch (CatalogoException ex) {
            Logger.getLogger(GestionPacienteBD.class.getName()).log(Level.SEVERE, null, ex);
            ejbContext.setRollbackOnly();
        }

        return entPac;
    }
}
