/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.EstadoPacienteException;
import ipn.cic.sistmr.modelo.EntEstadopaciente;
import javax.ejb.Local;

/**
 *
 * @author iliaco
 */
@Local
public interface EstadoPacienteSBLocal {
   EntEstadopaciente getEstadoPaciente(Integer idestado) throws EstadoPacienteException;     
}
