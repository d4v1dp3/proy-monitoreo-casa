/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.NoExistePersonaException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.modelo.EntPersona;
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
public class PersonaSB extends BaseSB implements PersonaSBLocal {
    private static final Logger logger = Logger.getLogger(PersonaSB.class.getName());
    
    @Override
    public EntPersona savePersona(EntPersona persona) throws SaveEntityException {
        persona = (EntPersona) this.saveEntity(persona);
        return persona;
    }
    
    @Override
    public EntPersona getPersonaDePaciente(Long idPaciente) {
        query = em.createQuery("SELECT e.idPersona FROM EntPaciente e WHERE e.idPaciente = :idPaciente");
        query.setParameter("idPaciente", idPaciente);
        try{ 
            EntPersona persona = (EntPersona)query.getSingleResult();
            return persona;
        }catch(NoResultException ex){
            logger.log(Level.SEVERE, "La consulta no obtuvo resultados");
        }
        return null;
    }
    
      @Override
    public EntPersona getEntPersona(EntPersona Persona) throws NoExistePersonaException {
        Query qry = em.createQuery("SELECT e FROM EntPersona e WHERE e.idPersona = :idPersona");
        qry.setParameter("idPersona", Persona.getIdPersona());

        EntPersona res = (EntPersona) qry.getSingleResult();
        res.getCurp();
        res.getEdad();
        res.getNombre();
        res.getPrimerApellido();
        res.getSegundoApellido();
        return res;
    }
    
}
