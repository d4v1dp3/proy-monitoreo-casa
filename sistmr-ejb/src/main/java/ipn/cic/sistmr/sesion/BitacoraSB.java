/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.BitacoraException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.exception.UpdateEntityException;
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
        EntEventobitacora evento = new EntEventobitacora(2, "REGISTRA_PACIENTE");
        Query qry = em.createQuery("SELECT e from EntBitacora e WHERE e.idEvento = :idEvento ");
        qry.setParameter("idEvento", evento);
        List<EntBitacora> res = qry.getResultList();

        for (EntBitacora entevento : res) {
            entevento.getFechaEntrada().getMonth();
            entevento.getIdEntrada();
            entevento.getIdEvento().getDescripcion();
        }

        return res;
    }

    @Override
    public List<EntBitacora> getIngresosPacientesPorMes(Date Mes) throws BitacoraException {
        EntEventobitacora evento = new EntEventobitacora(2, "REGISTRA_PACIENTE");

        Query qry = em.createQuery("SELECT e from EntBitacora e WHERE e.fechaEntrada LIKE :mes");
        qry.setParameter("mes", Mes);

        List<EntBitacora> res = qry.getResultList();

        return res;
    }

    @Override
    public List<EntBitacora> getAltasPacientes() throws BitacoraException {
        EntEventobitacora evento = new EntEventobitacora(8, "PACIENTE_ALTA");
        Query qry = em.createQuery("SELECT e from EntBitacora e WHERE e.idEvento = :idEvento ");
        qry.setParameter("idEvento", evento);
        List<EntBitacora> res = qry.getResultList();

        return res;
    }

    @Override
    public List<EntBitacora> getDecesosPacientes() throws BitacoraException {
        EntEventobitacora evento = new EntEventobitacora(9, "PACIENTE_DECESO");
        Query qry = em.createQuery("SELECT e from EntBitacora e WHERE e.idEvento = :idEvento ");
        qry.setParameter("idEvento", evento);
        List<EntBitacora> res = qry.getResultList();

        return res;
    }

    @Override
    public EntBitacora eventoRegistroDePaciente(Date fechaEntrada, EntUsuario idUsuario, EntUsuario idUsuario2) throws BitacoraException {
        EntEventobitacora registraPaciente = new EntEventobitacora(2, "REGISTRA_PACIENTE");
        EntEventobitacora pacienteRegistrado = new EntEventobitacora(6, "PACIENTE_REGISTRADO");

        EntBitacora eventoBitacora1 = new EntBitacora();
        eventoBitacora1.setFechaEntrada(fechaEntrada);
        eventoBitacora1.setIdUsuario(idUsuario);
        eventoBitacora1.setIdEvento(registraPaciente);

        EntBitacora eventoBitacora2 = new EntBitacora();
        eventoBitacora2.setFechaEntrada(fechaEntrada);
        eventoBitacora2.setIdUsuario(idUsuario2);
        eventoBitacora2.setIdEvento(pacienteRegistrado);

        try {
            saveEntity(eventoBitacora1);
            return (EntBitacora) saveEntity(eventoBitacora2);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE, "Error al registrar evento en Bitacora. : {0}", ex.getMessage());
            throw new BitacoraException("Error al registrar evento en Bitacora.", ex);
        }
    }

    @Override
    public EntBitacora eventoAltaDePaciente(Date fechaEntrada, EntUsuario usrMedico, EntUsuario usrPaciente) throws BitacoraException {
        EntEventobitacora actualizaEstado = new EntEventobitacora(5, "ACTUALIZA_ESTADO_PACIENTE");
        EntEventobitacora estadoAlta = new EntEventobitacora(8, "PACIENTE_ALTA");
        EntEventobitacora estadoDeceso = new EntEventobitacora(9, "PACIENTE_DECESO");

        EntBitacora eventoBitacora1 = new EntBitacora();
        eventoBitacora1.setFechaEntrada(fechaEntrada);
        eventoBitacora1.setIdUsuario(usrMedico);
        eventoBitacora1.setIdEvento(actualizaEstado);

        try {
            saveEntity(eventoBitacora1);
            logger.log(Level.INFO, "ACTUALIZA_ESTADO_PACIENTE. : {0}", usrMedico.getEmail());
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE, "Error al registrar evento en Bitacora. : {0}", ex.getMessage());
            throw new BitacoraException("Error al registrar evento en Bitacora.", ex);
        }

        EntBitacora eventoBitacora2;
        eventoBitacora2 = getEventoEstadoPaciente(usrPaciente, estadoDeceso);
        
        if (eventoBitacora2 != null) {//Actualizar estado
            eventoBitacora2.setFechaEntrada(fechaEntrada);
            eventoBitacora2.setIdUsuario(usrPaciente);
            eventoBitacora2.setIdEvento(estadoAlta);
            try {
                logger.log(Level.INFO, "ACTUALIZA: PACIENTE_ALTA");
                return (EntBitacora) updateEntity(eventoBitacora2);                
            } catch (UpdateEntityException ex) {
                logger.log(Level.SEVERE, "Error al registrar evento en Bitacora. : {0}", ex.getMessage());
                throw new BitacoraException("Error al registrar evento en Bitacora.", ex);
            }
        } else {
            eventoBitacora2 = new EntBitacora();
            eventoBitacora2.setFechaEntrada(fechaEntrada);
            eventoBitacora2.setIdUsuario(usrPaciente);
            eventoBitacora2.setIdEvento(estadoAlta);

            try {
                logger.log(Level.INFO, "REGISTRA: PACIENTE_ALTA");
                return (EntBitacora) saveEntity(eventoBitacora2);
            } catch (SaveEntityException ex) {
                logger.log(Level.SEVERE, "Error al registrar evento en Bitacora. : {0}", ex.getMessage());
                throw new BitacoraException("Error al registrar evento en Bitacora.", ex);
            }
        }
    }

    @Override
    public EntBitacora eventoDecesoDePaciente(Date fechaEntrada, EntUsuario usrMedico, EntUsuario usrPaciente) throws BitacoraException {
        EntEventobitacora actualizaEstado = new EntEventobitacora(5, "ACTUALIZA_ESTADO_PACIENTE");
        EntEventobitacora estadoAlta = new EntEventobitacora(8, "PACIENTE_ALTA");
        EntEventobitacora estadoDeceso = new EntEventobitacora(9, "PACIENTE_DECESO");

        EntBitacora eventoBitacora1 = new EntBitacora();
        eventoBitacora1.setFechaEntrada(fechaEntrada);
        eventoBitacora1.setIdUsuario(usrMedico);
        eventoBitacora1.setIdEvento(actualizaEstado);

        try {
            saveEntity(eventoBitacora1);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE, "Error al registrar evento1 en Bitacora. : {0}", ex.getMessage());
            throw new BitacoraException("Error al registrar evento en Bitacora.", ex);
        }

        EntBitacora eventoBitacora2;
        eventoBitacora2 = getEventoEstadoPaciente(usrPaciente, estadoAlta);
        
        if (eventoBitacora2 != null) {//Actualizar estado
            eventoBitacora2.setFechaEntrada(fechaEntrada);
            eventoBitacora2.setIdUsuario(usrPaciente);
            eventoBitacora2.setIdEvento(estadoDeceso);
            try {
                return (EntBitacora) updateEntity(eventoBitacora2);
            } catch (UpdateEntityException ex) {
                logger.log(Level.SEVERE, "Error al registrar evento2 en Bitacora. : {0}", ex.getMessage());
                throw new BitacoraException("Error al registrar evento2 en Bitacora.", ex);
            }
        } else {
            eventoBitacora2 = new EntBitacora();
            eventoBitacora2.setFechaEntrada(fechaEntrada);
            eventoBitacora2.setIdUsuario(usrPaciente);
            eventoBitacora2.setIdEvento(estadoDeceso);

            try {
                return (EntBitacora) saveEntity(eventoBitacora2);
            } catch (SaveEntityException ex) {
                logger.log(Level.SEVERE, "Error al registrar evento2 en Bitacora. : {0}", ex.getMessage());
                throw new BitacoraException("Error al registrar evento2 en Bitacora.", ex);
            }
        }
    }

    @Override
    public EntBitacora getEventoEstadoPaciente(EntUsuario paciente, EntEventobitacora evento) {
        Query qry = em.createQuery("SELECT e from EntBitacora e WHERE e.idUsuario = :paciente");
        qry.setParameter("paciente", paciente);
        List<EntBitacora> res = qry.getResultList();

        for (EntBitacora b : res) {
            if (b.getIdEvento().getIdEvento() == evento.getIdEvento()) {
                return b;
            }
        }
        return null;
    }
}