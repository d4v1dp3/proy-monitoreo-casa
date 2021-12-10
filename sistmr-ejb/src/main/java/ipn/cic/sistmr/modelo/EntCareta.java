/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.modelo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author J.Perez
 */
@Entity
@Table(name = "RM_CARETA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntCareta.findAll", query = "SELECT e FROM EntCareta e"),
    @NamedQuery(name = "EntCareta.findByIdCareta", query = "SELECT e FROM EntCareta e WHERE e.idCareta = :idCareta"),
    @NamedQuery(name = "EntCareta.findByFechaManufactura", query = "SELECT e FROM EntCareta e WHERE e.fechaManufactura = :fechaManufactura")})
public class EntCareta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_CARETA")
    private Long idCareta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FECHA_MANUFACTURA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaManufactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NO_SERIE")
    private long noSerie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entCareta", fetch = FetchType.LAZY)
    private List<EntCaretaHospital> entCaretaHospitalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "entCareta", fetch = FetchType.LAZY)
    private List<EntMedidas> entMedidasList;
    @OneToMany(mappedBy = "idCareta", fetch = FetchType.LAZY)
    private List<EntPaciente> entPacienteList;
    @JoinColumn(name = "ID_ESTADOCARETA", referencedColumnName = "ID_ESTADOCARETA")
    @ManyToOne(fetch = FetchType.LAZY)
    private EntEstadocareta idEstadocareta;

    public EntCareta() {
    }

    public EntCareta(Long idCareta) {
        this.idCareta = idCareta;
    }

    public EntCareta(Long idCareta, Date fechaManufactura) {
        this.idCareta = idCareta;
        this.fechaManufactura = fechaManufactura;
    }

    public Long getIdCareta() {
        return idCareta;
    }

    public void setIdCareta(Long idCareta) {
        this.idCareta = idCareta;
    }

    public Date getFechaManufactura() {
        return fechaManufactura;
    }

    public void setFechaManufactura(Date fechaManufactura) {
        this.fechaManufactura = fechaManufactura;
    }
    
    public void setFechaManufactura(String fechaManufactura){
        try {
            this.fechaManufactura = new SimpleDateFormat("dd-MM-yyyy").parse(fechaManufactura);
        } catch (ParseException e) {
        }
    }

    @XmlTransient
    public List<EntCaretaHospital> getEntCaretaHospitalList() {
        return entCaretaHospitalList;
    }

    public void setEntCaretaHospitalList(List<EntCaretaHospital> entCaretaHospitalList) {
        this.entCaretaHospitalList = entCaretaHospitalList;
    }

    @XmlTransient
    public List<EntMedidas> getEntMedidasList() {
        return entMedidasList;
    }

    public void setEntMedidasList(List<EntMedidas> entMedidasList) {
        this.entMedidasList = entMedidasList;
    }

    @XmlTransient
    public List<EntPaciente> getEntPacienteList() {
        return entPacienteList;
    }

    public void setEntPacienteList(List<EntPaciente> entPacienteList) {
        this.entPacienteList = entPacienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCareta != null ? idCareta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntCareta)) {
            return false;
        }
        EntCareta other = (EntCareta) object;
        if ((this.idCareta == null && other.idCareta != null) || (this.idCareta != null && !this.idCareta.equals(other.idCareta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntCareta[ idCareta=" + idCareta + " ]";
    }

    public long getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(long noSerie) {
        this.noSerie = noSerie;
    }

    public EntEstadocareta getIdEstadoCareta() {
        return idEstadocareta;
    }

    public void setIdEstadoCareta(EntEstadocareta idEstadocareta) {
        this.idEstadocareta = idEstadocareta;
    }

    
}
