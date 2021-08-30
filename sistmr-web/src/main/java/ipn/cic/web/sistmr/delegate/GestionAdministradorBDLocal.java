/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.delegate;

import ipn.cic.sistmr.exception.IDUsuarioException;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.web.sistmr.bean.vo.PersonaVO;
import ipn.cic.web.sistmr.bean.vo.UsuarioVO;
import javax.ejb.Local;

/**
 *
 * @author marcos
 */
@Local
public interface GestionAdministradorBDLocal {
    public EntUsuario guardarAdministradorNuevo(PersonaVO persona, UsuarioVO usuario) throws IDUsuarioException;
}
