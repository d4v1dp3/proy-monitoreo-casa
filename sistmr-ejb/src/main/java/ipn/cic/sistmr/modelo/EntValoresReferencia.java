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
    @NamedQuery(name = "EntValoresReferencia.findBySatOxigenoNormalMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.satOxigenoNormalMin = :satOxigenoNormalMin"),
    @NamedQuery(name = "EntValoresReferencia.findBySatOxigenoNormalMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.satOxigenoNormalMax = :satOxigenoNormalMax"),
    @NamedQuery(name = "EntValoresReferencia.findBySatOxigenoWarningMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.satOxigenoWarningMin = :satOxigenoWarningMin"),
    @NamedQuery(name = "EntValoresReferencia.findBySatOxigenoWarningMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.satOxigenoWarningMax = :satOxigenoWarningMax"),
    @NamedQuery(name = "EntValoresReferencia.findBySatOxigenoAlertMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.satOxigenoAlertMin = :satOxigenoAlertMin"),
    @NamedQuery(name = "EntValoresReferencia.findBySatOxigenoAlertMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.satOxigenoAlertMax = :satOxigenoAlertMax"), 
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaNormalMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaNormalMin = :temperaturaNormalMin"),
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaNormalMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaNormalMax = :temperaturaNormalMax"),
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaWarningMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaWarningMin = :temperaturaWarningMin"),
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaWarningMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaWarningMax = :temperaturaWarningMax"),
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaAlertMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaAlertMin = :temperaturaAlertMin"),
    @NamedQuery(name = "EntValoresReferencia.findByTemperaturaAlertMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.temperaturaAlertMax = :temperaturaAlertMax"),  
    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaNormalMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaNormalMin = :capnografiaNormalMin"),
    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaNormalMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaNormalMax = :capnografiaNormalMax"),
    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaWarningMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaWarningMin = :capnografiaWarningMin"),
    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaWarningMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaWarningMax = :capnografiaWarningMax"),
    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaAlertMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaAlertMin = :capnografiaAlertMin"),
    @NamedQuery(name = "EntValoresReferencia.findByCapnografiaAlertMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.capnografiaAlertMax = :capnografiaAlertMax"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaNormalMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaNormalMin = :frecCardiacaNormalMin"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaNormalMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaNormalMax = :frecCardiacaNormalMax"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaWarningMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaWarningMin = :frecCardiacaWarningMin"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaWarningMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaWarningMax = :frecCardiacaWarningMax"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaAlertMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaAlertMin = :frecCardiacaAlertMin"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecCardiacaAlertMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecCardiacaAlertMax = :frecCardiacaAlertMax"),  
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaNormalMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaNormalMin = :frecRespiratoriaNormalMin"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaNormalMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaNormalMax = :frecRespiratoriaNormalMax"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaWarningMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaWarningMin = :frecRespiratoriaWarningMin"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaWarningMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaWarningMax = :frecRespiratoriaWarningMax"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaAlertMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaAlertMin = :frecRespiratoriaAlertMin"),
    @NamedQuery(name = "EntValoresReferencia.findByFrecRespiratoriaAlertMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.frecRespiratoriaAlertMax = :frecRespiratoriaAlertMax"),
    @NamedQuery(name = "EntValoresReferencia.findByIdValref", query = "SELECT e FROM EntValoresReferencia e WHERE e.idValref = :idValref"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtSistolicaNormalMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtSistolicaNormalMin = :preArtSistolicaNormalMin"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtSistolicaNormalMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtSistolicaNormalMax = :preArtSistolicaNormalMax"),   
    @NamedQuery(name = "EntValoresReferencia.findByPreArtSistolicaWarningMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtSistolicaWarningMin = :preArtSistolicaWarningMin"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtSistolicaWarningMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtSistolicaWarningMax = :preArtSistolicaWarningMax"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtSistolicaAlertMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtSistolicaAlertMin = :preArtSistolicaAlertMin"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtSistolicaAlertMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtSistolicaAlertMax = :preArtSistolicaAlertMax"),    
    @NamedQuery(name = "EntValoresReferencia.findByPreArtDiastolicaNormalMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtDiastolicaNormalMin = :preArtDiastolicaNormalMin"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtDiastolicaNormalMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtDiastolicaNormalMax = :preArtDiastolicaNormalMax"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtDiastolicaWarningMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtDiastolicaWarningMin = :preArtDiastolicaWarningMin"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtDiastolicaWarningMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtDiastolicaWarningMax = :preArtDiastolicaWarningMax"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtDiastolicaAlertMin", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtDiastolicaAlertMin = :preArtDiastolicaAlertMin"),
    @NamedQuery(name = "EntValoresReferencia.findByPreArtDiastolicaAlertMax", query = "SELECT e FROM EntValoresReferencia e WHERE e.preArtDiastolicaAlertMax = :preArtDiastolicaAlertMax")})

