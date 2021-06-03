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
 * @author J.Perez
 */
@Entity
@Table(name = "RM_ESTADOCARETA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntEstadocareta.findAll", query = "SELECT e FROM EntEstadocareta e"),
    @NamedQuery(name = "EntEstadocareta.findByIdEstadocareta", query = "SELECT e FROM EntEstadocareta e WHERE e.idEstadocareta = :idEstadocareta"),
    @NamedQuery(name = "EntEstadocareta.findByDescripcion", query = "SELECT e FROM EntEstadocareta e WHERE e.descripcion = :descripcion")})
public class EntEstadocareta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADOCARETA")
    private Short idEstadocareta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idEstadocareta", fetch = FetchType.LAZY)
    private List<EntCareta> entCaretaList;

    public EntEstadocareta() {
    }

    public EntEstadocareta(Short idEstadocareta) {
        this.idEstadocareta = idEstadocareta;
    }

    public EntEstadocareta(Short idEstadocareta, String descripcion) {
        this.idEstadocareta = idEstadocareta;
        this.descripcion = descripcion;
    }

    public Short getIdEstadoCareta() {
        return idEstadocareta;
    }

    public void setIdEstadoCareta(Short idEstadocareta) {
        this.idEstadocareta = idEstadocareta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<EntCareta> getEntCaretaList() {
        return entCaretaList;
    }

    public void setEntCaretaList(List<EntCareta> entCaretaList) {
        this.entCaretaList = entCaretaList;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstadocareta != null ? idEstadocareta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntEstadocareta)) {
            return false;
        }
        EntEstadocareta other = (EntEstadocareta) object;
        if ((this.idEstadocareta == null && other.idEstadocareta != null) || (this.idEstadocareta != null && !this.idEstadocareta.equals(other.idEstadocareta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntEstadocareta[ idEstadocareta=" + idEstadocareta + " ]";
    }
    
}
