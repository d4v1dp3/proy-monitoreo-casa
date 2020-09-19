/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.MenuException;
import ipn.cic.sistmr.modelo.EntMenu;
import ipn.cic.sistmr.modelo.EntMenuOpcion;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz Local de Stateless Session Bean para manejo del menu
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Local
public interface MenuSBLocal {
      /**
     * Obtiene el menu del rol identificado por el parametro idRol ordenados por
     * idRol ascendentemente
     *
     * @param idRol Identificador unico del rol
     * @return Lista de entidades EntMenu
     * @throws MenuException
     */
    public List<EntMenu> getMenuByRol(Short idRol) throws MenuException;

    /**
     * Obtiene las opciones del menu identificado por el parametro idMenu y
     * ordena los resultados en orden ascendente por posicion
     *
     * @param idMenu Identificador unico del menu
     * @return Lista de entidades EntMenuOpcion
     * @throws MenuException
     */
    public List<EntMenuOpcion> getOpcsMenu(Short idMenu) throws MenuException;
}

