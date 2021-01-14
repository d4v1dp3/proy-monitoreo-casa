/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Entity
@Table(name = "RM_ANTECEDENTES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntAntecedentes.findAll", query = "SELECT e FROM EntAntecedentes e"),
    @NamedQuery(name = "EntAntecedentes.findByDiabetes", query = "SELECT e FROM EntAntecedentes e WHERE e.diabetes = :diabetes"),
    @NamedQuery(name = "EntAntecedentes.findByCancer", query = "SELECT e FROM EntAntecedentes e WHERE e.cancer = :cancer"),
    @NamedQuery(name = "EntAntecedentes.findByIdPaciente", query = "SELECT e FROM EntAntecedentes e WHERE e.idPaciente = :idPaciente"),
    @NamedQuery(name = "EntAntecedentes.findByAsma", query = "SELECT e FROM EntAntecedentes e WHERE e.asma = :asma"),
    @NamedQuery(name = "EntAntecedentes.findByVih", query = "SELECT e FROM EntAntecedentes e WHERE e.vih = :vih"),
    @NamedQuery(name = "EntAntecedentes.findByHas", query = "SELECT e FROM EntAntecedentes e WHERE e.has = :has"),
    @NamedQuery(name = "EntAntecedentes.findByEpoc", query = "SELECT e FROM EntAntecedentes e WHERE e.epoc = :epoc"),
    @NamedQuery(name = "EntAntecedentes.findByEmbarazo", query = "SELECT e FROM EntAntecedentes e WHERE e.embarazo = :embarazo"),
    @NamedQuery(name = "EntAntecedentes.findByArtritis", query = "SELECT e FROM EntAntecedentes e WHERE e.artritis = :artritis"),
    @NamedQuery(name = "EntAntecedentes.findByEnfautoinmune", query = "SELECT e FROM EntAntecedentes e WHERE e.enfautoinmune = :enfautoinmune"),
    @NamedQuery(name = "EntAntecedentes.findByFecha", query = "SELECT e FROM EntAntecedentes e WHERE e.fecha = :fecha")})
public class EntAntecedentes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "DIABETES")
    private Boolean diabetes;
    @Column(name = "CANCER")
    private Boolean cancer;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PACIENTE")
    private Long idPaciente;
    @Column(name = "ASMA")
    private Boolean asma;
    @Column(name = "VIH")
    private Boolean vih;
    @Column(name = "HAS")
    private Boolean has;
    @Column(name = "EPOC")
    private Boolean epoc;
    @Column(name = "EMBARAZO")
    private Boolean embarazo;
    @Column(name = "ARTRITIS")
    private Boolean artritis;
    @Column(name = "ENFAUTOINMUNE")
    private Boolean enfautoinmune;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID_PACIENTE", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private EntPaciente entPaciente;

    public EntAntecedentes() {
    }

    public EntAntecedentes(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public EntAntecedentes(Long idPaciente, Date fecha) {
        this.idPaciente = idPaciente;
        this.fecha = fecha;
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

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EntPaciente getEntPaciente() {
        return entPaciente;
    }

    public void setEntPaciente(EntPaciente entPaciente) {
        this.entPaciente = entPaciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPaciente != null ? idPaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntAntecedentes)) {
            return false;
        }
        EntAntecedentes other = (EntAntecedentes) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmhospital.modelo.EntAntecedentes[ idPaciente=" + idPaciente + " ]";
    }
    
}
