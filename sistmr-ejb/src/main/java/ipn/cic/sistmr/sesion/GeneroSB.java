/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.GeneroException;
import ipn.cic.sistmr.modelo.EntGenero;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author iliac
 */
@Stateless
public class GeneroSB extends BaseSB implements GeneroSBLocal {
    private static final Logger logger = Logger.getLogger(GeneroSB.class.getName());
    
    @Override
    public EntGenero getGeneroID(Short idGenero) throws GeneroException {
        try {
            Query qry = em.createNamedQuery("EntGenero.findByIdGenero");
            qry.setParameter("idGenero", idGenero);
            EntGenero genero = (EntGenero) qry.getSingleResult();
            return genero;
        }catch(Exception ex){
            logger.log(Level.SEVERE,"Error al obtener Entidad Genero : {0}",ex.getMessage());
            throw new GeneroException("Imposible recuperar EntGenero",ex);
        }
    }

}
