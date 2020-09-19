/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.CatalogoException;
import ipn.cic.sistmr.exception.GetEntityException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Stateless
public class CatalogoSB extends BaseSB implements CatalogoSBLocal {
    private static final Logger logger = Logger.getLogger(CatalogoSB.class.getName());
    
@Override
    public List getCatalogo(String nomEntidad) throws CatalogoException {
        try {
            return getRegistros(nomEntidad);
        }catch(GetEntityException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new CatalogoException("Error al obtener el catalogo: "+nomEntidad, e);
        }
    }

    @Override
    public Object getCatalogoElement(Class entidad, Integer id) throws CatalogoException {
        try {
            return getRegistroById(entidad, id);
        }catch(GetEntityException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new CatalogoException("Error al obtener el elemento del catalogo: "+entidad.getSimpleName(), e);
        }
    }

}
