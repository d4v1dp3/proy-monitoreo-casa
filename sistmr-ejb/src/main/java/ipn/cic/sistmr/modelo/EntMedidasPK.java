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
public class EntMedidasPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PACIENTE")
    private long idPaciente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_CARETA")
    private long idCareta;
    @Basic(optional = false)
    @Column(name = "ID_MEDICION")
    private long idMedicion;

    public EntMedidasPK() {
    }

    public EntMedidasPK(long idPaciente, long idCareta, long idMedicion) {
        this.idPaciente = idPaciente;
        this.idCareta = idCareta;
        this.idMedicion = idMedicion;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public long getIdCareta() {
        return idCareta;
    }

    public void setIdCareta(long idCareta) {
        this.idCareta = idCareta;
    }

    public long getIdMedicion() {
        return idMedicion;
    }

    public void setIdMedicion(long idMedicion) {
        this.idMedicion = idMedicion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPaciente;
        hash += (int) idCareta;
        hash += (int) idMedicion;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntMedidasPK)) {
            return false;
        }
        EntMedidasPK other = (EntMedidasPK) object;
        if (this.idPaciente != other.idPaciente) {
            return false;
        }
        if (this.idCareta != other.idCareta) {
            return false;
        }
        if (this.idMedicion != other.idMedicion) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntMedidasPK[ idPaciente=" + idPaciente + ", idCareta=" + idCareta + ", idMedicion=" + idMedicion + " ]";
    }
    
}
