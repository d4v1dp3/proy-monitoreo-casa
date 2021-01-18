/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.vo;

import ipn.cic.sistmr.modelo.EntHospital;
import ipn.cic.sistmr.modelo.EntPacienteMedico;
import ipn.cic.sistmr.modelo.EntPersona;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author J.PEREZ
 */
public class MedicoVO implements Serializable{
    private String cedulaProf;
    private String email;
    private String celular;

    public MedicoVO() {
        cedulaProf = "";
        celular = "";
        email = "";
    }

    
    
    public MedicoVO(String cedulaProf, String celular, String email) {
        this.cedulaProf = cedulaProf;
        this.celular = celular;
        this.email = email;
    }

    public String getCedulaProf() {
        return cedulaProf;
    }

    public void setCedulaProf(String cedulaProf) {
        this.cedulaProf = cedulaProf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
    
}
