/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.CaretaException;
import ipn.cic.sistmr.exception.NoExisteCaretaException;
import ipn.cic.sistmr.exception.RemoveEntityException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.modelo.EntCareta;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.Query;

/**
 *
 * @author J.PEREZ
 */
@Stateless
@LocalBean
public class CaretaSB extends BaseSB implements CaretaSBLocal{
    private static final Logger logger = Logger.getLogger(HospitalSB.class.getName());

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public EntCareta guardaCareta(EntCareta careta) throws CaretaException {
        try {
            return (EntCareta)saveEntity(careta);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE,"Error al intentar salvar entidad : {0}", ex.getMessage());
            throw new CaretaException("Error al salvar entidad en CaretaSB",ex);
        }
    }
    
    @Override
    public EntCareta getCareta(long idCareta) throws NoExisteCaretaException {        
        Query qry = em.createQuery("SELECT e FROM EntCareta e WHERE e.idCareta = :idCareta");
        qry.setParameter("idCareta", idCareta);
        
        EntCareta res = (EntCareta)qry.getSingleResult();
        
        return res;
    }
    
    @Override
    public List<EntCareta> getCaretas() throws NoExisteCaretaException {        
        Query qry = em.createQuery("SELECT e FROM EntCareta e");
        List<EntCareta> res = qry.getResultList();        
        if(res.size()==0){
            throw new NoExisteCaretaException("Careta no encontrada.");
        }
        return res;
    }
    
    @Override
    public boolean borrarCareta(EntCareta careta) throws RemoveEntityException {  
        return removeEntity(careta);
    }
    
    @Override
    public EntCareta updateCareta(EntCareta careta) throws UpdateEntityException {//*
        return (EntCareta)this.updateEntity(careta);   
    }
}

