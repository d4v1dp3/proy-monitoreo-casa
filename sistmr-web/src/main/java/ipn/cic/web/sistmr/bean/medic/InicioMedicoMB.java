/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.medic;

import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.exception.NoExisteHospitalException;
import ipn.cic.sistmr.exception.NoExistePersonaException;
import ipn.cic.sistmr.modelo.EntHospital;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPersona;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.HospitalSBLocal;
import ipn.cic.sistmr.sesion.MedicoSBLocal;
import ipn.cic.sistmr.sesion.PersonaSBLocal;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author J. Perez
 */
@Named(value = "inicioMedicoMB")
@ViewScoped
public class InicioMedicoMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(InicioMedicoMB.class.getName());

    @EJB
    private PersonaSBLocal personaSB;
    @EJB
    private MedicoSBLocal medicoSB;
    @EJB
    private UtilWebSBLocal utilWebSB;
    @EJB
    private HospitalSBLocal hospitalSB;

    private EntMedico medico;
    private List<EntPaciente> pacientesComp;
    private EntPaciente pacienteMostrar;
    private EntHospital hospital;
    private EntPersona persona;

    @PostConstruct
    public void cargaDatosMedico() {

        //Recuperar Datos       
        try {
            logger.log(Level.INFO, "Entra a cargar medico.");
            EntUsuario usrMedico = utilWebSB.getUsrAutenticado();
            logger.log(Level.INFO, "Usuario encontrado: {0}", usrMedico.getIdPersona());

            persona = personaSB.getEntPersona(usrMedico.getIdPersona());
            logger.log(Level.INFO, "Persona encontrada: {0}", persona.getNombre());

            medico = medicoSB.getMedico(usrMedico.getIdPersona());
     //       logger.log(Level.INFO, "Medico encontrado: {0}", medico.getEmail());
            pacientesComp = medicoSB.getListaPaciente(medico);

            hospital = hospitalSB.getPrimerHospital();
            logger.log(Level.INFO, "Hospital Recuperado: {0}", hospital.getIdHospital());

        } catch (MedicoException ex) {
            logger.log(Level.SEVERE, "Error al cargar medico.");
        } catch (NoExisteHospitalException ex) {
            logger.log(Level.SEVERE, "Error al cargar hospital.");
        }catch (NoExistePersonaException ex) {
            logger.log(Level.SEVERE, "Error al cargar esntidad persona.");
        }

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

    public EntMedico getMedico() {
        return medico;
    }

    public void setMedico(EntMedico medico) {
        this.medico = medico;
    }

    public EntHospital getHospital() {
        return hospital;
    }

    public void setHospital(EntHospital hospital) {
        this.hospital = hospital;
    }

    public EntPersona getPersona() {
        return persona;
    }

    public void setPersona(EntPersona persona) {
        this.persona = persona;
    }

    
}
