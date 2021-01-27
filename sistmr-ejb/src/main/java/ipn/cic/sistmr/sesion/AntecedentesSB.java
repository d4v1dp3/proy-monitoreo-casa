/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.AntecedentesException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.modelo.EntAntecedentes;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author marcos
 */
@Stateless
public class AntecedentesSB extends BaseSB implements AntecedentesSBLocal {

    private static final Logger logger = Logger.getLogger(AntecedentesSB.class.getName());
    
    @Override
    public EntAntecedentes guardaAntecedentes(EntAntecedentes antecedentes) throws AntecedentesException{
        try{
            return (EntAntecedentes)saveEntity(antecedentes);
        }catch(SaveEntityException ex){
            logger.log(Level.SEVERE,"Error al intentar salvar entidad : {0}", ex.getMessage());
            throw new AntecedentesException("Error al salvar entidad en AntecedentesSB",ex);
        }
    }
}
