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
    @NamedQuery(name = "EntValoresReferencia.findBySatOxigenoMinInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.satOxigenoMinInter = :satOxigenoMinInter"),
    @NamedQuery(name = "EntValoresReferencia.findBySatOxigenoMaxInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.satOxigenoMaxInter = :satOxigenoMaxInter"),
    @NamedQuery(name = "EntValoresReferencia.findBySatOxigenoMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.satOxigenoMax = :satOxigenoMax"),
    
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaMin = :temperaturaMin"),
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaMinInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaMinInter = :temperaturaMinInter"),
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaMaxInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaMaxInter = :temperaturaMaxInter"),
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaMax = :temperaturaMax"),

    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaMin = :capnografiaMin"),
    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaMinInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaMinInter = :capnografiaMinInter"),
    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaMaxInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaMaxInter = :capnografiaMaxInter"),
    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaMax = :capnografiaMax"),
    
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaMin = :frecCardiacaMin"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaMinInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaMinInter = :frecCardiacaMinInter"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaMaxInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaMaxInter = :frecCardiacaMaxInter"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaMax = :frecCardiacaMax"),
    
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaMin = :frecRespiratoriaMin"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaMinInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaMinInter = :frecRespiratoriaMinInter"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaMaxInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaMaxInter = :frecRespiratoriaMaxInter"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaMax = :frecRespiratoriaMax"),
 
    @NamedQuery(name = "EntValoresReferencia.findByIdValref", query = "SELECT e FROM EntValoresReferencia e WHERE e.idValref = :idValref"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtSistolicaMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtSistolicaMin = :preArtSistolicaMin"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtSistolicaMinInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtSistolicaMinInter = :preArtSistolicaMinInter"),   
    @NamedQuery(name = "EntValoresReferencia.findByPreArtSistolicaMaxInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtSistolicaMaxInter = :preArtSistolicaMaxInter"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtSistolicaMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtSistolicaMax = :preArtSistolicaMax"),
    
    @NamedQuery(name = "EntValoresReferencia.findByPreArtDiastolicaMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtDiastolicaMin = :preArtDiastolicaMin"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtDiastolicaMinInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtDiastolicaMinInter = :preArtDiastolicaMinInter"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtDiastolicaMaxInter", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtDiastolicaMaxInter = :preArtDiastolicaMaxInter"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtDiastolicaMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtDiastolicaMax = :preArtDiastolicaMax")})
