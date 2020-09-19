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
@Table(name = "RM_MENU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntMenu.findAll", query = "SELECT e FROM EntMenu e"),
    @NamedQuery(name = "EntMenu.findByIdMenu", query = "SELECT e FROM EntMenu e WHERE e.idMenu = :idMenu"),
    @NamedQuery(name = "EntMenu.findByDescripcion", query = "SELECT e FROM EntMenu e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "EntMenu.findByRutaIcono", query = "SELECT e FROM EntMenu e WHERE e.rutaIcono = :rutaIcono"),
    @NamedQuery(name = "EntMenu.findByPosicion", query = "SELECT e FROM EntMenu e WHERE e.posicion = :posicion")})
public class EntMenu implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MENU")
    private Short idMenu;
    @Size(max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @Size(max = 100)
    @Column(name = "RUTA_ICONO")
    private String rutaIcono;
    @Column(name = "POSICION")
    private Short posicion;
    @JoinTable(name = "RM_ROL_MENU", joinColumns = {
        @JoinColumn(name = "ID_MENU", referencedColumnName = "ID_MENU")}, inverseJoinColumns = {
        @JoinColumn(name = "ID_ROL", referencedColumnName = "ID_ROL")})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<EntRol> entRolList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idMenu", fetch = FetchType.LAZY)
    private List<EntMenuOpcion> entMenuOpcionList;
    @OneToMany(mappedBy = "idSubMenu", fetch = FetchType.LAZY)
    private List<EntMenuOpcion> entMenuOpcionList1;

    public EntMenu() {
    }

    public EntMenu(Short idMenu) {
        this.idMenu = idMenu;
    }

    public Short getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Short idMenu) {
        this.idMenu = idMenu;
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

    public Short getPosicion() {
        return posicion;
    }

    public void setPosicion(Short posicion) {
        this.posicion = posicion;
    }

    @XmlTransient
    public List<EntRol> getEntRolList() {
        return entRolList;
    }

    public void setEntRolList(List<EntRol> entRolList) {
        this.entRolList = entRolList;
    }

    @XmlTransient
    public List<EntMenuOpcion> getEntMenuOpcionList() {
        return entMenuOpcionList;
    }

    public void setEntMenuOpcionList(List<EntMenuOpcion> entMenuOpcionList) {
        this.entMenuOpcionList = entMenuOpcionList;
    }

    @XmlTransient
    public List<EntMenuOpcion> getEntMenuOpcionList1() {
        return entMenuOpcionList1;
    }

    public void setEntMenuOpcionList1(List<EntMenuOpcion> entMenuOpcionList1) {
        this.entMenuOpcionList1 = entMenuOpcionList1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMenu != null ? idMenu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntMenu)) {
            return false;
        }
        EntMenu other = (EntMenu) object;
        if ((this.idMenu == null && other.idMenu != null) || (this.idMenu != null && !this.idMenu.equals(other.idMenu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntMenu[ idMenu=" + idMenu + " ]";
    }
    
}
