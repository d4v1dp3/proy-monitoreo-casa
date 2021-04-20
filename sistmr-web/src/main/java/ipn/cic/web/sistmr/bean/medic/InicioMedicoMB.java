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
import java.util.ArrayList;
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

    private List<EntPaciente> pacientesEstables;
    private List<EntPaciente> pacientesGraves;
    private List<EntPaciente> pacientesAtendidos;

    private EntPaciente pacienteMostrar;
    private EntHospital hospital;
    private EntPersona persona;

    private ArrayList<String> notificaciones;

    @PostConstruct
    public void cargaDatosMedico() {

        notificaciones = new ArrayList();
        pacientesEstables = new ArrayList();
        pacientesGraves = new ArrayList();
        pacientesAtendidos = new ArrayList();

        logger.log(Level.INFO, "Entra a cargar datos de inicio medico.");

        EntUsuario usrMedico = utilWebSB.getUsrAutenticado();

        try {
            persona = personaSB.getEntPersona(usrMedico.getIdPersona());;
        } catch (NoExistePersonaException ex) {
            logger.log(Level.SEVERE, "Error al recuperar datos del usuario.");
        }

        try {
            medico = medicoSB.getMedico(usrMedico.getIdPersona());
        } catch (MedicoException ex) {
            logger.log(Level.SEVERE, "Error al recuperar datos del medico.");
        }

        try {
            pacientesComp = medicoSB.getListaPaciente(medico);

            for (EntPaciente pac : pacientesComp) {
                if (pac.getIdEstadopaciente().getIdEstadopaciente() == 1) 
                    pacientesEstables.add(pac);
                else if (pac.getIdEstadopaciente().getIdEstadopaciente() == 2) 
                    pacientesGraves.add(pac);
                else
                    pacientesAtendidos.add(pac);
            }
        } catch (MedicoException ex) {
            logger.log(Level.SEVERE, "Error al recuperar listad de pacietes del medico.");
            pacientesComp = new ArrayList();

        }

        try {
            hospital = hospitalSB.getPrimerHospital();
        } catch (NoExisteHospitalException ex) {
            logger.log(Level.SEVERE, "Error al recuperar datos de hospital.");
        }

        logger.log(Level.INFO, "\tDatos de inicio medico cargados correctamente.");

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

    public ArrayList<String> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(ArrayList<String> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public List<EntPaciente> getPacientesEstables() {
        return pacientesEstables;
    }

    public void setPacientesEstables(List<EntPaciente> pacientesEstables) {
        this.pacientesEstables = pacientesEstables;
    }

    public List<EntPaciente> getPacientesGraves() {
        return pacientesGraves;
    }

    public void setPacientesGraves(List<EntPaciente> pacientesGraves) {
        this.pacientesGraves = pacientesGraves;
    }

    public List<EntPaciente> getPacientesAtendidos() {
        return pacientesAtendidos;
    }

    public void setPacientesAtendidos(List<EntPaciente> pacientesAtendidos) {
        this.pacientesAtendidos = pacientesAtendidos;
    }
}