public class EntValoresReferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAT_OXIGENO_MIN")
    private float satOxigenoMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAT_OXIGENO_MIN_INTER")
    private float satOxigenoMinInter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAT_OXIGENO_MAX_INTER")
    private float satOxigenoMaxInter;
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
    @Column(name = "TEMPERATURA_MIN_INTER")
    private float temperaturaMinInter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURA_MAX_INTER")
    private float temperaturaMaxInter;
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
    @Column(name = "CAPNOGRAFIA_MIN_INTER")
    private short capnografiaMinInter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPNOGRAFIA_MAX_INTER")
    private short capnografiaMaxInter;
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
    @Column(name = "FREC_CARDIACA_MIN_INTER")
    private short frecCardiacaMinInter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_CARDIACA_MAX_INTER")
    private short frecCardiacaMaxInter;
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
    @Column(name = "FREC_RESPIRATORIA_MIN_INTER")
    private short frecRespiratoriaMinInter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_RESPIRATORIA_MAX_INTER")
    private short frecRespiratoriaMaxInter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_RESPIRATORIA_MAX")
    private short frecRespiratoriaMax;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VALREF")
    private Short idValref;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_SISTOLICA_MIN")
    private int preArtSistolicaMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_SISTOLICA_MIN_INTER")
    private int preArtSistolicaMinInter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_SISTOLICA_MAX_INTER")
    private int preArtSistolicaMaxInter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_SISTOLICA_MAX")
    private int preArtSistolicaMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_DIASTOLICA_MIN")
    private int preArtDiastolicaMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_DIASTOLICA_MIN_INTER")
    private int preArtDiastolicaMinInter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_DIASTOLICA_MAX_INTER")
    private int preArtDiastolicaMaxInter;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_DIASTOLICA_MAX")
    private int preArtDiastolicaMax;
    
    public EntValoresReferencia() {
    }

    public EntValoresReferencia(Short idValref) {
        this.idValref = idValref;
    }

     public EntValoresReferencia(float satOxigenoMin, float satOxigenoMinInter, float satOxigenoMaxInter, float satOxigenoMax, float temperaturaMin, float temperaturaMinInter, float temperaturaMaxInter, float temperaturaMax, short capnografiaMin, short capnografiaMinInter, short capnografiaMaxInter, short capnografiaMax, short frecCardiacaMin, short frecCardiacaMinInter, short frecCardiacaMaxInter, short frecCardiacaMax, short frecRespiratoriaMin, short frecRespiratoriaMinInter, short frecRespiratoriaMaxInter, short frecRespiratoriaMax, Short idValref, int preArtSistolicaMin, int preArtSistolicaMinInter, int preArtSistolicaMaxInter, int preArtSistolicaMax, int preArtDiastolicaMin, int preArtDiastolicaMinInter, int preArtDiastolicaMaxInter, int preArtDiastolicaMax) {
        this.satOxigenoMin = satOxigenoMin;
        this.satOxigenoMinInter = satOxigenoMinInter;
        this.satOxigenoMaxInter = satOxigenoMaxInter;
        this.satOxigenoMax = satOxigenoMax;
        this.temperaturaMin = temperaturaMin;
        this.temperaturaMinInter = temperaturaMinInter;
        this.temperaturaMaxInter = temperaturaMaxInter;
        this.temperaturaMax = temperaturaMax;
        this.capnografiaMin = capnografiaMin;
        this.capnografiaMinInter = capnografiaMinInter;
        this.capnografiaMaxInter = capnografiaMaxInter;
        this.capnografiaMax = capnografiaMax;
        this.frecCardiacaMin = frecCardiacaMin;
        this.frecCardiacaMinInter = frecCardiacaMinInter;
        this.frecCardiacaMaxInter = frecCardiacaMaxInter;
        this.frecCardiacaMax = frecCardiacaMax;
        this.frecRespiratoriaMin = frecRespiratoriaMin;
        this.frecRespiratoriaMinInter = frecRespiratoriaMinInter;
        this.frecRespiratoriaMaxInter = frecRespiratoriaMaxInter;
        this.frecRespiratoriaMax = frecRespiratoriaMax;
        this.idValref = idValref;
        this.preArtSistolicaMin = preArtSistolicaMin;
        this.preArtSistolicaMinInter = preArtSistolicaMinInter;
        this.preArtSistolicaMaxInter = preArtSistolicaMaxInter;
        this.preArtSistolicaMax = preArtSistolicaMax;
        this.preArtDiastolicaMin = preArtDiastolicaMin;
        this.preArtDiastolicaMinInter = preArtDiastolicaMinInter;
        this.preArtDiastolicaMaxInter = preArtDiastolicaMaxInter;
        this.preArtDiastolicaMax = preArtDiastolicaMax;
    }

    public float getSatOxigenoMin() {
        return satOxigenoMin;
    }

    public void setSatOxigenoMin(float satOxigenoMin) {
        this.satOxigenoMin = satOxigenoMin;
    }

    public float getSatOxigenoMinInter() {
        return satOxigenoMinInter;
    }

    public void setSatOxigenoMinInter(float satOxigenoMinInter) {
        this.satOxigenoMinInter = satOxigenoMinInter;
    }

    public float getSatOxigenoMaxInter() {
        return satOxigenoMaxInter;
    }

    public void setSatOxigenoMaxInter(float satOxigenoMaxInter) {
        this.satOxigenoMaxInter = satOxigenoMaxInter;
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

    public float getTemperaturaMinInter() {
        return temperaturaMinInter;
    }

    public void setTemperaturaMinInter(float temperaturaMinInter) {
        this.temperaturaMinInter = temperaturaMinInter;
    }

    public float getTemperaturaMaxInter() {
        return temperaturaMaxInter;
    }

    public void setTemperaturaMaxInter(float temperaturaMaxInter) {
        this.temperaturaMaxInter = temperaturaMaxInter;
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

    public short getCapnografiaMinInter() {
        return capnografiaMinInter;
    }

    public void setCapnografiaMinInter(short capnografiaMinInter) {
        this.capnografiaMinInter = capnografiaMinInter;
    }

    public short getCapnografiaMaxInter() {
        return capnografiaMaxInter;
    }

    public void setCapnografiaMaxInter(short capnografiaMaxInter) {
        this.capnografiaMaxInter = capnografiaMaxInter;
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

    public short getFrecCardiacaMinInter() {
        return frecCardiacaMinInter;
    }

    public void setFrecCardiacaMinInter(short frecCardiacaMinInter) {
        this.frecCardiacaMinInter = frecCardiacaMinInter;
    }

    public short getFrecCardiacaMaxInter() {
        return frecCardiacaMaxInter;
    }

    public void setFrecCardiacaMaxInter(short frecCardiacaMaxInter) {
        this.frecCardiacaMaxInter = frecCardiacaMaxInter;
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

    public short getFrecRespiratoriaMinInter() {
        return frecRespiratoriaMinInter;
    }

    public void setFrecRespiratoriaMinInter(short frecRespiratoriaMinInter) {
        this.frecRespiratoriaMinInter = frecRespiratoriaMinInter;
    }

    public short getFrecRespiratoriaMaxInter() {
        return frecRespiratoriaMaxInter;
    }

    public void setFrecRespiratoriaMaxInter(short frecRespiratoriaMaxInter) {
        this.frecRespiratoriaMaxInter = frecRespiratoriaMaxInter;
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

    public int getPreArtSistolicaMin() {
        return preArtSistolicaMin;
    }

    public void setPreArtSistolicaMin(int preArtSistolicaMin) {
        this.preArtSistolicaMin = preArtSistolicaMin;
    }

    public int getPreArtSistolicaMinInter() {
        return preArtSistolicaMinInter;
    }

    public void setPreArtSistolicaMinInter(int preArtSistolicaMinInter) {
        this.preArtSistolicaMinInter = preArtSistolicaMinInter;
    }

    public int getPreArtSistolicaMaxInter() {
        return preArtSistolicaMaxInter;
    }

    public void setPreArtSistolicaMaxInter(int preArtSistolicaMaxInter) {
        this.preArtSistolicaMaxInter = preArtSistolicaMaxInter;
    }

    public int getPreArtSistolicaMax() {
        return preArtSistolicaMax;
    }

    public void setPreArtSistolicaMax(int preArtSistolicaMax) {
        this.preArtSistolicaMax = preArtSistolicaMax;
    }

    public int getPreArtDiastolicaMin() {
        return preArtDiastolicaMin;
    }

    public void setPreArtDiastolicaMin(int preArtDiastolicaMin) {
        this.preArtDiastolicaMin = preArtDiastolicaMin;
    }

    public int getPreArtDiastolicaMinInter() {
        return preArtDiastolicaMinInter;
    }

    public void setPreArtDiastolicaMinInter(int preArtDiastolicaMinInter) {
        this.preArtDiastolicaMinInter = preArtDiastolicaMinInter;
    }

    public int getPreArtDiastolicaMaxInter() {
        return preArtDiastolicaMaxInter;
    }

    public void setPreArtDiastolicaMaxInter(int preArtDiastolicaMaxInter) {
        this.preArtDiastolicaMaxInter = preArtDiastolicaMaxInter;
    }

    public int getPreArtDiastolicaMax() {
        return preArtDiastolicaMax;
    }

    public void setPreArtDiastolicaMax(int preArtDiastolicaMax) {
        this.preArtDiastolicaMax = preArtDiastolicaMax;
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
