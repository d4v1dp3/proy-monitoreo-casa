/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.delegate;

import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.web.sistmr.bean.vo.MedicoVO;
import ipn.cic.web.sistmr.bean.vo.PersonaVO;
import ipn.cic.web.sistmr.bean.vo.UsuarioVO;
import javax.ejb.Local;

/**
 *
 * @author iliaco
 */
@Local
public interface GestionMedicoBDLocal {
    public EntMedico guardarMedicoNuevo(MedicoVO medico, PersonaVO persona, 
                                        UsuarioVO usuario) throws MedicoException;
}
