/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.GeneroException;
import ipn.cic.sistmr.modelo.EntGenero;
import javax.ejb.Local;

/**
 *
 * @author iliac
 */
@Local
public interface GeneroSBLocal {
    EntGenero getGeneroID (Short idGenero) throws GeneroException;
}
