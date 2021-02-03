/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.modelo.EntPersona;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

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
    
}
