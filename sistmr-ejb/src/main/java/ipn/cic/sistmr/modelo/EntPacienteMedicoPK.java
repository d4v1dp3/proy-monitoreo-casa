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
public class EntPacienteMedicoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PACIENTE")
    private long idPaciente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MEDICO")
    private int idMedico;

    public EntPacienteMedicoPK() {
    }

    public EntPacienteMedicoPK(long idPaciente, int idMedico) {
        this.idPaciente = idPaciente;
        this.idMedico = idMedico;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPaciente;
        hash += (int) idMedico;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntPacienteMedicoPK)) {
            return false;
        }
        EntPacienteMedicoPK other = (EntPacienteMedicoPK) object;
        if (this.idPaciente != other.idPaciente) {
            return false;
        }
        if (this.idMedico != other.idMedico) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntPacienteMedicoPK[ idPaciente=" + idPaciente + ", idMedico=" + idMedico + " ]";
    }
    
}
