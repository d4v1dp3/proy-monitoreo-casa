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
@Table(name = "RM_EVENTOBITACORA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntEventobitacora.findAll", query = "SELECT e FROM EntEventobitacora e"),
    @NamedQuery(name = "EntEventobitacora.findByIdEvento", query = "SELECT e FROM EntEventobitacora e WHERE e.idEvento = :idEvento"),
    @NamedQuery(name = "EntEventobitacora.findByDescripcion", query = "SELECT e FROM EntEventobitacora e WHERE e.descripcion = :descripcion")})
public class EntEventobitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_EVENTO")
    private Integer idEvento;
    @Size(max = 50)
    @Column(name = "DESCRIPCION")
    private String descripcion;
    @OneToMany(mappedBy = "idEvento", fetch = FetchType.LAZY)
    private List<EntBitacora> entBitacoraList;

    public EntEventobitacora() {
    }

    public EntEventobitacora(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public EntEventobitacora(Integer idEvento, String descripcion) {
        this.idEvento = idEvento;
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<EntBitacora> getEntBitacoraList() {
        return entBitacoraList;
    }

    public void setEntBitacoraList(List<EntBitacora> entBitacoraList) {
        this.entBitacoraList = entBitacoraList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntEventobitacora)) {
            return false;
        }
        EntEventobitacora other = (EntEventobitacora) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntEventobitacora[ idEvento=" + idEvento + " ]";
    }
    
}
