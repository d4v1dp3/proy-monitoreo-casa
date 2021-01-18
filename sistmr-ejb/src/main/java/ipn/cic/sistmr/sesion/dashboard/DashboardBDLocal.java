/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion.dashboard;

import ipn.cic.sistmr.exception.NoExisteMedicionesException;
import ipn.cic.sistmr.exception.NoExistePacienteDashException;
import ipn.cic.sistmr.modelo.EntMedidas;
import ipn.cic.sistmr.modelo.EntPaciente;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author marcos
 */
@Local 
public interface DashboardBDLocal {
    public EntPaciente getPaciente(Long idPaciente) throws NoExistePacienteDashException;
    public List<EntMedidas> getListaMedidas(EntPaciente entPaciente) throws NoExisteMedicionesException;
}