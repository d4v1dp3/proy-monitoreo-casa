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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "RM_MEDICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntMedico.findAll", query = "SELECT e FROM EntMedico e"),
    @NamedQuery(name = "EntMedico.findByIdMedico", query = "SELECT e FROM EntMedico e WHERE e.idMedico = :idMedico"),
    @NamedQuery(name = "EntMedico.findByCedulaProf", query = "SELECT e FROM EntMedico e WHERE e.cedulaProf = :cedulaProf"),
    @NamedQuery(name = "EntMedico.findByCelular", query = "SELECT e FROM EntMedico e WHERE e.celular = :celular")})
public class EntMedico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_MEDICO")
    private Integer idMedico;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "CEDULA_PROF")
    private String cedulaProf;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "CELULAR")
    private String celular;
    @ManyToMany(mappedBy = "entMedicoList", fetch = FetchType.LAZY)
    private List<EntHospital> entHospitalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entMedico", fetch = FetchType.EAGER)
    private List<EntPacienteMedico> entPacienteMedicoList;
    @JoinColumn(name = "ID_PERSONA", referencedColumnName = "ID_PERSONA")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EntPersona idPersona;

    public EntMedico() {
    }

    public EntMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public EntMedico(Integer idMedico, String cedulaProf, String celular) {
        this.idMedico = idMedico;
        this.cedulaProf = cedulaProf;
        this.celular = celular;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getCedulaProf() {
        return cedulaProf;
    }

    public void setCedulaProf(String cedulaProf) {
        this.cedulaProf = cedulaProf;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    @XmlTransient
    public List<EntHospital> getEntHospitalList() {
        return entHospitalList;
    }

    public void setEntHospitalList(List<EntHospital> entHospitalList) {
        this.entHospitalList = entHospitalList;
    }

    @XmlTransient
    public List<EntPacienteMedico> getEntPacienteMedicoList() {
        return entPacienteMedicoList;
    }

    public void setEntPacienteMedicoList(List<EntPacienteMedico> entPacienteMedicoList) {
        this.entPacienteMedicoList = entPacienteMedicoList;
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
        hash += (idMedico != null ? idMedico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntMedico)) {
            return false;
        }
        EntMedico other = (EntMedico) object;
        if ((this.idMedico == null && other.idMedico != null) || (this.idMedico != null && !this.idMedico.equals(other.idMedico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntMedico[ idMedico=" + idMedico + " ]";
    }
    
}
