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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Entity
@Table(name = "RM_CARETA_HOSPITAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntCaretaHospital.findAll", query = "SELECT e FROM EntCaretaHospital e"),
    @NamedQuery(name = "EntCaretaHospital.findByFechaAsignacion", query = "SELECT e FROM EntCaretaHospital e WHERE e.fechaAsignacion = :fechaAsignacion"),
    @NamedQuery(name = "EntCaretaHospital.findByIdCareta", query = "SELECT e FROM EntCaretaHospital e WHERE e.entCaretaHospitalPK.idCareta = :idCareta"),
    @NamedQuery(name = "EntCaretaHospital.findByIdHospital", query = "SELECT e FROM EntCaretaHospital e WHERE e.entCaretaHospitalPK.idHospital = :idHospital")})
public class EntCaretaHospital implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EntCaretaHospitalPK entCaretaHospitalPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "FECHA_ASIGNACION")
    private String fechaAsignacion;
    @JoinColumn(name = "ID_CARETA", referencedColumnName = "ID_CARETA", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EntCareta entCareta;
    @JoinColumn(name = "ID_HOSPITAL", referencedColumnName = "ID_HOSPITAL", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EntHospital entHospital;

    public EntCaretaHospital() {
    }

    public EntCaretaHospital(EntCaretaHospitalPK entCaretaHospitalPK) {
        this.entCaretaHospitalPK = entCaretaHospitalPK;
    }

    public EntCaretaHospital(EntCaretaHospitalPK entCaretaHospitalPK, String fechaAsignacion) {
        this.entCaretaHospitalPK = entCaretaHospitalPK;
        this.fechaAsignacion = fechaAsignacion;
    }

    public EntCaretaHospital(long idCareta, int idHospital) {
        this.entCaretaHospitalPK = new EntCaretaHospitalPK(idCareta, idHospital);
    }

    public EntCaretaHospitalPK getEntCaretaHospitalPK() {
        return entCaretaHospitalPK;
    }

    public void setEntCaretaHospitalPK(EntCaretaHospitalPK entCaretaHospitalPK) {
        this.entCaretaHospitalPK = entCaretaHospitalPK;
    }

    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public EntCareta getEntCareta() {
        return entCareta;
    }

    public void setEntCareta(EntCareta entCareta) {
        this.entCareta = entCareta;
    }

    public EntHospital getEntHospital() {
        return entHospital;
    }

    public void setEntHospital(EntHospital entHospital) {
        this.entHospital = entHospital;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entCaretaHospitalPK != null ? entCaretaHospitalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntCaretaHospital)) {
            return false;
        }
        EntCaretaHospital other = (EntCaretaHospital) object;
        if ((this.entCaretaHospitalPK == null && other.entCaretaHospitalPK != null) || (this.entCaretaHospitalPK != null && !this.entCaretaHospitalPK.equals(other.entCaretaHospitalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntCaretaHospital[ entCaretaHospitalPK=" + entCaretaHospitalPK + " ]";
    }
    
}
