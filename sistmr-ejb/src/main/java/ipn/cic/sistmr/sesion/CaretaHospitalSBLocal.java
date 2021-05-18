/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.CaretaHospitalException;
import ipn.cic.sistmr.exception.RemoveEntityException;
import ipn.cic.sistmr.modelo.EntCareta;
import ipn.cic.sistmr.modelo.EntCaretaHospital;
import ipn.cic.sistmr.modelo.EntHospital;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author leoj_
 */
@Local
public interface CaretaHospitalSBLocal {
    EntCaretaHospital guardaCaretaHospital(EntCaretaHospital caretahospital) throws CaretaHospitalException;
    EntCaretaHospital getCaretaHospital(EntCareta careta) throws CaretaHospitalException;
    List<EntCaretaHospital> getCaretasAsignadas() throws CaretaHospitalException;
    List<EntCaretaHospital> getCaretasDisponibles() throws CaretaHospitalException;
    public List<EntCaretaHospital> getCaretasAveriadas() throws CaretaHospitalException;
    List<EntCaretaHospital> getCaretasDisponibles(EntHospital entHospital) throws CaretaHospitalException;
    boolean borrarCaretaHospital(EntCaretaHospital caretahospital) throws RemoveEntityException;
    
}
