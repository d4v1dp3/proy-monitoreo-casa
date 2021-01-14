/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.delegate;

import ipn.cic.sistmr.exception.MedidasException;
import ipn.cic.sistmr.exception.NoExistePacienteException;
import ipn.cic.web.sistmr.bean.vo.MedidasVO;
import javax.ejb.Local;
import javax.json.JsonObject;

/**
 *
 * @author iliaco
 */
@Local
public interface MedidasBDLocal {
 
    //definir un metodo que reciba el VO y lo persista lanzar una excepcion si no se puede hacer
    JsonObject guardarMedidas(MedidasVO med) throws MedidasException, NoExistePacienteException;
    
}
