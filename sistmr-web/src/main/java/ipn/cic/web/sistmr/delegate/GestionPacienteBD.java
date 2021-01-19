/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipn.cic.web.sistmr.delegate;

import ipn.cic.sistmr.exception.GeneroException;
import ipn.cic.sistmr.exception.NoExisteHospitalException;
import ipn.cic.sistmr.exception.PacienteException;
import ipn.cic.sistmr.exception.RolException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.modelo.EntGenero;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPersona;
import ipn.cic.sistmr.modelo.EntRol;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.GeneroSBLocal;
import ipn.cic.sistmr.sesion.HospitalSBLocal;
import ipn.cic.sistmr.sesion.PacienteSBLocal;
import ipn.cic.sistmr.sesion.PersonaSBLocal;
import ipn.cic.sistmr.sesion.RolSBLocal;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import ipn.cic.sistmr.util.Constantes;
import ipn.cic.web.sistmr.bean.vo.PacienteVO;
import ipn.cic.web.sistmr.bean.vo.PersonaVO;
import ipn.cic.web.sistmr.bean.vo.UsuarioVO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 *
 * @author J.PEREZ
 */

@Stateless
@PermitAll
@SecurityDomain("other")
public class GestionPacienteBD implements GestionPacienteBDLocal {

     private static final Logger logger = Logger.getLogger(GestionMedicoBD.class.getName());

    @EJB
    private PersonaSBLocal personaSB;
    @EJB
    private UsuarioSBLocal usuarioSB;
    @EJB
    private PacienteSBLocal pacienteSB;
    @EJB
    private GeneroSBLocal generoSB;
    @EJB
    private RolSBLocal  rolSB;
    @EJB
    private HospitalSBLocal hospitalSB;

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public EntPaciente guardarPacienteNuevo(PacienteVO paciente, PersonaVO persona, UsuarioVO usuario) throws PacienteException {
        
        EntPersona entPersona = new EntPersona();
        
        entPersona.setNombre(persona.getNombre().toUpperCase());
        entPersona.setPrimerApellido(persona.getPrimerApellido().toUpperCase());
        entPersona.setSegundoApellido(persona.getSegundoApellido().toUpperCase());
        entPersona.setCurp(persona.getCurp().toUpperCase());
        
        try {
            EntGenero genero = generoSB.getGeneroID(persona.getIdGenero().shortValue());
            entPersona.setIdGenero(genero);
            entPersona = personaSB.savePersona(entPersona);
        } catch (GeneroException ex) {
            logger.log(Level.SEVERE, "Error al obtener EntGenero con id : {0}", persona.getIdGenero());
            logger.log(Level.SEVERE, "Error al obtener EntGenero: {0}", ex.getMessage());
            throw new PacienteException("Imposible asignar genero ", ex);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE,"Error al persistir EntPersona : {0}",ex.getMessage());
            throw new PacienteException("Imposible salvar Persona para Paciente ", ex);
        }
        
         // Creando la Entidad USUARIO
        EntUsuario entUsuario = new EntUsuario();
        entUsuario.setIdUsuario(usuario.getIdUsuario());
        entUsuario.setContrasenia(usuario.getContrasenia());
        entUsuario.setIdPersona(entPersona);
        entUsuario.setActivo(usuario.getActivo());
        Short pacRol = new Integer(Constantes.getInstance().getInt("ROL_PACIENTE")).shortValue();
        
        try {
            EntRol rolMedico = rolSB.getRolId(pacRol);
            entUsuario.getEntRolList().add(rolMedico);
            entUsuario = usuarioSB.saveUsuario(entUsuario);
        } catch (SaveEntityException | RolException ex) {
            logger.log(Level.SEVERE,"Error al persistir usuario : {0}",ex.getMessage());
            throw new PacienteException("Error ", ex);
        }
        
        EntPaciente entPac = new EntPaciente();
        
        logger.log(Level.INFO,"Creando Entidad Paciente[1]");
        entPac.setIdPersona(entPersona);
        entPac.setDirCalle(paciente.getDirCalle());
        entPac.setDirNumero(paciente.getDirNumero());
        entPac.setDirInterior(paciente.getDirInterior());
        entPac.setTelFijo(paciente.getTelFijo());
        entPac.setTelCel(paciente.getTelCel());
        //entPac.setIdCareta(paciente.getIdCareta());
        //entPac.setIdEstadopaciente(paciente.getIdEstadopaciente());
        
        try {            
            entPac.setIdHospital(hospitalSB.getPrimerHospital());
        } catch (NoExisteHospitalException ex) {
            logger.log(Level.SEVERE,"Error al ObtenerDatos de hospital : {0}",ex.getMessage());
            throw new PacienteException("Error al consultar datos de hospital ", ex);
        }

        logger.log(Level.INFO,"Invocando GuardaPaciente en BD");
        entPac = pacienteSB.guardaPaciente(entPac);
        
        return entPac;
    }
    
    
    
}
