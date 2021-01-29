/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.AntecedentesException;
import ipn.cic.sistmr.modelo.EntAntecedentes;
import javax.ejb.Local;

/**
 *
 * @author marcos
 */
@Local
public interface AntecedentesSBLocal {
    EntAntecedentes guardaAntecedentes(EntAntecedentes antecedentes) throws AntecedentesException;
}
