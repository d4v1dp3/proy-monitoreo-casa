/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.NoExistePersonaException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.modelo.EntPersona;
import javax.ejb.Local;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Local
public interface PersonaSBLocal {
    EntPersona savePersona(EntPersona persona)throws SaveEntityException;
    public EntPersona updatePersona(EntPersona persona) throws UpdateEntityException;
    EntPersona getPersonaDePaciente(Long idPaciente);
    EntPersona getEntPersona(EntPersona Persona) throws NoExistePersonaException;
}
