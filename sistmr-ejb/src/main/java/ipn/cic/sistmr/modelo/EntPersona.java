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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Entity
@Table(name = "RM_PERSONA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntPersona.findAll", query = "SELECT e FROM EntPersona e"),
    @NamedQuery(name = "EntPersona.findByIdPersona", query = "SELECT e FROM EntPersona e WHERE e.idPersona = :idPersona"),
    @NamedQuery(name = "EntPersona.findByNombre", query = "SELECT e FROM EntPersona e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "EntPersona.findByPrimerApellido", query = "SELECT e FROM EntPersona e WHERE e.primerApellido = :primerApellido"),
    @NamedQuery(name = "EntPersona.findBySegundoApellido", query = "SELECT e FROM EntPersona e WHERE e.segundoApellido = :segundoApellido"),
    @NamedQuery(name = "EntPersona.findByCurp", query = "SELECT e FROM EntPersona e WHERE e.curp = :curp"),
    @NamedQuery(name = "EntPersona.findByEdad", query = "SELECT e FROM EntPersona e WHERE e.edad = :edad")})
public class EntPersona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PERSONA")
    private Integer idPersona;
    @Size(max = 50)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 50)
    @Column(name = "PRIMER_APELLIDO")
    private String primerApellido;
    @Size(max = 50)
    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;
    @Size(max = 50)
    @Column(name = "CURP")
    private String curp;
    @Basic(optional = false)
    @Column(name = "EDAD")
    private Integer edad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersona", fetch = FetchType.LAZY)
    private List<EntMedico> entMedicoList;
    @JoinColumn(name = "ID_GENERO", referencedColumnName = "ID_GENERO")
    @ManyToOne(fetch = FetchType.LAZY)
    private EntGenero idGenero;
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.LAZY)
    private List<EntUsuario> entUsuarioList;
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.LAZY)
    private List<EntPaciente> entPacienteList;

    public EntPersona() {
    }

    public EntPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
    @XmlTransient
    public List<EntUsuario> getEntUsuarioList() {
        return entUsuarioList;
    }

    public void setEntUsuarioList(List<EntUsuario> entUsuarioList) {
        this.entUsuarioList = entUsuarioList;
    }

    @XmlTransient
    public List<EntMedico> getEntMedicoList() {
        return entMedicoList;
    }

    public void setEntMedicoList(List<EntMedico> entMedicoList) {
        this.entMedicoList = entMedicoList;
    }

    public EntGenero getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(EntGenero idGenero) {
        this.idGenero = idGenero;
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
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntPersona)) {
            return false;
        }
        EntPersona other = (EntPersona) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ipn.cic.sistmr.modelo.EntPersona[ idPersona=" + idPersona + " ]";
    }
    
}
