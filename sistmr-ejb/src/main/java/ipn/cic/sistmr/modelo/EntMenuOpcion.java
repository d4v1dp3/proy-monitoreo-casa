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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "RM_MENU_OPCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntMenuOpcion.findAll", query = "SELECT e FROM EntMenuOpcion e"),
    @NamedQuery(name = "EntMenuOpcion.findByIdMenuOpcion", query = "SELECT e FROM EntMenuOpcion e WHERE e.idMenuOpcion = :idMenuOpcion"),
    @NamedQuery(name = "EntMenuOpcion.findByPosicion", query = "SELECT e FROM EntMenuOpcion e WHERE e.posicion = :posicion")})
public class EntMenuOpcion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MENU_OPCION")
    private Integer idMenuOpcion;
    @Column(name = "POSICION")
    private Short posicion;
    @JoinColumn(name = "ID_MENU", referencedColumnName = "ID_MENU")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EntMenu idMenu;
    @JoinColumn(name = "ID_SUB_MENU", referencedColumnName = "ID_MENU")
    @ManyToOne(fetch = FetchType.LAZY)
    private EntMenu idSubMenu;
    @JoinColumn(name = "ID_OPCION", referencedColumnName = "ID_OPCION")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EntOpcion idOpcion;

    public EntMenuOpcion() {
    }

    public EntMenuOpcion(Integer idMenuOpcion) {
        this.idMenuOpcion = idMenuOpcion;
    }

    public Integer getIdMenuOpcion() {
        return idMenuOpcion;
    }

    public void setIdMenuOpcion(Integer idMenuOpcion) {
        this.idMenuOpcion = idMenuOpcion;
    }

    public Short getPosicion() {
        return posicion;
    }

    public void setPosicion(Short posicion) {
        this.posicion = posicion;
    }

    public EntMenu getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(EntMenu idMenu) {
        this.idMenu = idMenu;
    }

    public EntMenu getIdSubMenu() {
        return idSubMenu;
    }

    public void setIdSubMenu(EntMenu idSubMenu) {
        this.idSubMenu = idSubMenu;
    }

    public EntOpcion getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(EntOpcion idOpcion) {
        this.idOpcion = idOpcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenuOpcion != null ? idMenuOpcion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntMenuOpcion)) {
            return false;
        }
        EntMenuOpcion other = (EntMenuOpcion) object;
        if ((this.idMenuOpcion == null && other.idMenuOpcion != null) || (this.idMenuOpcion != null && !this.idMenuOpcion.equals(other.idMenuOpcion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntMenuOpcion[ idMenuOpcion=" + idMenuOpcion + " ]";
    }
    
}
