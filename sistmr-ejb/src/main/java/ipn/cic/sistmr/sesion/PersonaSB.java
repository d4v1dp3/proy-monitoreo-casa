/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.modelo.EntPersona;
import javax.ejb.Stateless;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Stateless
public class PersonaSB extends BaseSB implements PersonaSBLocal {

    @Override
    public EntPersona savePersona(EntPersona persona) throws SaveEntityException {
        persona = (EntPersona) this.saveEntity(persona);
        return persona;
    }
    
    
}
