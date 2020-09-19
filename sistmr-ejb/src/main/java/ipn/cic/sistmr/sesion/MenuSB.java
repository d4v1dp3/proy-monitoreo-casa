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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Stateless
public class MenuSB implements MenuSBLocal {

    private static final Logger logger = Logger.getLogger(MenuSB.class.getName());
    @PersistenceContext(unitName = "sistmrDS")
    private EntityManager em;
    private Query query;

    /**
     * Obtiene el menu del rol identificado por el parametro idRol ordenados por
     * idRol ascendentemente
     *
     * @param idRol Identificador unico del rol
     * @return Lista de entidades EntMenu
     * @throws MenuException
     */
    @Override
    public List<EntMenu> getMenuByRol(Short idRol) throws MenuException {
        try {
            query = em.createQuery("SELECT m FROM EntMenu m "
                    + "JOIN m.entRolList r "
                    + "WHERE r.idRol=:id_Rol "
                    + "ORDER BY m.posicion ASC");
            query.setParameter("id_Rol", idRol);
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener el menu del idRol: " + idRol, e);
            throw new MenuException(e.getMessage(), e);
        }
    }

    /**
     * Obtiene las opciones del menu identificado por el parametro idMenu y
     * ordena los resultados en orden ascendente por posicion
     *
     * @param idMenu Identificador unico del menu
     * @return Lista de entidades EntMenuOpcion
     * @throws MenuException
     */
    @Override
    public List<EntMenuOpcion> getOpcsMenu(Short idMenu) throws MenuException {
        try {
            query = em.createQuery("SELECT mo FROM EntMenuOpcion mo "
                    + "LEFT OUTER JOIN FETCH mo.idOpcion m "
                    + "LEFT OUTER JOIN FETCH mo.idSubMenu sm "
                    + "WHERE mo.idMenu.idMenu=:id_Menu "
                    + "ORDER BY mo.posicion ASC");
            query.setParameter("id_Menu", idMenu);
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener las opciones del menu:" + idMenu, e);
            throw new MenuException(e.getMessage(), e);
        }
    }
}
