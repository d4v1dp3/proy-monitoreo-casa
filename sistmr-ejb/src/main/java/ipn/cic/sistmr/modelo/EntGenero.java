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
@Table(name = "RM_GENERO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntGenero.findAll", query = "SELECT e FROM EntGenero e"),
    @NamedQuery(name = "EntGenero.findByIdGenero", query = "SELECT e FROM EntGenero e WHERE e.idGenero = :idGenero"),
    @NamedQuery(name = "EntGenero.findByDescripcion", query = "SELECT e FROM EntGenero e WHERE e.descripcion = :descripcion")})
public class EntGenero implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_GENERO")
    private Short idGenero;
    @Size(max = 30)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idGenero", fetch = FetchType.LAZY)
    private List<EntPersona> entPersonaList;

    public EntGenero() {
    }

    public EntGenero(Short idGenero) {
        this.idGenero = idGenero;
    }

    public Short getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Short idGenero) {
        this.idGenero = idGenero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<EntPersona> getEntPersonaList() {
        return entPersonaList;
    }

    public void setEntPersonaList(List<EntPersona> entPersonaList) {
        this.entPersonaList = entPersonaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGenero != null ? idGenero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntGenero)) {
            return false;
        }
        EntGenero other = (EntGenero) object;
        if ((this.idGenero == null && other.idGenero != null) || (this.idGenero != null && !this.idGenero.equals(other.idGenero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntGenero[ idGenero=" + idGenero + " ]";
    }
    
}
