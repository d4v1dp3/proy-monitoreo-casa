/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "RM_PACIENTE_MEDICO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntPacienteMedico.findAll", query = "SELECT e FROM EntPacienteMedico e"),
    @NamedQuery(name = "EntPacienteMedico.findByFechaInicio", query = "SELECT e FROM EntPacienteMedico e WHERE e.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "EntPacienteMedico.findByIdPaciente", query = "SELECT e FROM EntPacienteMedico e WHERE e.entPacienteMedicoPK.idPaciente = :idPaciente"),
    @NamedQuery(name = "EntPacienteMedico.findByIdMedico", query = "SELECT e FROM EntPacienteMedico e WHERE e.entPacienteMedicoPK.idMedico = :idMedico")})
public class EntPacienteMedico implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EntPacienteMedicoPK entPacienteMedicoPK;
    @Column(name = "FECHA_INICIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInicio;
    @JoinColumn(name = "ID_MEDICO", referencedColumnName = "ID_MEDICO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EntMedico entMedico;
    @JoinColumn(name = "ID_PACIENTE", referencedColumnName = "ID_PACIENTE", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private EntPaciente entPaciente;

    public EntPacienteMedico() {
    }

    public EntPacienteMedico(EntPacienteMedicoPK entPacienteMedicoPK) {
        this.entPacienteMedicoPK = entPacienteMedicoPK;
    }

    public EntPacienteMedico(long idPaciente, int idMedico) {
        this.entPacienteMedicoPK = new EntPacienteMedicoPK(idPaciente, idMedico);
    }

    public EntPacienteMedicoPK getEntPacienteMedicoPK() {
        return entPacienteMedicoPK;
    }

    public void setEntPacienteMedicoPK(EntPacienteMedicoPK entPacienteMedicoPK) {
        this.entPacienteMedicoPK = entPacienteMedicoPK;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public EntMedico getEntMedico() {
        return entMedico;
    }

    public void setEntMedico(EntMedico entMedico) {
        this.entMedico = entMedico;
    }

    public EntPaciente getEntPaciente() {
        return entPaciente;
    }

    public void setEntPaciente(EntPaciente entPaciente) {
        this.entPaciente = entPaciente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entPacienteMedicoPK != null ? entPacienteMedicoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntPacienteMedico)) {
            return false;
        }
        EntPacienteMedico other = (EntPacienteMedico) object;
        if ((this.entPacienteMedicoPK == null && other.entPacienteMedicoPK != null) || (this.entPacienteMedicoPK != null && !this.entPacienteMedicoPK.equals(other.entPacienteMedicoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntPacienteMedico[ entPacienteMedicoPK=" + entPacienteMedicoPK + " ]";
    }
    
}
