/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.RolException;
import ipn.cic.sistmr.modelo.EntRol;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author iliac
 */
@Stateless
public class RolSB extends BaseSB implements RolSBLocal {

    @Override
    public EntRol getRolId(Short idRol) throws RolException {
        Query qry = em.createNamedQuery("EntRol.findByIdRol");
        qry.setParameter("idRol", idRol);
        
        return (EntRol)qry.getSingleResult();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
