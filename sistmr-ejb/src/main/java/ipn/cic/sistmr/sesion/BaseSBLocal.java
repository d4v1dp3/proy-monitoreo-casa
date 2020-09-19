/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.GetEntityException;
import ipn.cic.sistmr.exception.RemoveEntityException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.EntityManager;

/**
 * Interfaz Local de Stateless Session Bean para manejo de acciones comunes del
 * EntityManager.
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Local
public interface BaseSBLocal {
  /**
     * Persiste la entidad pasada como parametro
     *
     * @param entity Entidad a persistir.
     * @return Entidad persistida con su CVE primaria asignada se puede
     * persistir a causa de una excepcion.
     * @throws SaveEntityException
     */
    public Object saveEntity(Object entity) throws SaveEntityException;

    /**
     * Actualiza la entidad pasada como parametro.
     *
     * @param entity Entidad que se actualiza.
     * @return La entidad actualizada, devuelve null si no se puede actualizar
     * la entidad a causa de una excepcion.
     * @throws UpdateEntityException
     */
    public Object updateEntity(Object entity) throws UpdateEntityException;

    /**
     * Elimina la entidad pasada como parametro.
     *
     * @param entity Entidad a borrar.
     * @return true -> si se borra la entidad correctamente, false -> si no se
     * puede eliminar a causa de una excepcion.
     * @throws ipn.dch.siiee.sesion.exceptions.RemoveEntityException
     */
    public boolean removeEntity(Object entity) throws RemoveEntityException;

    /**
     * Obtiene True si el query obtiene resultados 1 o mas resultados
     *
     * @param nombEntity Clase que identifica el tipo de entity buscado
     * @param where Cadena que contiene las restricciones para la consulta
     * @param params Parametros a ser agregados a la consulta
     * @return true -> si el query obtiene resultados 1 o mas resultados false
     * -> si el query no obtiene resultados
     * @throws GetEntityException
     */
    public boolean existeRegistroByQry(String nombreEntity, String where, 
                                        HashMap<String, Object> params) throws GetEntityException;

    /**
     * Obtiene el catalogo definido por el parametro nombreEntity
     *
     * @param nombreEntity Nombre de la clase de la entidad buscada
     * @return Lista de entidades del tipo del parametro nombreEntity
     * @throws GetEntityException
     */
    public List getRegistros(String nombreEntity) throws GetEntityException;

    public List getRegistrosByQry(String nombreEntity, String qry) throws GetEntityException;

    public Object getRegistroByQry(String sel, String nombreEntity, String qry) throws GetEntityException;

    /**
     * Obtiene la entidad asociada al parametro id
     *
     * @param entity Clase que identifica el tipo de entity
     * @param id Llave primaria de la entidad
     * @return Objeto de tipo entity enviado como parametro
     * @throws GetEntityException
     */
    public Object getRegistroById(Class entity, Integer id) throws GetEntityException;
    public Object getRegistroById(Class entity, Short id) throws GetEntityException ;
}