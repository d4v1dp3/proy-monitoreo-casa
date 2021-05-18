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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
@Table(name = "RM_ESTADOPACIENTE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntEstadopaciente.findAll", query = "SELECT e FROM EntEstadopaciente e"),
    @NamedQuery(name = "EntEstadopaciente.findByIdEstadopaciente", query = "SELECT e FROM EntEstadopaciente e WHERE e.idEstadopaciente = :idEstadopaciente"),
    @NamedQuery(name = "EntEstadopaciente.findByDescripcion", query = "SELECT e FROM EntEstadopaciente e WHERE e.descripcion = :descripcion")})
public class EntEstadopaciente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADOPACIENTE")
    private Short idEstadopaciente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idEstadopaciente", fetch = FetchType.LAZY)
    private List<EntPaciente> entPacienteList;

    public EntEstadopaciente() {
    }

    public EntEstadopaciente(Short idEstadopaciente) {
        this.idEstadopaciente = idEstadopaciente;
    }

    public EntEstadopaciente(Short idEstadopaciente, String descripcion) {
        this.idEstadopaciente = idEstadopaciente;
        this.descripcion = descripcion;
    }

    public Short getIdEstadopaciente() {
        return idEstadopaciente;
    }

    public void setIdEstadopaciente(Short idEstadopaciente) {
        this.idEstadopaciente = idEstadopaciente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
        hash += (idEstadopaciente != null ? idEstadopaciente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntEstadopaciente)) {
            return false;
        }
        EntEstadopaciente other = (EntEstadopaciente) object;
        if ((this.idEstadopaciente == null && other.idEstadopaciente != null) || (this.idEstadopaciente != null && !this.idEstadopaciente.equals(other.idEstadopaciente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntEstadopaciente[ idEstadopaciente=" + idEstadopaciente + " ]";
    }
    
}
