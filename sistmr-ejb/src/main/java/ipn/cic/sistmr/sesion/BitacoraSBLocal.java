/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.BitacoraException;
import ipn.cic.sistmr.modelo.EntBitacora;
import ipn.cic.sistmr.modelo.EntEventobitacora;
import ipn.cic.sistmr.modelo.EntUsuario;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author J.Perez
 */
@Local
public interface BitacoraSBLocal {
    public List<EntBitacora> getIngresosPacientes() throws BitacoraException;
    public List<EntBitacora> getIngresosPacientesPorMes(Date Mes) throws BitacoraException;
    public List<EntBitacora> getAltasPacientes() throws BitacoraException;
    public List<EntBitacora> getDecesosPacientes() throws BitacoraException;
    public EntBitacora eventoRegistroDePaciente(Date fechaEntrada, EntUsuario idUsuario, EntUsuario idUsuario2) throws BitacoraException;
    public EntBitacora eventoAltaDePaciente(Date fechaEntrada, EntUsuario usrMedico, EntUsuario usrPaciente) throws BitacoraException;
    public EntBitacora eventoDecesoDePaciente(Date fechaEntrada, EntUsuario usrMedico, EntUsuario usrPaciente) throws BitacoraException;
    public EntBitacora getEventoEstadoPaciente(EntUsuario paciente, EntEventobitacora evento);
}