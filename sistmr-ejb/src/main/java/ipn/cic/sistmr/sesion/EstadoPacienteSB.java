/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.EstadoPacienteException;
import ipn.cic.sistmr.modelo.EntEstadopaciente;
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
public class EstadoPacienteSB extends BaseSB implements EstadoPacienteSBLocal {
    private static final Logger logger = Logger.getLogger(EstadoPacienteSB.class.getName());

    @Override
    public EntEstadopaciente getEstadoPaciente(Integer idestado) throws EstadoPacienteException {
        Query qry = em.createQuery("SELECT e FROM EntEstadopaciente e WHERE e.idEstadopaciente = :idestado");
        qry.setParameter("idestado", idestado);
        try{
            EntEstadopaciente res = (EntEstadopaciente)qry.getSingleResult();
            logger.log(Level.SEVERE, "Estado {0} encontrado.", res.getIdEstadopaciente());
            return res;
        }catch(NoResultException ex){
            logger.log(Level.SEVERE, "La consulta no obtuvo resultados");
            throw new EstadoPacienteException("No se encontro el estado de paciente SB.");
        }
    }

}
