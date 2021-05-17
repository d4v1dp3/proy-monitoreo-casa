/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.NoExistePacienteMedicoException;
import ipn.cic.sistmr.exception.PacienteMedicoException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPacienteMedico;
import javax.ejb.Local;

/**
 *
 * @author iliaco
 */
@Local
public interface PacienteMedicoSBLocal {
    public EntPacienteMedico guardaEntPacienteMedico(EntPacienteMedico pacmed) throws PacienteMedicoException;
    public EntPacienteMedico updateEntPacienteMedico(EntPacienteMedico pacMed) throws UpdateEntityException;
    public EntPacienteMedico getEntPacienteMedico(EntPaciente idPaciente) throws NoExistePacienteMedicoException;
}
