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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Entity
@Table(name = "RM_HOSPITAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntHospital.findAll", query = "SELECT e FROM EntHospital e"),
    @NamedQuery(name = "EntHospital.findByIdHospital", query = "SELECT e FROM EntHospital e WHERE e.idHospital = :idHospital"),
    @NamedQuery(name = "EntHospital.findByNombre", query = "SELECT e FROM EntHospital e WHERE e.nombre = :nombre")
})
public class EntHospital implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_HOSPITAL")
    private Integer idHospital;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "UBICACION_GEO")
    private String ubicacionGeo;
    @Size(max = 50)
    @Column(name = "TEL_EMERGENCIAS")
    private String telEmergencias;
    @Size(max = 50)
    @Column(name = "DIR_CALLE")
    private String dirCalle;
    @Column(name = "NUMERO")
    private Integer numero;
    @Size(max = 50)
    @Column(name = "COLONIA")
    private String colonia;
    @Size(max = 50)
    @Column(name = "MUNICIPIO")
    private String municipio;
    @Size(max = 50)
    @Column(name = "ESTADO")
    private String estado;
    @JoinTable(name = "RM_HOSPITAL_MEDICO", joinColumns = {
        @JoinColumn(name = "ID_HOSPITAL", referencedColumnName = "ID_HOSPITAL")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_MEDICO", referencedColumnName = "ID_MEDICO")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<EntMedico> entMedicoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entHospital", fetch = FetchType.LAZY)
    private List<EntCaretaHospital> entCaretaHospitalList;
    @OneToMany(mappedBy = "idHospital", fetch = FetchType.LAZY)
    private List<EntPaciente> entPacienteList;

    public EntHospital() {
    }

    public EntHospital(Integer idHospital) {
        this.idHospital = idHospital;
    }

    public EntHospital(Integer idHospital, String nombre, String ubicacionGeo) {
        this.idHospital = idHospital;
        this.nombre = nombre;
        this.ubicacionGeo = ubicacionGeo;
    }

    public Integer getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(Integer idHospital) {
        this.idHospital = idHospital;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacionGeo() {
        return ubicacionGeo;
    }

    public void setUbicacionGeo(String ubicacionGeo) {
        this.ubicacionGeo = ubicacionGeo;
    }

    public String getTelEmergencias() {
        return telEmergencias;
    }

    public void setTelEmergencias(String telEmergencias) {
        this.telEmergencias = telEmergencias;
    }

    public String getDirCalle() {
        return dirCalle;
    }

    public void setDirCalle(String dirCalle) {
        this.dirCalle = dirCalle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<EntMedico> getEntMedicoList() {
        return entMedicoList;
    }

    public void setEntMedicoList(List<EntMedico> entMedicoList) {
        this.entMedicoList = entMedicoList;
    }

    @XmlTransient
    public List<EntCaretaHospital> getEntCaretaHospitalList() {
        return entCaretaHospitalList;
    }

    public void setEntCaretaHospitalList(List<EntCaretaHospital> entCaretaHospitalList) {
        this.entCaretaHospitalList = entCaretaHospitalList;
    }

    @XmlTransient
    public List<EntPaciente> getEntPacienteList() {
        return entPacienteList;
    }

    public void setEntPacienteList(List<EntPaciente> entPacienteList) {
        this.entPacienteList = entPacienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHospital != null ? idHospital.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntHospital)) {
            return false;
        }
        EntHospital other = (EntHospital) object;
        if ((this.idHospital == null && other.idHospital != null) || (this.idHospital != null && !this.idHospital.equals(other.idHospital))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntHospital[ idHospital=" + idHospital + " ]";
    }
    
}
