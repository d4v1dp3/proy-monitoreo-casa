/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPacienteMedico;
import ipn.cic.sistmr.modelo.EntPersona;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 * SessionBean para manejo de entidades EntMedico, encargada de la persistencia
 * actualización y eliminación de registros.
 * @author Iliac Huerta Trujillo
 */
@Stateless
public class MedicoSB extends BaseSB implements MedicoSBLocal {
    private static final Logger logger = Logger.getLogger(MedicoSB.class.getName());
    
    @Override
    public EntMedico saveMedico(EntMedico med) throws MedicoException {
        try {
            med = (EntMedico) saveEntity(med);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE,"Error al persistir entidadMedico : {0}",ex.getMessage());
            throw new MedicoException("Imposible persistir Entidad Médico",ex);
        }
        return med;
    }
    
    
    @Override
    public EntMedico getMedico(EntPersona entPersona) throws MedicoException {
        logger.log(Level.INFO, "MedicoSB: Entra a recuperar medico.");
        
        query = em.createQuery("SELECT e From EntMedico e WHERE e.idPersona = :idPersona")
                .setParameter("idPersona", entPersona);
//        logger.log(Level.INFO, "PacienteSB: consulta ejecutada.");
        EntMedico res = new EntMedico();
        res = (EntMedico)query.getSingleResult();

        logger.log(Level.INFO, "MedicoSB: Medico recuperado. {0}", res.getCedulaProf());
        return res;
    }
    
    @Override
    public EntMedico getMedicoDePaciente(EntPaciente entPaciente) throws MedicoException {
        logger.log(Level.INFO, "MedicoSB: Entra a recuperar medico de un paciente.");
        
        query = em.createQuery("SELECT e From EntPacienteMedico e WHERE e.entPacienteMedicoPK.idPaciente = :idPaciente")
                .setParameter("idPaciente", entPaciente.getIdPaciente());
        EntPacienteMedico res = new EntPacienteMedico();
        res = (EntPacienteMedico)query.getSingleResult();
        EntMedico med = res.getEntMedico();
        logger.log(Level.INFO, "MedicoSB: Medico recuperado. {0}", med.getCedulaProf());
        
        med.getEmail();
        med.getIdPersona().getNombre();
        med.getIdPersona().getPrimerApellido();
        med.getIdPersona().getSegundoApellido();
        
        return med;
    }

    @Override
    public List<EntPaciente> getListaPaciente(EntMedico entMedico) throws MedicoException {
         Query qry = em.createQuery("SELECT e from EntMedico e LEFT JOIN FETCH e.entPacienteMedicoList "
                                  + "WHERE e = :entMedico ");
         qry.setParameter("entMedico", entMedico);
         entMedico = (EntMedico) qry.getSingleResult();
         List<EntPaciente> lista = new ArrayList<>();
         logger.log(Level.INFO,"Tamaño de Lista : {0}",entMedico.getEntPacienteMedicoList().size());
         for(int i = 0; i < entMedico.getEntPacienteMedicoList().size();i++){
             EntPaciente paciente = entMedico.getEntPacienteMedicoList().get(i).getEntPaciente();
             paciente.getIdPaciente();
             paciente.getIdPersona().getNombre();
             paciente.getIdCareta().getIdCareta();
             paciente.getIdEstadopaciente().getDescripcion();
             logger.log(Level.INFO,"Datos Paciente : {0}",paciente.getIdPersona().getNombre());
             lista.add(paciente);
             
         }
         return lista;
    }
    
    @Override
    public List<EntMedico> getMedicos() throws MedicoException {
        try{
            query = em.createQuery("SELECT med From EntMedico med LEFT JOIN FETCH  med.idPersona p "
                    + "ORDER BY p.primerApellido, p.segundoApellido, p.nombre");
            return query.getResultList();
            
//            
//            List<EntMedico> lista = new ArrayList<>();
//            lista = query.getResultList();
//
//            logger.log(Level.INFO, "Tamaño de Lista : {0}", lista.size());
//            for (int i = 0; i < lista.size(); i++) {
//                EntMedico medico = lista.get(i);
//                medico.getIdMedico();
//                medico.getCedulaProf();
//                medico.getEntPacienteMedicoList();
//                medico.getIdPersona().getNombre();
//                medico.getIdPersona().getPrimerApellido();
//                medico.getIdPersona().getSegundoApellido();
//                //logger.log(Level.INFO,"Datos Paciente : {0}",medico.getIdPersona().getNombre());
//                lista.add(medico);
//
//            }
//            return lista;
        }catch(Exception e){
            logger.log(Level.SEVERE,"Error al obtener la lista de medicos: {0}",e.getMessage());
            throw new MedicoException("No esposible obtener la lista de medicos",e);
        }
                    
    }

    @Override
    public EntMedico getMedico(Integer idMedico) throws MedicoException {
            Query qry = em.createQuery("SELECT e From EntMedico e WHERE e.idMedico = :idMedico");
        qry.setParameter("idMedico", idMedico);
        EntMedico res = (EntMedico)qry.getSingleResult();
        res.getCedulaProf();
        res.getIdMedico();
        return res;
    }
}
