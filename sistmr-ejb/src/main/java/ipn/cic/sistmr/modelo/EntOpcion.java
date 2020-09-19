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
@Table(name = "RM_OPCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntOpcion.findAll", query = "SELECT e FROM EntOpcion e"),
    @NamedQuery(name = "EntOpcion.findByIdOpcion", query = "SELECT e FROM EntOpcion e WHERE e.idOpcion = :idOpcion"),
    @NamedQuery(name = "EntOpcion.findByDescripcion", query = "SELECT e FROM EntOpcion e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "EntOpcion.findByRutaIcono", query = "SELECT e FROM EntOpcion e WHERE e.rutaIcono = :rutaIcono"),
    @NamedQuery(name = "EntOpcion.findByAccion", query = "SELECT e FROM EntOpcion e WHERE e.accion = :accion")})
public class EntOpcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_OPCION")
    private Integer idOpcion;
    @Size(max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 50)
    @Column(name = "RUTA_ICONO")
    private String rutaIcono;
    @Size(max = 100)
    @Column(name = "ACCION")
    private String accion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOpcion", fetch = FetchType.LAZY)
    private List<EntMenuOpcion> entMenuOpcionList;

    public EntOpcion() {
    }

    public EntOpcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public Integer getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRutaIcono() {
        return rutaIcono;
    }

    public void setRutaIcono(String rutaIcono) {
        this.rutaIcono = rutaIcono;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    @XmlTransient
    public List<EntMenuOpcion> getEntMenuOpcionList() {
        return entMenuOpcionList;
    }

    public void setEntMenuOpcionList(List<EntMenuOpcion> entMenuOpcionList) {
        this.entMenuOpcionList = entMenuOpcionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOpcion != null ? idOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntOpcion)) {
            return false;
        }
        EntOpcion other = (EntOpcion) object;
        if ((this.idOpcion == null && other.idOpcion != null) || (this.idOpcion != null && !this.idOpcion.equals(other.idOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntOpcion[ idOpcion=" + idOpcion + " ]";
    }
    
}
