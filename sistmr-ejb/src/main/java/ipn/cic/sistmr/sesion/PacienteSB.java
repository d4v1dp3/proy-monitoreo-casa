/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.NoExistePacienteException;
import ipn.cic.sistmr.exception.PacienteException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPersona;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Stateless
public class PacienteSB extends BaseSB implements PacienteSBLocal {
    private static final Logger logger = Logger.getLogger(PacienteSB.class.getName());
    
    @Override
    public EntPaciente guardaPaciente(EntPaciente paciente) throws PacienteException {
        logger.log(Level.INFO,"Guardando datos paciente en SB");
        try {
            return (EntPaciente)saveEntity(paciente);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE,"Error al intentar salvar entidad : {0}", ex.getMessage());
            throw new PacienteException("Error al salvar entidad en PacienteSB",ex);
        }
    }
    
    @Override
    public EntPaciente getPaciente(long idPaciente) throws NoExistePacienteException {          
        Query qry = em.createQuery("SELECT e FROM EntPaciente e WHERE e.idPaciente = :idPaciente");
        qry.setParameter("idPaciente", idPaciente);
        try{
            EntPaciente res = (EntPaciente)qry.getSingleResult();
            res.getIdCareta();
            return res;
        }catch(NoResultException ex){
            logger.log(Level.SEVERE, "La consulta no obtuvo resultados");
            throw new NoExistePacienteException("No se encontro el paciente.");
        }
    }
    
    @Override
    public List<EntPaciente> getPacientes(EntMedico entMedico) throws PacienteException {
        try{
            logger.log(Level.SEVERE, "PacienteSB: Entra a ejecutar consulta.");
            
            query = em.createQuery("SELECT e From EntPaciente e "
                    + "WHERE e.entPacienteMedicoList.entMedico = :entMedico")
            .setParameter("entMedico", entMedico);
            
            return query.getResultList();
            
        }catch(Exception e){
            logger.log(Level.SEVERE,"Error al obtener la lista de pacientes : {0}",e.getMessage());
            throw new PacienteException("No esposible obtener la lista de pacientes",e);
        }
                    
    }
    
    
    @Override
    public EntPaciente getPaciente(EntPersona Persona) throws NoExistePacienteException {          
        Query qry = em.createQuery("SELECT e FROM EntPaciente e WHERE e.idPersona = :idPersona");
        qry.setParameter("idPersona", Persona);
        try{
            EntPaciente res = (EntPaciente)qry.getSingleResult();
            
            res.getIdPaciente();
            res.getIdPersona().getNombre();
            res.getIdPersona().getSegundoApellido();
            res.getIdPersona().getPrimerApellido();
            
            return res;
        }catch(NoResultException ex){
            logger.log(Level.SEVERE, "La consulta no obtuvo resultados");
            throw new NoExistePacienteException("No se encontro el paciente.");
        }
    }
    

}
