/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.BitacoraException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.modelo.EntBitacora;
import ipn.cic.sistmr.modelo.EntEventobitacora;
import ipn.cic.sistmr.modelo.EntUsuario;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author J.Perez
 */
@Stateless
public class BitacoraSB extends BaseSB implements BitacoraSBLocal {

    private static final Logger logger = Logger.getLogger(BitacoraSB.class.getName());

    @Override
    public List<EntBitacora> getIngresosPacientes() throws BitacoraException {
        EntEventobitacora evento = new EntEventobitacora(1, "REGISTRO_PACIENTE");
        Query qry = em.createQuery("SELECT e from EntBitacora e WHERE e.idEvento = :idEvento ");
        qry.setParameter("idEvento", evento);
        List<EntBitacora> res = qry.getResultList();
        
        for(EntBitacora entevento: res){
            entevento.getFechaEntrada().getMonth();
            entevento.getIdEntrada();
            entevento.getIdEvento().getDescripcion();
        }

        return res;
    }

    @Override
    public List<EntBitacora> getIngresosPacientesPorMes(Date Mes) throws BitacoraException {
        EntEventobitacora evento = new EntEventobitacora(1, "REGISTRO_PACIENTE");
        Query qry = em.createQuery("SELECT e from EntBitacora e WHERE e.fechaEntrada LIKE :mes" );

        qry.setParameter("mes", Mes);
        
        List<EntBitacora> res = qry.getResultList();

        return res;
    }

    @Override
    public List<EntBitacora> getAltasPacientes() throws BitacoraException {
        EntEventobitacora evento = new EntEventobitacora(3, "ALTA_PACIENTE");
        Query qry = em.createQuery("SELECT e from EntBitacora e WHERE e.idEvento = :idEvento ");
        qry.setParameter("idEvento", evento);
        List<EntBitacora> res = qry.getResultList();

        return res;
    }
    
    @Override
    public List<EntBitacora> getDecesosPacientes() throws BitacoraException {
        EntEventobitacora evento = new EntEventobitacora(4, "DECESO_PACIENTE");
        Query qry = em.createQuery("SELECT e from EntBitacora e WHERE e.idEvento = :idEvento ");
        qry.setParameter("idEvento", evento);
        List<EntBitacora> res = qry.getResultList();

        return res;
    }

    @Override
    public EntBitacora eventoRegistroDePaciente(Date fechaEntrada, EntUsuario idUsuario) throws BitacoraException {
        EntEventobitacora evento = new EntEventobitacora(1, "REGISTRO_PACIENTE");
        
        EntBitacora eventoBitacora = new EntBitacora();
        eventoBitacora.setFechaEntrada(fechaEntrada);
        eventoBitacora.setIdUsuario(idUsuario);
        eventoBitacora.setIdEvento(evento);
        
        try {
            return (EntBitacora)saveEntity(eventoBitacora);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE,"Error al registrar evento en Bitacora. : {0}", ex.getMessage());
            throw new BitacoraException("Error al registrar evento en Bitacora.",ex);
        }
    }
    
    @Override
    public EntBitacora eventoAltaDePaciente(Date fechaEntrada, EntUsuario idUsuario) throws BitacoraException {
        EntEventobitacora evento = new EntEventobitacora(3, "ALTA_PACIENTE");
        
        EntBitacora eventoBitacora = new EntBitacora();
        eventoBitacora.setFechaEntrada(fechaEntrada);
        eventoBitacora.setIdUsuario(idUsuario);
        eventoBitacora.setIdEvento(evento);
        
        try {
            return (EntBitacora)saveEntity(eventoBitacora);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE,"Error al registrar evento en Bitacora. : {0}", ex.getMessage());
            throw new BitacoraException("Error al registrar evento en Bitacora.",ex);
        }
    }
    
    @Override
    public EntBitacora eventoDecesoDePaciente(Date fechaEntrada, EntUsuario idUsuario) throws BitacoraException {
        EntEventobitacora evento = new EntEventobitacora(4, "DECESO_PACIENTE");
        
        EntBitacora eventoBitacora = new EntBitacora();
        eventoBitacora.setFechaEntrada(fechaEntrada);
        eventoBitacora.setIdUsuario(idUsuario);
        eventoBitacora.setIdEvento(evento);
        
        try {
            return (EntBitacora)saveEntity(eventoBitacora);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE,"Error al registrar evento en Bitacora. : {0}", ex.getMessage());
            throw new BitacoraException("Error al registrar evento en Bitacora.",ex);
        }
    }
}
