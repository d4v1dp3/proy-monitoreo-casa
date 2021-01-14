/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Entity
@Table(name = "MH_SINTOMAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntSintomas.findAll", query = "SELECT e FROM EntSintomas e"),
    @NamedQuery(name = "EntSintomas.findByFecha", query = "SELECT e FROM EntSintomas e WHERE e.entSintomasPK.fecha = :fecha"),
    @NamedQuery(name = "EntSintomas.findByIdPaciente", query = "SELECT e FROM EntSintomas e WHERE e.entSintomasPK.idPaciente = :idPaciente"),
    @NamedQuery(name = "EntSintomas.findByTos", query = "SELECT e FROM EntSintomas e WHERE e.tos = :tos"),
    @NamedQuery(name = "EntSintomas.findByCefalea", query = "SELECT e FROM EntSintomas e WHERE e.cefalea = :cefalea"),
    @NamedQuery(name = "EntSintomas.findByDolorAbdominal", query = "SELECT e FROM EntSintomas e WHERE e.dolorAbdominal = :dolorAbdominal"),
    @NamedQuery(name = "EntSintomas.findByDolorToracico", query = "SELECT e FROM EntSintomas e WHERE e.dolorToracico = :dolorToracico"),
    @NamedQuery(name = "EntSintomas.findByVomito", query = "SELECT e FROM EntSintomas e WHERE e.vomito = :vomito"),
    @NamedQuery(name = "EntSintomas.findByIrritable", query = "SELECT e FROM EntSintomas e WHERE e.irritable = :irritable"),
    @NamedQuery(name = "EntSintomas.findByPerdidaOlfatoGusto", query = "SELECT e FROM EntSintomas e WHERE e.perdidaOlfatoGusto = :perdidaOlfatoGusto"),
    @NamedQuery(name = "EntSintomas.findByDiarrea", query = "SELECT e FROM EntSintomas e WHERE e.diarrea = :diarrea"),
    @NamedQuery(name = "EntSintomas.findByOdinofagia", query = "SELECT e FROM EntSintomas e WHERE e.odinofagia = :odinofagia"),
    @NamedQuery(name = "EntSintomas.findByRinorrea", query = "SELECT e FROM EntSintomas e WHERE e.rinorrea = :rinorrea"),
    @NamedQuery(name = "EntSintomas.findByMialgias", query = "SELECT e FROM EntSintomas e WHERE e.mialgias = :mialgias")})
public class EntSintomas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EntSintomasPK entSintomasPK;
    @Column(name = "TOS")
    private Boolean tos;
    @Column(name = "CEFALEA")
    private Boolean cefalea;
    @Column(name = "DOLOR_ABDOMINAL")
    private Boolean dolorAbdominal;
    @Column(name = "DOLOR_TORACICO")
    private Boolean dolorToracico;
    @Column(name = "VOMITO")
    private Boolean vomito;
    @Column(name = "IRRITABLE")
    private Boolean irritable;
    @Column(name = "PERDIDA_OLFATO_GUSTO")
    private Boolean perdidaOlfatoGusto;
    @Column(name = "DIARREA")
    private Boolean diarrea;
    @Column(name = "ODINOFAGIA")
    private Boolean odinofagia;
    @Column(name = "RINORREA")
    private Boolean rinorrea;
    @Column(name = "MIALGIAS")
    private Boolean mialgias;
    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID_PACIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EntPaciente entPaciente;

    public EntSintomas() {
    }

    public EntSintomas(EntSintomasPK entSintomasPK) {
        this.entSintomasPK = entSintomasPK;
    }

    public EntSintomas(Date fecha, long idPaciente) {
        this.entSintomasPK = new EntSintomasPK(fecha, idPaciente);
    }

    public EntSintomasPK getEntSintomasPK() {
        return entSintomasPK;
    }

    public void setEntSintomasPK(EntSintomasPK entSintomasPK) {
        this.entSintomasPK = entSintomasPK;
    }

    public Boolean getTos() {
        return tos;
    }

    public void setTos(Boolean tos) {
        this.tos = tos;
    }

    public Boolean getCefalea() {
        return cefalea;
    }

    public void setCefalea(Boolean cefalea) {
        this.cefalea = cefalea;
    }

    public Boolean getDolorAbdominal() {
        return dolorAbdominal;
    }

    public void setDolorAbdominal(Boolean dolorAbdominal) {
        this.dolorAbdominal = dolorAbdominal;
    }

    public Boolean getDolorToracico() {
        return dolorToracico;
    }

    public void setDolorToracico(Boolean dolorToracico) {
        this.dolorToracico = dolorToracico;
    }

    public Boolean getVomito() {
        return vomito;
    }

    public void setVomito(Boolean vomito) {
        this.vomito = vomito;
    }

    public Boolean getIrritable() {
        return irritable;
    }

    public void setIrritable(Boolean irritable) {
        this.irritable = irritable;
    }

    public Boolean getPerdidaOlfatoGusto() {
        return perdidaOlfatoGusto;
    }

    public void setPerdidaOlfatoGusto(Boolean perdidaOlfatoGusto) {
        this.perdidaOlfatoGusto = perdidaOlfatoGusto;
    }

    public Boolean getDiarrea() {
        return diarrea;
    }

    public void setDiarrea(Boolean diarrea) {
        this.diarrea = diarrea;
    }

    public Boolean getOdinofagia() {
        return odinofagia;
    }

    public void setOdinofagia(Boolean odinofagia) {
        this.odinofagia = odinofagia;
    }

    public Boolean getRinorrea() {
        return rinorrea;
    }

    public void setRinorrea(Boolean rinorrea) {
        this.rinorrea = rinorrea;
    }

    public Boolean getMialgias() {
        return mialgias;
    }

    public void setMialgias(Boolean mialgias) {
        this.mialgias = mialgias;
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
        hash += (entSintomasPK != null ? entSintomasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntSintomas)) {
            return false;
        }
        EntSintomas other = (EntSintomas) object;
        if ((this.entSintomasPK == null && other.entSintomasPK != null) || (this.entSintomasPK != null && !this.entSintomasPK.equals(other.entSintomasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmhospital.modelo.EntSintomas[ entSintomasPK=" + entSintomasPK + " ]";
    }
    
}
