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
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Embeddable
public class EntCaretaHospitalPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CARETA")
    private long idCareta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_HOSPITAL")
    private int idHospital;

    public EntCaretaHospitalPK() {
    }

    public EntCaretaHospitalPK(long idCareta, int idHospital) {
        this.idCareta = idCareta;
        this.idHospital = idHospital;
    }

    public long getIdCareta() {
        return idCareta;
    }

    public void setIdCareta(long idCareta) {
        this.idCareta = idCareta;
    }

    public int getIdHospital() {
        return idHospital;
    }

    public void setIdHospital(int idHospital) {
        this.idHospital = idHospital;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCareta;
        hash += (int) idHospital;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntCaretaHospitalPK)) {
            return false;
        }
        EntCaretaHospitalPK other = (EntCaretaHospitalPK) object;
        if (this.idCareta != other.idCareta) {
            return false;
        }
        if (this.idHospital != other.idHospital) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntCaretaHospitalPK[ idCareta=" + idCareta + ", idHospital=" + idHospital + " ]";
    }
    
}
