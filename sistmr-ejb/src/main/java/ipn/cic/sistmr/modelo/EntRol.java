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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "RM_ROL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntRol.findAll", query = "SELECT e FROM EntRol e"),
    @NamedQuery(name = "EntRol.findByIdRol", query = "SELECT e FROM EntRol e WHERE e.idRol = :idRol"),
    @NamedQuery(name = "EntRol.findByDescripcion", query = "SELECT e FROM EntRol e WHERE e.descripcion = :descripcion")})
public class EntRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ROL")
    private Short idRol;
    @Size(max = 50)
    @Column(name = "Descripcion")
    private String descripcion;
    @JoinTable(name = "RM_USUARIO_ROL", joinColumns = {
        @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<EntUsuario> entUsuarioList;
    @ManyToMany(mappedBy = "entRolList", fetch = FetchType.LAZY)
    private List<EntMenu> entMenuList;

    public EntRol() {
    }

    public EntRol(Short idRol) {
        this.idRol = idRol;
    }

    public Short getIdRol() {
        return idRol;
    }

    public void setIdRol(Short idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<EntUsuario> getEntUsuarioList() {
        return entUsuarioList;
    }

    public void setEntUsuarioList(List<EntUsuario> entUsuarioList) {
        this.entUsuarioList = entUsuarioList;
    }

    @XmlTransient
    public List<EntMenu> getEntMenuList() {
        return entMenuList;
    }

    public void setEntMenuList(List<EntMenu> entMenuList) {
        this.entMenuList = entMenuList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRol != null ? idRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntRol)) {
            return false;
        }
        EntRol other = (EntRol) object;
        if ((this.idRol == null && other.idRol != null) || (this.idRol != null && !this.idRol.equals(other.idRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntRol[ idRol=" + idRol + " ]";
    }
    
}
