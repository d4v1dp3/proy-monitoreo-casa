/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.CaretaException;
import ipn.cic.sistmr.exception.NoExisteCaretaException;
import ipn.cic.sistmr.exception.RemoveEntityException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.modelo.EntCareta;
import ipn.cic.sistmr.modelo.EntEstadocareta;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author J.PEREZ
 */

@Local
public interface CaretaSBLocal {
    EntCareta getCareta(long idCareta) throws NoExisteCaretaException;
    EntCareta guardaCareta(EntCareta careta) throws CaretaException;
    List<EntCareta> getCaretas() throws NoExisteCaretaException;
    boolean borrarCareta(EntCareta careta) throws RemoveEntityException;
    EntCareta updateCareta(EntCareta careta) throws UpdateEntityException;
    public EntEstadocareta getEstadoCareta(EntCareta Careta) throws NoExisteCaretaException;
}
