/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.NoExistePacienteException;
import ipn.cic.sistmr.exception.PacienteException;
import ipn.cic.sistmr.exception.UsuarioException;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntPersona;
import ipn.cic.sistmr.modelo.EntUsuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Local
public interface PacienteSBLocal {
    
    EntPaciente guardaPaciente(EntPaciente paciente) throws PacienteException;
    EntPaciente getPaciente(long idPaciente) throws NoExistePacienteException;
    public EntPaciente getPaciente(EntPersona Persona) throws NoExistePacienteException;
    public List<EntPaciente> getPacientes(EntMedico entMedico) throws PacienteException;
    
}