public class EntValoresReferencia implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAT_OXIGENO_NORMAL_MIN")
    private float satOxigenoNormalMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAT_OXIGENO_NORMAL_MAX")
    private float satOxigenoNormalMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAT_OXIGENO_WARNING_MIN")
    private float satOxigenoWarningMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAT_OXIGENO_WARNING_MAX")
    private float satOxigenoWarningMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAT_OXIGENO_ALERT_MIN")
    private float satOxigenoAlertMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SAT_OXIGENO_ALERT_MAX")
    private float satOxigenoAlertMax;  
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURA_NORMAL_MIN")
    private float temperaturaNormalMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURA_NORMAL_MAX")
    private float temperaturaNormalMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURA_WARNING_MIN")
    private float temperaturaWarningMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURA_WARNING_MAX")
    private float temperaturaWarningMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURA_ALERT_MIN")
    private float temperaturaAlertMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TEMPERATURA_ALERT_MAX")
    private float temperaturaAlertMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPNOGRAFIA_NORMAL_MIN")
    private short capnografiaNormalMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPNOGRAFIA_NORMAL_MAX")
    private short capnografiaNormalMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPNOGRAFIA_WARNING_MIN")
    private short capnografiaWarningMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPNOGRAFIA_WARNING_MAX")
    private short capnografiaWarningMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPNOGRAFIA_ALERT_MIN")
    private short capnografiaAlertMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CAPNOGRAFIA_ALERT_MAX")
    private short capnografiaAlertMax;    
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_CARDIACA_NORMAL_MIN")
    private short frecCardiacaNormalMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_CARDIACA_NORMAL_MAX")
    private short frecCardiacaNormalMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_CARDIACA_WARNING_MIN")
    private short frecCardiacaWarningMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_CARDIACA_WARNING_MAX")
    private short frecCardiacaWarningMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_CARDIACA_ALERT_MIN")
    private short frecCardiacaAlertMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_CARDIACA_ALERT_MAX")
    private short frecCardiacaAlertMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_RESPIRATORIA_NORMAL_MIN")
    private short frecRespiratoriaNormalMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_RESPIRATORIA_NORMAL_MAX")
    private short frecRespiratoriaNormalMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_RESPIRATORIA_WARNING_MIN")
    private short frecRespiratoriaWarningMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_RESPIRATORIA_WARNING_MAX")
    private short frecRespiratoriaWarningMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_RESPIRATORIA_ALERT_MIN")
    private short frecRespiratoriaAlertMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FREC_RESPIRATORIA_ALERT_MAX")
    private short frecRespiratoriaAlertMax;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_VALREF")
    private Short idValref;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_SISTOLICA_NORMAL_MIN")
    private int preArtSistolicaNormalMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_SISTOLICA_NORMAL_MAX")
    private int preArtSistolicaNormalMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_SISTOLICA_WARNING_MIN")
    private int preArtSistolicaWarningMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_SISTOLICA_WARNING_MAX")
    private int preArtSistolicaWarningMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_SISTOLICA_ALERT_MIN")
    private int preArtSistolicaAlertMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_SISTOLICA_ALERT_MAX")
    private int preArtSistolicaAlertMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_DIASTOLICA_NORMAL_MIN")
    private int preArtDiastolicaNormalMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_DIASTOLICA_NORMAL_MAX")
    private int preArtDiastolicaNormalMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_DIASTOLICA_WARNING_MIN")
    private int preArtDiastolicaWarningMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_DIASTOLICA_WARNING_MAX")
    private int preArtDiastolicaWarningMax;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_DIASTOLICA_ALERT_MIN")
    private int preArtDiastolicaAlertMin;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRE_ART_DIASTOLICA_ALERT_MAX")
    private int preArtDiastolicaAlertMax;
    
    public EntValoresReferencia() {
    }

    public EntValoresReferencia(Short idValref) {
        this.idValref = idValref;
    }

    public EntValoresReferencia(float satOxigenoNormalMin, float satOxigenoNormalMax, float satOxigenoWarningMin, float satOxigenoWarningMax, float satOxigenoAlertMin, float satOxigenoAlertMax, float temperaturaNormalMin, float temperaturaNormalMax, float temperaturaWarningMin, float temperaturaWarningMax, float temperaturaAlertMin, float temperaturaAlertMax, short capnografiaNormalMin, short capnografiaNormalMax, short capnografiaWarningMin, short capnografiaWarningMax, short capnografiaAlertMin, short capnografiaAlertMax, short frecCardiacaNormalMin, short frecCardiacaNormalMax, short frecCardiacaWarningMin, short frecCardiacaWarningMax, short frecCardiacaAlertMin, short frecCardiacaAlertMax, short frecRespiratoriaNormalMin, short frecRespiratoriaNormalMax, short frecRespiratoriaWarningMin, short frecRespiratoriaWarningMax, short frecRespiratoriaAlertMin, short frecRespiratoriaAlertMax, Short idValref, int preArtSistolicaNormalMin, int preArtSistolicaNormalMax, int preArtSistolicaWarningMin, int preArtSistolicaWarningMax, int preArtSistolicaAlertMin, int preArtSistolicaAlertMax, int preArtDiastolicaNormalMin, int preArtDiastolicaNormalMax, int preArtDiastolicaWarningMin, int preArtDiastolicaWarningMax, int preArtDiastolicaAlertMin, int preArtDiastolicaAlertMax) {
        this.satOxigenoNormalMin = satOxigenoNormalMin;
        this.satOxigenoNormalMax = satOxigenoNormalMax;
        this.satOxigenoWarningMin = satOxigenoWarningMin;
        this.satOxigenoWarningMax = satOxigenoWarningMax;
        this.satOxigenoAlertMin = satOxigenoAlertMin;
        this.satOxigenoAlertMax = satOxigenoAlertMax;
        this.temperaturaNormalMin = temperaturaNormalMin;
        this.temperaturaNormalMax = temperaturaNormalMax;
        this.temperaturaWarningMin = temperaturaWarningMin;
        this.temperaturaWarningMax = temperaturaWarningMax;
        this.temperaturaAlertMin = temperaturaAlertMin;
        this.temperaturaAlertMax = temperaturaAlertMax;
        this.capnografiaNormalMin = capnografiaNormalMin;
        this.capnografiaNormalMax = capnografiaNormalMax;
        this.capnografiaWarningMin = capnografiaWarningMin;
        this.capnografiaWarningMax = capnografiaWarningMax;
        this.capnografiaAlertMin = capnografiaAlertMin;
        this.capnografiaAlertMax = capnografiaAlertMax;
        this.frecCardiacaNormalMin = frecCardiacaNormalMin;
        this.frecCardiacaNormalMax = frecCardiacaNormalMax;
        this.frecCardiacaWarningMin = frecCardiacaWarningMin;
        this.frecCardiacaWarningMax = frecCardiacaWarningMax;
        this.frecCardiacaAlertMin = frecCardiacaAlertMin;
        this.frecCardiacaAlertMax = frecCardiacaAlertMax;
        this.frecRespiratoriaNormalMin = frecRespiratoriaNormalMin;
        this.frecRespiratoriaNormalMax = frecRespiratoriaNormalMax;
        this.frecRespiratoriaWarningMin = frecRespiratoriaWarningMin;
        this.frecRespiratoriaWarningMax = frecRespiratoriaWarningMax;
        this.frecRespiratoriaAlertMin = frecRespiratoriaAlertMin;
        this.frecRespiratoriaAlertMax = frecRespiratoriaAlertMax;
        this.idValref = idValref;
        this.preArtSistolicaNormalMin = preArtSistolicaNormalMin;
        this.preArtSistolicaNormalMax = preArtSistolicaNormalMax;
        this.preArtSistolicaWarningMin = preArtSistolicaWarningMin;
        this.preArtSistolicaWarningMax = preArtSistolicaWarningMax;
        this.preArtSistolicaAlertMin = preArtSistolicaAlertMin;
        this.preArtSistolicaAlertMax = preArtSistolicaAlertMax;
        this.preArtDiastolicaNormalMin = preArtDiastolicaNormalMin;
        this.preArtDiastolicaNormalMax = preArtDiastolicaNormalMax;
        this.preArtDiastolicaWarningMin = preArtDiastolicaWarningMin;
        this.preArtDiastolicaWarningMax = preArtDiastolicaWarningMax;
        this.preArtDiastolicaAlertMin = preArtDiastolicaAlertMin;
        this.preArtDiastolicaAlertMax = preArtDiastolicaAlertMax;
    }

    public float getSatOxigenoNormalMin() {
        return satOxigenoNormalMin;
    }

    public void setSatOxigenoNormalMin(float satOxigenoNormalMin) {
        this.satOxigenoNormalMin = satOxigenoNormalMin;
    }

    public float getSatOxigenoNormalMax() {
        return satOxigenoNormalMax;
    }

    public void setSatOxigenoNormalMax(float satOxigenoNormalMax) {
        this.satOxigenoNormalMax = satOxigenoNormalMax;
    }

    public float getSatOxigenoWarningMin() {
        return satOxigenoWarningMin;
    }

    public void setSatOxigenoWarningMin(float satOxigenoWarningMin) {
        this.satOxigenoWarningMin = satOxigenoWarningMin;
    }

    public float getSatOxigenoWarningMax() {
        return satOxigenoWarningMax;
    }

    public void setSatOxigenoWarningMax(float satOxigenoWarningMax) {
        this.satOxigenoWarningMax = satOxigenoWarningMax;
    }

    public float getSatOxigenoAlertMin() {
        return satOxigenoAlertMin;
    }

    public void setSatOxigenoAlertMin(float satOxigenoAlertMin) {
        this.satOxigenoAlertMin = satOxigenoAlertMin;
    }

    public float getSatOxigenoAlertMax() {
        return satOxigenoAlertMax;
    }

    public void setSatOxigenoAlertMax(float satOxigenoAlertMax) {
        this.satOxigenoAlertMax = satOxigenoAlertMax;
    }

    public float getTemperaturaNormalMin() {
        return temperaturaNormalMin;
    }

    public void setTemperaturaNormalMin(float temperaturaNormalMin) {
        this.temperaturaNormalMin = temperaturaNormalMin;
    }

    public float getTemperaturaNormalMax() {
        return temperaturaNormalMax;
    }

    public void setTemperaturaNormalMax(float temperaturaNormalMax) {
        this.temperaturaNormalMax = temperaturaNormalMax;
    }

    public float getTemperaturaWarningMin() {
        return temperaturaWarningMin;
    }

    public void setTemperaturaWarningMin(float temperaturaWarningMin) {
        this.temperaturaWarningMin = temperaturaWarningMin;
    }

    public float getTemperaturaWarningMax() {
        return temperaturaWarningMax;
    }

    public void setTemperaturaWarningMax(float temperaturaWarningMax) {
        this.temperaturaWarningMax = temperaturaWarningMax;
    }

    public float getTemperaturaAlertMin() {
        return temperaturaAlertMin;
    }

    public void setTemperaturaAlertMin(float temperaturaAlertMin) {
        this.temperaturaAlertMin = temperaturaAlertMin;
    }

    public float getTemperaturaAlertMax() {
        return temperaturaAlertMax;
    }

    public void setTemperaturaAlertMax(float temperaturaAlertMax) {
        this.temperaturaAlertMax = temperaturaAlertMax;
    }

    public short getCapnografiaNormalMin() {
        return capnografiaNormalMin;
    }

    public void setCapnografiaNormalMin(short capnografiaNormalMin) {
        this.capnografiaNormalMin = capnografiaNormalMin;
    }

    public short getCapnografiaNormalMax() {
        return capnografiaNormalMax;
    }

    public void setCapnografiaNormalMax(short capnografiaNormalMax) {
        this.capnografiaNormalMax = capnografiaNormalMax;
    }

    public short getCapnografiaWarningMin() {
        return capnografiaWarningMin;
    }

    public void setCapnografiaWarningMin(short capnografiaWarningMin) {
        this.capnografiaWarningMin = capnografiaWarningMin;
    }

    public short getCapnografiaWarningMax() {
        return capnografiaWarningMax;
    }

    public void setCapnografiaWarningMax(short capnografiaWarningMax) {
        this.capnografiaWarningMax = capnografiaWarningMax;
    }

    public short getCapnografiaAlertMin() {
        return capnografiaAlertMin;
    }

    public void setCapnografiaAlertMin(short capnografiaAlertMin) {
        this.capnografiaAlertMin = capnografiaAlertMin;
    }

    public short getCapnografiaAlertMax() {
        return capnografiaAlertMax;
    }

    public void setCapnografiaAlertMax(short capnografiaAlertMax) {
        this.capnografiaAlertMax = capnografiaAlertMax;
    }

    public short getFrecCardiacaNormalMin() {
        return frecCardiacaNormalMin;
    }

    public void setFrecCardiacaNormalMin(short frecCardiacaNormalMin) {
        this.frecCardiacaNormalMin = frecCardiacaNormalMin;
    }

    public short getFrecCardiacaNormalMax() {
        return frecCardiacaNormalMax;
    }

    public void setFrecCardiacaNormalMax(short frecCardiacaNormalMax) {
        this.frecCardiacaNormalMax = frecCardiacaNormalMax;
    }

    public short getFrecCardiacaWarningMin() {
        return frecCardiacaWarningMin;
    }

    public void setFrecCardiacaWarningMin(short frecCardiacaWarningMin) {
        this.frecCardiacaWarningMin = frecCardiacaWarningMin;
    }

    public short getFrecCardiacaWarningMax() {
        return frecCardiacaWarningMax;
    }

    public void setFrecCardiacaWarningMax(short frecCardiacaWarningMax) {
        this.frecCardiacaWarningMax = frecCardiacaWarningMax;
    }

    public short getFrecCardiacaAlertMin() {
        return frecCardiacaAlertMin;
    }

    public void setFrecCardiacaAlertMin(short frecCardiacaAlertMin) {
        this.frecCardiacaAlertMin = frecCardiacaAlertMin;
    }

    public short getFrecCardiacaAlertMax() {
        return frecCardiacaAlertMax;
    }

    public void setFrecCardiacaAlertMax(short frecCardiacaAlertMax) {
        this.frecCardiacaAlertMax = frecCardiacaAlertMax;
    }

    public short getFrecRespiratoriaNormalMin() {
        return frecRespiratoriaNormalMin;
    }

    public void setFrecRespiratoriaNormalMin(short frecRespiratoriaNormalMin) {
        this.frecRespiratoriaNormalMin = frecRespiratoriaNormalMin;
    }

    public short getFrecRespiratoriaNormalMax() {
        return frecRespiratoriaNormalMax;
    }

    public void setFrecRespiratoriaNormalMax(short frecRespiratoriaNormalMax) {
        this.frecRespiratoriaNormalMax = frecRespiratoriaNormalMax;
    }

    public short getFrecRespiratoriaWarningMin() {
        return frecRespiratoriaWarningMin;
    }

    public void setFrecRespiratoriaWarningMin(short frecRespiratoriaWarningMin) {
        this.frecRespiratoriaWarningMin = frecRespiratoriaWarningMin;
    }

    public short getFrecRespiratoriaWarningMax() {
        return frecRespiratoriaWarningMax;
    }

    public void setFrecRespiratoriaWarningMax(short frecRespiratoriaWarningMax) {
        this.frecRespiratoriaWarningMax = frecRespiratoriaWarningMax;
    }

    public short getFrecRespiratoriaAlertMin() {
        return frecRespiratoriaAlertMin;
    }

    public void setFrecRespiratoriaAlertMin(short frecRespiratoriaAlertMin) {
        this.frecRespiratoriaAlertMin = frecRespiratoriaAlertMin;
    }

    public short getFrecRespiratoriaAlertMax() {
        return frecRespiratoriaAlertMax;
    }

    public void setFrecRespiratoriaAlertMax(short frecRespiratoriaAlertMax) {
        this.frecRespiratoriaAlertMax = frecRespiratoriaAlertMax;
    }

    public Short getIdValref() {
        return idValref;
    }

    public void setIdValref(Short idValref) {
        this.idValref = idValref;
    }

    public int getPreArtSistolicaNormalMin() {
        return preArtSistolicaNormalMin;
    }

    public void setPreArtSistolicaNormalMin(int preArtSistolicaNormalMin) {
        this.preArtSistolicaNormalMin = preArtSistolicaNormalMin;
    }

    public int getPreArtSistolicaNormalMax() {
        return preArtSistolicaNormalMax;
    }

    public void setPreArtSistolicaNormalMax(int preArtSistolicaNormalMax) {
        this.preArtSistolicaNormalMax = preArtSistolicaNormalMax;
    }

    public int getPreArtSistolicaWarningMin() {
        return preArtSistolicaWarningMin;
    }

    public void setPreArtSistolicaWarningMin(int preArtSistolicaWarningMin) {
        this.preArtSistolicaWarningMin = preArtSistolicaWarningMin;
    }

    public int getPreArtSistolicaWarningMax() {
        return preArtSistolicaWarningMax;
    }

    public void setPreArtSistolicaWarningMax(int preArtSistolicaWarningMax) {
        this.preArtSistolicaWarningMax = preArtSistolicaWarningMax;
    }

    public int getPreArtSistolicaAlertMin() {
        return preArtSistolicaAlertMin;
    }

    public void setPreArtSistolicaAlertMin(int preArtSistolicaAlertMin) {
        this.preArtSistolicaAlertMin = preArtSistolicaAlertMin;
    }

    public int getPreArtSistolicaAlertMax() {
        return preArtSistolicaAlertMax;
    }

    public void setPreArtSistolicaAlertMax(int preArtSistolicaAlertMax) {
        this.preArtSistolicaAlertMax = preArtSistolicaAlertMax;
    }

    public int getPreArtDiastolicaNormalMin() {
        return preArtDiastolicaNormalMin;
    }

    public void setPreArtDiastolicaNormalMin(int preArtDiastolicaNormalMin) {
        this.preArtDiastolicaNormalMin = preArtDiastolicaNormalMin;
    }

    public int getPreArtDiastolicaNormalMax() {
        return preArtDiastolicaNormalMax;
    }

    public void setPreArtDiastolicaNormalMax(int preArtDiastolicaNormalMax) {
        this.preArtDiastolicaNormalMax = preArtDiastolicaNormalMax;
    }

    public int getPreArtDiastolicaWarningMin() {
        return preArtDiastolicaWarningMin;
    }

    public void setPreArtDiastolicaWarningMin(int preArtDiastolicaWarningMin) {
        this.preArtDiastolicaWarningMin = preArtDiastolicaWarningMin;
    }

    public int getPreArtDiastolicaWarningMax() {
        return preArtDiastolicaWarningMax;
    }

    public void setPreArtDiastolicaWarningMax(int preArtDiastolicaWarningMax) {
        this.preArtDiastolicaWarningMax = preArtDiastolicaWarningMax;
    }

    public int getPreArtDiastolicaAlertMin() {
        return preArtDiastolicaAlertMin;
    }

    public void setPreArtDiastolicaAlertMin(int preArtDiastolicaAlertMin) {
        this.preArtDiastolicaAlertMin = preArtDiastolicaAlertMin;
    }

    public int getPreArtDiastolicaAlertMax() {
        return preArtDiastolicaAlertMax;
    }

    public void setPreArtDiastolicaAlertMax(int preArtDiastolicaAlertMax) {
        this.preArtDiastolicaAlertMax = preArtDiastolicaAlertMax;
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
