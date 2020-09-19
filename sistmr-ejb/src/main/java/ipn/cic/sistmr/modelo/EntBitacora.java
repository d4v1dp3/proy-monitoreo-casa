/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Entity
@Table(name = "RM_BITACORA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntBitacora.findAll", query = "SELECT e FROM EntBitacora e"),
    @NamedQuery(name = "EntBitacora.findByIdEntrada", query = "SELECT e FROM EntBitacora e WHERE e.idEntrada = :idEntrada"),
    @NamedQuery(name = "EntBitacora.findByFechaEntrada", query = "SELECT e FROM EntBitacora e WHERE e.fechaEntrada = :fechaEntrada")})
public class EntBitacora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ENTRADA")
    private Long idEntrada;
    @Column(name = "FECHA_ENTRADA")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrada;
    @JoinColumn(name = "ID_EVENTO", referencedColumnName = "ID_EVENTO")
    @ManyToOne(fetch = FetchType.LAZY)
    private EntEventobitacora idEvento;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private EntUsuario idUsuario;

    public EntBitacora() {
    }

    public EntBitacora(Long idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Long getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(Long idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public EntEventobitacora getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(EntEventobitacora idEvento) {
        this.idEvento = idEvento;
    }

    public EntUsuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(EntUsuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEntrada != null ? idEntrada.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntBitacora)) {
            return false;
        }
        EntBitacora other = (EntBitacora) object;
        if ((this.idEntrada == null && other.idEntrada != null) || (this.idEntrada != null && !this.idEntrada.equals(other.idEntrada))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntBitacora[ idEntrada=" + idEntrada + " ]";
    }
    
}
