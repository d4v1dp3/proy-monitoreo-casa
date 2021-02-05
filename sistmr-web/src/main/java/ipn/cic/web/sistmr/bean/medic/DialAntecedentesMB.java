/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.medic;

import ipn.cic.web.sistmr.bean.admon.GestionHospitalMB;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author J.Perez
 */
@Named(value="dialAntecedentesMB")
@ViewScoped
public class DialAntecedentesMB implements Serializable{

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(GestionHospitalMB.class.getName());
    
    private String pacienteNombre="";
    private String pacientePrimerAp="";
    private String pacienteSegundoAp="";
    
    private String diabetes = "";
    private String cancer= "";
    private String asma = "";
    private String vih = "";
    private String has = "";
    private String epoc = "";
    private String embarazo = "";
    private String artritis = "";
    private String enfautoinmune= "";

    public String getCancer() {
        return cancer;
    }

    public void setCancer(String cancer) {
        this.cancer = cancer;
    }

    public String getAsma() {
        return asma;
    }

    public void setAsma(String asma) {
        this.asma = asma;
    }

    public String getVih() {
        return vih;
    }

    public void setVih(String vih) {
        this.vih = vih;
    }

    public String getHas() {
        return has;
    }

    public void setHas(String has) {
        this.has = has;
    }

    public String getEpoc() {
        return epoc;
    }

    public void setEpoc(String epoc) {
        this.epoc = epoc;
    }

    public String getEmbarazo() {
        return embarazo;
    }

    public void setEmbarazo(String embarazo) {
        this.embarazo = embarazo;
    }

    public String getArtritis() {
        return artritis;
    }

    public void setArtritis(String artritis) {
        this.artritis = artritis;
    }

    public String getEnfautoinmune() {
        return enfautoinmune;
    }

    public void setEnfautoinmune(String enfautoinmune) {
        this.enfautoinmune = enfautoinmune;
    }
    
    public String getPacienteNombre() {
        return pacienteNombre;
    }

    public void setPacienteNombre(String pacienteNombre) {
        this.pacienteNombre = pacienteNombre;
    }

    public String getPacientePrimerAp() {
        return pacientePrimerAp;
    }

    public void setPacientePrimerAp(String pacientePrimerAp) {
        this.pacientePrimerAp = pacientePrimerAp;
    }

    public String getPacienteSegundoAp() {
        return pacienteSegundoAp;
    }

    public void setPacienteSegundoAp(String pacienteSegundoAp) {
        this.pacienteSegundoAp = pacienteSegundoAp;
    }
    
    public String getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(String diabetes) {
        this.diabetes = diabetes;
    }

    
}

