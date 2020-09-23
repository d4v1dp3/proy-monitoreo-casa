/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Entity
@Table(name = "RM_PACIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntPaciente.findAll", query = "SELECT e FROM EntPaciente e"),
    @NamedQuery(name = "EntPaciente.findByIdPaciente", query = "SELECT e FROM EntPaciente e WHERE e.idPaciente = :idPaciente"),
    @NamedQuery(name = "EntPaciente.findByDirCalle", query = "SELECT e FROM EntPaciente e WHERE e.dirCalle = :dirCalle"),
    @NamedQuery(name = "EntPaciente.findByDirNumero", query = "SELECT e FROM EntPaciente e WHERE e.dirNumero = :dirNumero"),
    @NamedQuery(name = "EntPaciente.findByDirInterior", query = "SELECT e FROM EntPaciente e WHERE e.dirInterior = :dirInterior"),
    @NamedQuery(name = "EntPaciente.findByTelFijo", query = "SELECT e FROM EntPaciente e WHERE e.telFijo = :telFijo"),
    @NamedQuery(name = "EntPaciente.findByTelCel", query = "SELECT e FROM EntPaciente e WHERE e.telCel = :telCel")})
public class EntPaciente implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PACIENTE")
    private Long idPaciente;
    @Size(max = 50)
    @Column(name = "DIR_CALLE")
    private String dirCalle;
    @Column(name = "DIR_NUMERO")
    private Integer dirNumero;
    @Size(max = 50)
    @Column(name = "DIR_INTERIOR")
    private String dirInterior;
    @Size(max = 12)
    @Column(name = "TEL_FIJO")
    private String telFijo;
    @Size(max = 12)
    @Column(name = "TEL_CEL")
    private String telCel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entPaciente", fetch = FetchType.LAZY)
    private List<EntPacienteMedico> entPacienteMedicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entPaciente", fetch = FetchType.LAZY)
    private List<EntMedidas> entMedidasList;
    @JoinColumn(name = "ID_CARETA", referencedColumnName = "ID_CARETA")
    @ManyToOne(fetch = FetchType.LAZY)
    private EntCareta idCareta;
    @JoinColumn(name = "ID_ESTADOPACIENTE", referencedColumnName = "ID_ESTADOPACIENTE")
    @ManyToOne(fetch = FetchType.LAZY)
    private EntEstadopaciente idEstadopaciente;
    @JoinColumn(name = "ID_HOSPITAL", referencedColumnName = "ID_HOSPITAL")
    @ManyToOne(fetch = FetchType.LAZY)
    private EntHospital idHospital;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @ManyToOne(fetch = FetchType.LAZY)
    private EntPersona idPersona;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "entPaciente", fetch = FetchType.LAZY)
    private EntAntecedentes entAntecedentes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entPaciente", fetch = FetchType.LAZY)
    private List<EntSintomas> entSintomasList;

    public EntPaciente() {
    }

    public EntPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getDirCalle() {
        return dirCalle;
    }

    public void setDirCalle(String dirCalle) {
        this.dirCalle = dirCalle;
    }

    public Integer getDirNumero() {
        return dirNumero;
    }

    public void setDirNumero(Integer dirNumero) {
        this.dirNumero = dirNumero;
    }

    public String getDirInterior() {
        return dirInterior;
    }

    public void setDirInterior(String dirInterior) {
        this.dirInterior = dirInterior;
    }

    public String getTelFijo() {
        return telFijo;
    }

    public void setTelFijo(String telFijo) {
        this.telFijo = telFijo;
    }

    public String getTelCel() {
        return telCel;
    }

    public void setTelCel(String telCel) {
        this.telCel = telCel;
    }

    @XmlTransient
    public List<EntPacienteMedico> getEntPacienteMedicoList() {
        return entPacienteMedicoList;
    }

    public void setEntPacienteMedicoList(List<EntPacienteMedico> entPacienteMedicoList) {
        this.entPacienteMedicoList = entPacienteMedicoList;
    }

    @XmlTransient
    public List<EntMedidas> getEntMedidasList() {
        return entMedidasList;
    }

    public void setEntMedidasList(List<EntMedidas> entMedidasList) {
        this.entMedidasList = entMedidasList;
    }

    public EntCareta getIdCareta() {
        return idCareta;
    }

    public void setIdCareta(EntCareta idCareta) {
        this.idCareta = idCareta;
    }

    public EntEstadopaciente getIdEstadopaciente() {
        return idEstadopaciente;
    }

    public void setIdEstadopaciente(EntEstadopaciente idEstadopaciente) {
        this.idEstadopaciente = idEstadopaciente;
    }

    public EntHospital getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(EntHospital idHospital) {
        this.idHospital = idHospital;
    }

    public EntPersona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(EntPersona idPersona) {
        this.idPersona = idPersona;
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
        if (!(object instanceof EntPaciente)) {
            return false;
        }
        EntPaciente other = (EntPaciente) object;
        if ((this.idPaciente == null && other.idPaciente != null) || (this.idPaciente != null && !this.idPaciente.equals(other.idPaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntPaciente[ idPaciente=" + idPaciente + " ]";
    }

    public EntAntecedentes getEntAntecedentes() {
        return entAntecedentes;
    }

    public void setEntAntecedentes(EntAntecedentes entAntecedentes) {
        this.entAntecedentes = entAntecedentes;
    }

    @XmlTransient
    public List<EntSintomas> getEntSintomasList() {
        return entSintomasList;
    }

    public void setEntSintomasList(List<EntSintomas> entSintomasList) {
        this.entSintomasList = entSintomasList;
    }
    
}
