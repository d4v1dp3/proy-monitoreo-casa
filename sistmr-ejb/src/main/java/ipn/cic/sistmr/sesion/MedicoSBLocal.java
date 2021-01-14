/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPersona;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author iliac
 */
@Local
public interface MedicoSBLocal {
    public EntMedico saveMedico(EntMedico med) throws MedicoException;
    
    public EntMedico getMedico(EntPersona entPersona) throws MedicoException;
    
    public List<EntPaciente> getListaPaciente(EntMedico entMedico) throws MedicoException;
    
    public List<EntMedico> getMedicos() throws MedicoException;
    
    public EntMedico getMedicoDePaciente(EntPaciente entPaciente) throws MedicoException;
    
}
