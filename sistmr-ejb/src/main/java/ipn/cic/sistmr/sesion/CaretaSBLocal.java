/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.NoExisteCaretaException;
import ipn.cic.sistmr.modelo.EntCareta;
import javax.ejb.Local;

/**
 *
 * @author J.PEREZ
 */

@Local
public interface CaretaSBLocal {
    EntCareta getCareta(long idCareta) throws NoExisteCaretaException;
}
