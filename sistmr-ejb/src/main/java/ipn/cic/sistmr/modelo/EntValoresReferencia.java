/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Entity
@Table(name = "RM_VALORES_REFERENCIA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntValoresReferencia.findAll", query = "SELECT e FROM EntValoresReferencia e"),
    @NamedQuery(name = "EntValoresReferencia.findBySatOxigenoMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.satOxigenoMin = :satOxigenoMin"),
    @NamedQuery(name = "EntValoresReferencia.findBySatOxigenoMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.satOxigenoMax = :satOxigenoMax"),
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaMin = :temperaturaMin"),
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaMax = :temperaturaMax"),
    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaMin = :capnografiaMin"),
    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaMax = :capnografiaMax"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaMin = :frecCardiacaMin"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaMax = :frecCardiacaMax"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaMin = :frecRespiratoriaMin"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaMax = :frecRespiratoriaMax"),
    @NamedQuery(name = "EntValoresReferencia.findByIdValref", query = "SELECT e FROM EntValoresReferencia e WHERE e.idValref = :idValref")})
public class EntValoresReferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAT_OXIGENO_MIN")
    private float satOxigenoMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAT_OXIGENO_MAX")
    private float satOxigenoMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURA_MIN")
    private float temperaturaMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURA_MAX")
    private float temperaturaMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPNOGRAFIA_MIN")
    private short capnografiaMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPNOGRAFIA_MAX")
    private short capnografiaMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_CARDIACA_MIN")
    private short frecCardiacaMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_CARDIACA_MAX")
    private short frecCardiacaMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_RESPIRATORIA_MIN")
    private short frecRespiratoriaMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_RESPIRATORIA_MAX")
    private short frecRespiratoriaMax;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VALREF")
    private Short idValref;

    public EntValoresReferencia() {
    }

    public EntValoresReferencia(Short idValref) {
        this.idValref = idValref;
    }

    public EntValoresReferencia(Short idValref, float satOxigenoMin, float satOxigenoMax, float temperaturaMin, float temperaturaMax, short capnografiaMin, short capnografiaMax, short frecCardiacaMin, short frecCardiacaMax, short frecRespiratoriaMin, short frecRespiratoriaMax) {
        this.idValref = idValref;
        this.satOxigenoMin = satOxigenoMin;
        this.satOxigenoMax = satOxigenoMax;
        this.temperaturaMin = temperaturaMin;
        this.temperaturaMax = temperaturaMax;
        this.capnografiaMin = capnografiaMin;
        this.capnografiaMax = capnografiaMax;
        this.frecCardiacaMin = frecCardiacaMin;
        this.frecCardiacaMax = frecCardiacaMax;
        this.frecRespiratoriaMin = frecRespiratoriaMin;
        this.frecRespiratoriaMax = frecRespiratoriaMax;
    }

    public float getSatOxigenoMin() {
        return satOxigenoMin;
    }

    public void setSatOxigenoMin(float satOxigenoMin) {
        this.satOxigenoMin = satOxigenoMin;
    }

    public float getSatOxigenoMax() {
        return satOxigenoMax;
    }

    public void setSatOxigenoMax(float satOxigenoMax) {
        this.satOxigenoMax = satOxigenoMax;
    }

    public float getTemperaturaMin() {
        return temperaturaMin;
    }

    public void setTemperaturaMin(float temperaturaMin) {
        this.temperaturaMin = temperaturaMin;
    }

    public float getTemperaturaMax() {
        return temperaturaMax;
    }

    public void setTemperaturaMax(float temperaturaMax) {
        this.temperaturaMax = temperaturaMax;
    }

    public short getCapnografiaMin() {
        return capnografiaMin;
    }

    public void setCapnografiaMin(short capnografiaMin) {
        this.capnografiaMin = capnografiaMin;
    }

    public short getCapnografiaMax() {
        return capnografiaMax;
    }

    public void setCapnografiaMax(short capnografiaMax) {
        this.capnografiaMax = capnografiaMax;
    }

    public short getFrecCardiacaMin() {
        return frecCardiacaMin;
    }

    public void setFrecCardiacaMin(short frecCardiacaMin) {
        this.frecCardiacaMin = frecCardiacaMin;
    }

    public short getFrecCardiacaMax() {
        return frecCardiacaMax;
    }

    public void setFrecCardiacaMax(short frecCardiacaMax) {
        this.frecCardiacaMax = frecCardiacaMax;
    }

    public short getFrecRespiratoriaMin() {
        return frecRespiratoriaMin;
    }

    public void setFrecRespiratoriaMin(short frecRespiratoriaMin) {
        this.frecRespiratoriaMin = frecRespiratoriaMin;
    }

    public short getFrecRespiratoriaMax() {
        return frecRespiratoriaMax;
    }

    public void setFrecRespiratoriaMax(short frecRespiratoriaMax) {
        this.frecRespiratoriaMax = frecRespiratoriaMax;
    }

    public Short getIdValref() {
        return idValref;
    }

    public void setIdValref(Short idValref) {
        this.idValref = idValref;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idValref != null ? idValref.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntValoresReferencia)) {
            return false;
        }
        EntValoresReferencia other = (EntValoresReferencia) object;
        if ((this.idValref == null && other.idValref != null) || (this.idValref != null && !this.idValref.equals(other.idValref))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntValoresReferencia[ idValref=" + idValref + " ]";
    }
    
}
