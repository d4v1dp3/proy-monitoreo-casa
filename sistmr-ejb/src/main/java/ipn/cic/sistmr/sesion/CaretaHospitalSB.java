/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.CaretaHospitalException;
import ipn.cic.sistmr.exception.RemoveEntityException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.modelo.EntCareta;
import ipn.cic.sistmr.modelo.EntCaretaHospital;
import ipn.cic.sistmr.modelo.EntHospital;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author J.Perez
 */
@Stateless
public class CaretaHospitalSB extends BaseSB implements CaretaHospitalSBLocal {
    private static final Logger logger = Logger.getLogger(HospitalSB.class.getName());

    @Override
    public EntCaretaHospital guardaCaretaHospital(EntCaretaHospital caretahospital) throws CaretaHospitalException {
        try {
            return (EntCaretaHospital)saveEntity(caretahospital);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE,"Error al intentar salvar entidad : {0}", ex.getMessage());
            throw new CaretaHospitalException("Error al salvar entidad en CaretaHospitalSB",ex);
        }
    }
    
    @Override
    public EntCaretaHospital getCaretaHospital(EntCareta careta) throws CaretaHospitalException {          
        Query qry = em.createQuery("SELECT e FROM EntCaretaHospital e WHERE e.entCareta = :entCareta");
        qry.setParameter("entCareta", careta);
        try{
            EntCaretaHospital res = (EntCaretaHospital)qry.getSingleResult();
            return res;
        }catch(NoResultException ex){
            logger.log(Level.SEVERE, "La consulta no obtuvo resultados");
            throw new CaretaHospitalException("No se encontro caretahospital SB.");
        }
    }
    
    @Override
    public List<EntCaretaHospital> getCaretasAsignadas() throws CaretaHospitalException {
        Query qry = em.createQuery("SELECT e FROM EntCaretaHospital e");
        List<EntCaretaHospital> res = qry.getResultList();

        Query qry2 = em.createQuery("SELECT f.idCareta FROM EntPaciente f");
        List<EntCareta> res2 = qry2.getResultList();

        List<EntCaretaHospital> resp = new ArrayList();

        for (EntCaretaHospital ent : res) {
            for (EntCareta car : res2) {
                if (Objects.equals(ent.getEntCareta().getIdCareta(), car.getIdCareta())) {
                    ent.getEntCareta().getIdCareta();
                    ent.getEntCareta().getNoSerie();
                    ent.getEntCareta().getFechaManufactura();
                    ent.getEntHospital().getIdHospital();
                    ent.getEntHospital().getNombre();
                    ent.getFechaAsignacion();
                    resp.add(ent);
                    break;
                }
            }
        }
        if (resp.isEmpty()) {
            throw new CaretaHospitalException("Relacion sin registros.");
        }
        return resp;
    }
    
    @Override
    public List<EntCaretaHospital> getCaretasNoAsignadas() throws CaretaHospitalException {
        Query qry = em.createQuery("SELECT e FROM EntCaretaHospital e");
        List<EntCaretaHospital> res = qry.getResultList();
        List<EntCaretaHospital> res2 = getCaretasAsignadas();
        res.removeAll(res2);

        List<EntCaretaHospital> resp = new ArrayList();
        for (EntCaretaHospital ent : res) {
            ent.getEntCareta().getIdCareta();
            ent.getEntCareta().getNoSerie();
            ent.getEntCareta().getFechaManufactura();
            ent.getEntHospital().getIdHospital();
            ent.getEntHospital().getNombre();
            ent.getFechaAsignacion();
            resp.add(ent);
        }
        return resp;
    }
    
    @Override
    public List<EntCaretaHospital> getCaretasNoAsignadas(EntHospital entHospital) throws CaretaHospitalException {        
        Query qry = em.createQuery("SELECT e FROM EntCaretaHospital e WHERE e.entHospital = :entHospital");
        qry.setParameter("entHospital", entHospital);
        
        Query qry2 = em.createQuery("SELECT f.idCareta FROM EntPaciente f");
        List<EntCareta> res2 = qry2.getResultList();
        
        List<EntCaretaHospital> res = qry.getResultList(); 
        
        List<EntCaretaHospital> resp = new ArrayList();  
        for (EntCaretaHospital ent : res) {
            for (EntCareta car : res2) {
                if (!Objects.equals(car.getIdCareta(), ent.getEntCareta().getIdCareta())) {
                    ent.getEntCareta().getIdCareta();
                    ent.getEntCareta().getNoSerie();
                    ent.getEntCareta().getFechaManufactura();
                    ent.getEntHospital().getIdHospital();
                    ent.getEntHospital().getNombre();
                    ent.getFechaAsignacion();
                    resp.add(ent);
                    break;
                }
            }
        }
        return resp;
    }
    
    @Override
    public boolean borrarCaretaHospital(EntCaretaHospital caretahospital) throws RemoveEntityException {  
        return removeEntity(caretahospital);
    }
}
