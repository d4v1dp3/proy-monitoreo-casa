/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.AntecedentesException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.modelo.EntAntecedentes;
import ipn.cic.sistmr.modelo.EntPaciente;
import javax.ejb.Local;

/**
 *
 * @author marcos
 */
@Local
public interface AntecedentesSBLocal {
    EntAntecedentes guardaAntecedentes(EntAntecedentes antecedentes) throws AntecedentesException;
    public EntAntecedentes getAntecedentesPac(EntPaciente paciente) throws AntecedentesException;
    public EntAntecedentes actualizarAntecedentes(EntAntecedentes antecedentes) throws UpdateEntityException;
}
