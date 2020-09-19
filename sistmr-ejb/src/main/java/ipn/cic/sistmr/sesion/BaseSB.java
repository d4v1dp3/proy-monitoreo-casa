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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Stateless
public class BaseSB implements BaseSBLocal {
    private static final Logger logger = Logger.getLogger(BaseSB.class.getName());
    @PersistenceContext(unitName = "sistmrDS")
    protected EntityManager em;
    
    protected Query query;

    /**
     * Persiste la entidad pasada como parametro
     *
     * @param entity Entidad a persistir.
     * @return Entidad persistida con su CVE primaria asignada se puede
     * persistir a causa de una excepcion.
     * @throws SaveEntityException
     */
    @Override
    public Object saveEntity(Object entity) throws SaveEntityException {
        try {
            em.persist(entity);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al persistir entity: " + entity, e);
            throw new SaveEntityException(e.getMessage(), e);
        }
        return entity;
    }

    /**
     * Actualiza la entidad pasada como parametro.
     *
     * @param entity Entidad que se actualiza.
     * @return La entidad actualizada, devuelve null si no se puede actualizar
     * la entidad a causa de una excepcion.
     * @throws UpdateEntityException
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object updateEntity(Object entity) throws UpdateEntityException {
        Object entityCopy = null;
        if (entity != null) {
            try {
                entityCopy = em.merge(entity);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error al actualizar entity: " + entity, e);
                throw new UpdateEntityException(e.getMessage(), e);
            }
            return entityCopy;
        } else {
            return null;
        }
    }

    /**
     * Elimina la entidad pasada como parametro.
     *
     * @param entity Entidad a borrar.
     * @return true -> si se borra la entidad correctamente, false -> si no se
     * puede eliminar a causa de una excepcion.
     * @throws RemoveEntityException
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean removeEntity(Object entity) throws RemoveEntityException {
        try {
            em.remove(em.merge(entity));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al eliminar entity: " + entity, e);
            throw new RemoveEntityException(e.getMessage(), e);
        }
        return true;
    }

    
     /**
     * Obtiene True si el query obtiene resultados 1 o mas resultados
     *
     * @param nombreEntity Clase que identifica el tipo de entity buscado
     * @param where Cadena que contiene las restricciones para la consulta
     * @param params Parametros a ser agregados a la consulta
     * @return true -> si el query obtiene resultados 1 o mas resultados false
     * -> si el query no obtiene resultados
     * @throws GetEntityException
     */
    @Override
    public boolean existeRegistroByQry(String nombreEntity, String where, HashMap<String, Object> params) throws GetEntityException {
        try {
            query = em.createQuery("FROM " + nombreEntity + " WHERE " + where);

            if (params != null) {
                for (String name : params.keySet()) {
                    query.setParameter(name, params.get(name));
                }
            }
            query.setMaxResults(1);
            if (query.getResultList().size() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener Registro:  " + nombreEntity + " Qry: " + where, e);
            throw new GetEntityException(e.getMessage(), e);
        }
    }


   /**
     * Obtiene el catalogo definido por el parametro nombreEntity
     *
     * @param nombreEntity Nombre de la clase de la entidad buscada
     * @return Lista de entidades del tipo del parametro nombreEntity
     * @throws GetEntityException
     */
    @Override
    public List getRegistros(String nombreEntity) throws GetEntityException {
        try {
            query = em.createNamedQuery(nombreEntity + ".findAll");
            return query.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener Registro:  " + nombreEntity, e);
            throw new GetEntityException(e.getMessage(), e);
        }
    }

    
   @Override
    public List getRegistrosByQry(String nombreEntity, String qry) throws GetEntityException {
        try {
            query = em.createQuery("FROM " + nombreEntity + " WHERE " + qry);
            return query.getResultList();
        } catch (IllegalStateException ise) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + nombreEntity + " Qry: " + qry, ise);
            throw new GetEntityException(ise.getMessage(), ise);
        } catch (IllegalArgumentException iae) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + nombreEntity + " Qry: " + qry, iae);
            throw new GetEntityException(iae.getMessage(), iae);
        }
    }

    @Override
    public Object getRegistroByQry(String sel, String nombreEntity, String qry) throws GetEntityException {
        try {
            if (existeRegistroByQry(nombreEntity, qry, null)) {
                query = em.createQuery("SELECT " + sel + " FROM " + nombreEntity + " WHERE " + qry);
                return query.getSingleResult();
            } else {
                return null;
            }
        } catch (IllegalStateException ise) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + nombreEntity + " Qry: " + qry, ise);
            throw new GetEntityException(ise.getMessage(), ise);
        } catch (IllegalArgumentException iae) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + nombreEntity + " Qry: " + qry, iae);
            throw new GetEntityException(iae.getMessage(), iae);
        }
    }

    /**
     * Obtiene la entidad asociada al parametro id.
     *
     * @param entity Clase que identifica el tipo de entity
     * @param id Llave primaria de la entidad
     * @return Objeto de tipo entity enviado como parametro
     * @throws GetEntityException
     */
    @Override
    public Object getRegistroById(Class entity, Integer id) throws GetEntityException {
        try {
            String nomEntity = entity.getSimpleName();
            query = em.createNamedQuery(nomEntity + ".findById");
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (IllegalStateException ise) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + entity + " id: " + id, ise);
            throw new GetEntityException(ise.getMessage(), ise);
        } catch (NoResultException nre) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + entity + " id: " + id, nre);
            throw new GetEntityException(nre.getMessage(), nre);
        } catch (NonUniqueResultException nure) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + entity + " id: " + id, nure);
            throw new GetEntityException(nure.getMessage(), nure);
        } catch (IllegalArgumentException iae) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + entity + " id: " + id, iae);
            throw new GetEntityException(iae.getMessage(), iae);
        }
    }

   @Override
    public Object getRegistroById(Class entity, Short id) throws GetEntityException {
        try {
            String nomEntity = entity.getSimpleName();
            query = em.createNamedQuery(nomEntity + ".findById");
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (IllegalStateException ise) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + entity + " id: " + id, ise);
            throw new GetEntityException(ise.getMessage(), ise);
        } catch (NoResultException nre) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + entity + " id: " + id, nre);
            throw new GetEntityException(nre.getMessage(), nre);
        } catch (NonUniqueResultException nure) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + entity + " id: " + id, nure);
            throw new GetEntityException(nure.getMessage(), nure);
        } catch (IllegalArgumentException iae) {
            logger.log(Level.SEVERE, "Error al obtener Registro: " + entity + " id: " + id, iae);
            throw new GetEntityException(iae.getMessage(), iae);
        }
    }
    
}
