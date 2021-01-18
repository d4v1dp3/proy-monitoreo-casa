/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.NoExisteCaretaException;
import ipn.cic.sistmr.modelo.EntCareta;
import java.util.List;
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public EntCareta getCareta(long idCareta) throws NoExisteCaretaException {        
        Query qry = em.createQuery("SELECT e FROM EntCareta e WHERE e.idCareta = :idCareta");
        List<EntCareta> res = qry.getResultList();//DUDA
        
        if(res.size()==0){
            throw new NoExisteCaretaException("Careta no encontrada.");
        }
        return res.get(0);
    }
}
