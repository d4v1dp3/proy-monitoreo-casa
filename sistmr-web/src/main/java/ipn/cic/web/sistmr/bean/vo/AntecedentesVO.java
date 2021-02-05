/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.vo;

import java.io.Serializable;

/**
 *
 * @author marcos
 */
public class AntecedentesVO implements Serializable{
    
    private Boolean diabetes;
    private Boolean cancer;
    private Boolean asma;
    private Boolean vih;
    private Boolean has;
    private Boolean epoc;
    private Boolean embarazo;
    private Boolean artritis;
    private Boolean enfautoinmune;
    
    public AntecedentesVO() {}

    public AntecedentesVO(Boolean diabetes, Boolean cancer, Boolean asma, Boolean vih, Boolean has, Boolean epoc, Boolean embarazo, Boolean artritis, Boolean enfautoinmune) {
        this.diabetes = diabetes;
        this.cancer = cancer;
        this.asma = asma;
        this.vih = vih;
        this.has = has;
        this.epoc = epoc;
        this.embarazo = embarazo;
        this.artritis = artritis;
        this.enfautoinmune = enfautoinmune;
    }

    public Boolean getDiabetes() {
        return diabetes;
    }

    public void setDiabetes(Boolean diabetes) {
        this.diabetes = diabetes;
    }

    public Boolean getCancer() {
        return cancer;
    }

    public void setCancer(Boolean cancer) {
        this.cancer = cancer;
    }

    public Boolean getAsma() {
        return asma;
    }

    public void setAsma(Boolean asma) {
        this.asma = asma;
    }

    public Boolean getVih() {
        return vih;
    }

    public void setVih(Boolean vih) {
        this.vih = vih;
    }

    public Boolean getHas() {
        return has;
    }

    public void setHas(Boolean has) {
        this.has = has;
    }

    public Boolean getEpoc() {
        return epoc;
    }

    public void setEpoc(Boolean epoc) {
        this.epoc = epoc;
    }

    public Boolean getEmbarazo() {
        return embarazo;
    }

    public void setEmbarazo(Boolean embarazo) {
        this.embarazo = embarazo;
    }

    public Boolean getArtritis() {
        return artritis;
    }

    public void setArtritis(Boolean artritis) {
        this.artritis = artritis;
    }

    public Boolean getEnfautoinmune() {
        return enfautoinmune;
    }

    public void setEnfautoinmune(Boolean enfautoinmune) {
        this.enfautoinmune = enfautoinmune;
    }
    
}

