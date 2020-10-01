/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.modelo.EntHospital;
import ipn.cic.sistmr.util.Constantes;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Named(value="gestionHospitalMB")
public class GestionHospitalMB implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(GestionHospitalMB.class.getName());
    
    private String nomSistema;
    private EntHospital hospEnt;
    
    @PostConstruct
    public void iniciaMB(){
        nomSistema = Constantes.getInstance().getString("NOMBRE_SISTEMA");
    }
    
    
    /**
     * @return the nomSistema
     */
    public String getNomSistema() {
        return nomSistema;
    }

    /**
     * @param nomSistema the nomSistema to set
     */
    public void setNomSistema(String nomSistema) {
        this.nomSistema = nomSistema;
    }
    
    
    /**
     * @return the hospEnt
     */
    public EntHospital getHospEnt() {
        return hospEnt;
    }

    /**
     * @param hospEnt the hospEnt to set
     */
    public void setHospEnt(EntHospital hospEnt) {
        this.hospEnt = hospEnt;
    }
    
}
