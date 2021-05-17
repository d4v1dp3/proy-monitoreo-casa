/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.NoExistePacienteMedicoException;
import ipn.cic.sistmr.exception.PacienteMedicoException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPacienteMedico;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author iliaco
 */
@Stateless
public class PacienteMedicoSB extends BaseSB implements PacienteMedicoSBLocal {
    private static final Logger logger = Logger.getLogger(HospitalSB.class.getName());

    @Override
    public EntPacienteMedico guardaEntPacienteMedico(EntPacienteMedico pacmed) throws PacienteMedicoException {
        try {
            return (EntPacienteMedico)saveEntity(pacmed);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE,"Error al intentar salvar entidad : {0}", ex.getMessage());
            throw new PacienteMedicoException("Error al salvar entidad en PacienteMedicoSB",ex);
        }
    }
    
    @Override
    public EntPacienteMedico updateEntPacienteMedico(EntPacienteMedico pacMed) throws UpdateEntityException {//*
        return (EntPacienteMedico) this.updateEntity(pacMed);
    }

    @Override
    public EntPacienteMedico getEntPacienteMedico(EntPaciente idPaciente) throws NoExistePacienteMedicoException {
        Query qry = em.createQuery("SELECT e FROM EntPacienteMedico e WHERE e.entPaciente = :idPaciente");
        qry.setParameter("idPaciente", idPaciente);
        try {
            EntPacienteMedico res = (EntPacienteMedico) qry.getSingleResult();
            return res;
        } catch (NoResultException ex) {
            logger.log(Level.SEVERE, "La consulta no obtuvo resultados");
            throw new NoExistePacienteMedicoException("No se encontro la relacion medico-Paciente.");
        }
    }
}
