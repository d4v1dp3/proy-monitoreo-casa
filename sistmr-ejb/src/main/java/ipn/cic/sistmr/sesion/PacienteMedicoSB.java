/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.PacienteMedicoException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.modelo.EntPacienteMedico;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author iliaco
 */
@Stateless
public class PacienteMedicoSB extends BaseSB implements PacienteMedicoSBLocal {
    private static final Logger logger = Logger.getLogger(HospitalSB.class.getName());

    @Override
    public EntPacienteMedico guardaEntPacienteMedico(EntPacienteMedico pacmed) throws PacienteMedicoException {
        try {
            return (EntPacienteMedico)saveEntity(pacmed);
        } catch (SaveEntityException ex) {
            logger.log(Level.SEVERE,"Error al intentar salvar entidad : {0}", ex.getMessage());
            throw new PacienteMedicoException("Error al salvar entidad en PacienteMedicoSB",ex);
        }
    }
}
