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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "RM_MEDIDAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntMedidas.findAll", query = "SELECT e FROM EntMedidas e"),
    @NamedQuery(name = "EntMedidas.findByFechaMedicion", query = "SELECT e FROM EntMedidas e WHERE e.fechaMedicion = :fechaMedicion"),
    @NamedQuery(name = "EntMedidas.findBySaturacionOxigeno", query = "SELECT e FROM EntMedidas e WHERE e.saturacionOxigeno = :saturacionOxigeno"),
    @NamedQuery(name = "EntMedidas.findByTemperatura", query = "SELECT e FROM EntMedidas e WHERE e.temperatura = :temperatura"),
    @NamedQuery(name = "EntMedidas.findByCapnografia", query = "SELECT e FROM EntMedidas e WHERE e.capnografia = :capnografia"),
    @NamedQuery(name = "EntMedidas.findByFrecCardiaca", query = "SELECT e FROM EntMedidas e WHERE e.frecCardiaca = :frecCardiaca"),
    @NamedQuery(name = "EntMedidas.findByFrecRespiratoria", query = "SELECT e FROM EntMedidas e WHERE e.frecRespiratoria = :frecRespiratoria"),
    @NamedQuery(name = "EntMedidas.findByIdPaciente", query = "SELECT e FROM EntMedidas e WHERE e.entMedidasPK.idPaciente = :idPaciente"),
    @NamedQuery(name = "EntMedidas.findByIdCareta", query = "SELECT e FROM EntMedidas e WHERE e.entMedidasPK.idCareta = :idCareta"),
    @NamedQuery(name = "EntMedidas.findByIdMedicion", query = "SELECT e FROM EntMedidas e WHERE e.entMedidasPK.idMedicion = :idMedicion"),
    @NamedQuery(name = "EntMedidas.findByAlerta", query = "SELECT e FROM EntMedidas e WHERE e.alerta = :alerta"),
    @NamedQuery(name = "EntMedidas.findByPreArtSistolica", query = "SELECT e FROM EntMedidas e WHERE e.preArtSistolica = :preArtSistolica"),
    @NamedQuery(name = "EntMedidas.findByPreArtDiastolica", query = "SELECT e FROM EntMedidas e WHERE e.preArtDiastolica = :preArtDiastolica")})
public class EntMedidas implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EntMedidasPK entMedidasPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_MEDICION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaMedicion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SATURACION_OXIGENO")
    private float saturacionOxigeno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURA")
    private float temperatura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPNOGRAFIA")
    private short capnografia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_CARDIACA")
    private short frecCardiaca;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_RESPIRATORIA")
    private short frecRespiratoria;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ALERTA")
    private boolean alerta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_SISTOLICA")
    private int preArtSistolica;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_DIASTOLICA")
    private int preArtDiastolica;
    @JoinColumn(name = "ID_CARETA", referencedColumnName = "ID_CARETA", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EntCareta entCareta;
    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID_PACIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EntPaciente entPaciente;

    public EntMedidas() {
    }

    public EntMedidas(EntMedidasPK entMedidasPK) {
        this.entMedidasPK = entMedidasPK;
    }

    public EntMedidas(EntMedidasPK entMedidasPK, Date fechaMedicion, float saturacionOxigeno, float temperatura, short capnografia, short frecCardiaca, short frecRespiratoria, boolean alerta) {
        this.entMedidasPK = entMedidasPK;
        this.fechaMedicion = fechaMedicion;
        this.saturacionOxigeno = saturacionOxigeno;
        this.temperatura = temperatura;
        this.capnografia = capnografia;
        this.frecCardiaca = frecCardiaca;
        this.frecRespiratoria = frecRespiratoria;
        this.alerta = alerta;
    }

    public EntMedidas(long idPaciente, long idCareta, long idMedicion) {
        this.entMedidasPK = new EntMedidasPK(idPaciente, idCareta, idMedicion);
    }

    public EntMedidasPK getEntMedidasPK() {
        return entMedidasPK;
    }

    public void setEntMedidasPK(EntMedidasPK entMedidasPK) {
        this.entMedidasPK = entMedidasPK;
    }

    public Date getFechaMedicion() {
        return fechaMedicion;
    }

    public void setFechaMedicion(Date fechaMedicion) {
        this.fechaMedicion = fechaMedicion;
    }

    public float getSaturacionOxigeno() {
        return saturacionOxigeno;
    }

    public void setSaturacionOxigeno(float saturacionOxigeno) {
        this.saturacionOxigeno = saturacionOxigeno;
    }

    public float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(float temperatura) {
        this.temperatura = temperatura;
    }

    public short getCapnografia() {
        return capnografia;
    }

    public void setCapnografia(short capnografia) {
        this.capnografia = capnografia;
    }

    public short getFrecCardiaca() {
        return frecCardiaca;
    }

    public void setFrecCardiaca(short frecCardiaca) {
        this.frecCardiaca = frecCardiaca;
    }

    public short getFrecRespiratoria() {
        return frecRespiratoria;
    }

    public void setFrecRespiratoria(short frecRespiratoria) {
        this.frecRespiratoria = frecRespiratoria;
    }

    public boolean getAlerta() {
        return alerta;
    }

    public void setAlerta(boolean alerta) {
        this.alerta = alerta;
    }

    public EntCareta getEntCareta() {
        return entCareta;
    }

    public void setEntCareta(EntCareta entCareta) {
        this.entCareta = entCareta;
    }

    public EntPaciente getEntPaciente() {
        return entPaciente;
    }

    public void setEntPaciente(EntPaciente entPaciente) {
        this.entPaciente = entPaciente;
    }
    
    public int getPreArtSistolica() {
        return preArtSistolica;
    }

    public void setPreArtSistolica(int preArtSistolica) {
        this.preArtSistolica = preArtSistolica;
    }

    public int getPreArtDiastolica() {
        return preArtDiastolica;
    }

    public void setPreArtDiastolica(int preArtDiastolica) {
        this.preArtDiastolica = preArtDiastolica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entMedidasPK != null ? entMedidasPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntMedidas)) {
            return false;
        }
        EntMedidas other = (EntMedidas) object;
        if ((this.entMedidasPK == null && other.entMedidasPK != null) || (this.entMedidasPK != null && !this.entMedidasPK.equals(other.entMedidasPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntMedidas[ entMedidasPK=" + entMedidasPK + " ]";
    }
    
}
