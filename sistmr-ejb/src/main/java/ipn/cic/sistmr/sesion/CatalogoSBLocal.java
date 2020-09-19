/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.CatalogoException;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz Local de Stateless Session Bean para manejo catalogos
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Local
public interface CatalogoSBLocal {
     /**
     *
     * @param nomEntidad Nombre de la entidad
     * @return Lista generica de la entidad pasada como parametro
     * @throws CatalogoException
     */
    public List getCatalogo(String nomEntidad) throws CatalogoException;
    
    /**
     *
     * @param entidad clase de la entidad
     * @param id Identificador del elemento del catalogo
     * @return Objeto de la entidad pasada como parametro
     * @throws CatalogoException
     */
    public Object getCatalogoElement(Class entidad, Integer id)throws CatalogoException;
    
}
