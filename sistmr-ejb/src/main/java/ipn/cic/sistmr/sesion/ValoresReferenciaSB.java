/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.NoExisteValoresRefException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.exception.ValoresReferenciaException;
import ipn.cic.sistmr.modelo.EntValoresReferencia;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author leoj_
 */
@Stateless
public class ValoresReferenciaSB extends BaseSB implements ValoresReferenciaSBLocal {
    private static final Logger logger = Logger.getLogger(ValoresReferenciaSB.class.getName());
    
    @Override
    public Boolean existenVref() throws ValoresReferenciaException {
        Query qry = em.createQuery("SELECT count(h) FROM EntValoresReferencia h");
        Long res = (Long)qry.getSingleResult();
        logger.log(Level.INFO,"Entero resultado = {0}",res);
        return res!=0;    
    }
    
    @Override
    public EntValoresReferencia guardaValoresReferencia(EntValoresReferencia vref) throws ValoresReferenciaException {
        try {
            return (EntValoresReferencia)saveEntity(vref);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE,"Error al intentar salvar valores de referencia : {0}", ex.getMessage());
            throw new ValoresReferenciaException("Error al salvar entidad en ValoresReferenciaSB",ex);
        }
    }

    @Override
    public EntValoresReferencia getValoresReferencia() throws ValoresReferenciaException{
        Query qry = em.createQuery("SELECT h FROM EntValoresReferencia h");
        List<EntValoresReferencia> res = qry.getResultList();
        
        if(res.size()==0){
            throw new ValoresReferenciaException("No hay valores de referencia!");
        }
        return res.get(0);
    }
    
    @Override
    public EntValoresReferencia updateValoresReferencia(EntValoresReferencia vref) throws UpdateEntityException{
        return (EntValoresReferencia)this.updateEntity(vref); 
    }
    
    @Override
    public EntValoresReferencia getValoresReferenciaId(Short idValRef) throws NoExisteValoresRefException{
        logger.log(Level.INFO,"Recuperando valores de referencia");
        query = em.createQuery("SELECT e FROM EntValoresReferencia e WHERE e.idValref = :idValRef");
        query.setParameter("idValRef", idValRef);
        try{
            EntValoresReferencia valoresRef = (EntValoresReferencia)query.getSingleResult();
            return valoresRef;
        }catch(Exception e){
            logger.log(Level.SEVERE,"Error al obtener valores de referencia: {0}",e.getMessage());
            throw new NoExisteValoresRefException("No es posible obtener los valores de referencia");
        }
    }
}

