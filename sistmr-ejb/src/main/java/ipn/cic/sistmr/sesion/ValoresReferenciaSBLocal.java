/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.NoExisteValoresRefException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.exception.ValoresReferenciaException;
import ipn.cic.sistmr.modelo.EntValoresReferencia;
import javax.ejb.Local;

/**
 *
 * @author J.Perez
 */
@Local
public interface ValoresReferenciaSBLocal {
    Boolean existenVref() throws ValoresReferenciaException;
    EntValoresReferencia guardaValoresReferencia(EntValoresReferencia vref) throws ValoresReferenciaException;
    EntValoresReferencia getValoresReferencia() throws ValoresReferenciaException;
    EntValoresReferencia updateValoresReferencia(EntValoresReferencia vref) throws UpdateEntityException;
    EntValoresReferencia getValoresReferenciaId(Short idValRef) throws NoExisteValoresRefException;
}
