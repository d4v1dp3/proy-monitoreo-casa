/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.CaretaHospitalException;
import ipn.cic.sistmr.modelo.EntCaretaHospital;
import javax.ejb.Local;

/**
 *
 * @author leoj_
 */
@Local
public interface CaretaHospitalSBLocal {
    EntCaretaHospital guardaCaretaHospital(EntCaretaHospital caretahospital) throws CaretaHospitalException;
}
