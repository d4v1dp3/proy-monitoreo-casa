/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.pacient;

import ipn.cic.sistmr.exception.MedicoException;
import ipn.cic.sistmr.exception.NoExistePacienteException;
import ipn.cic.sistmr.exception.PacienteException;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntMedidas;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.MedicoSBLocal;
import ipn.cic.sistmr.sesion.PacienteSBLocal;
import ipn.cic.web.sistmr.util.Mensaje;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 * ManageBean que se utiliza para carga de usuarios en el sistema
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */

@Named(value = "dialMedicoPacienteMB")
@ViewScoped
public class DialMedicoPacienteMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DialMedicoPacienteMB.class.getName());


    private String medNombre="";
    private String medPrimerAp="";
    private String medSegundoAp="";
    private String medEmail = "";
    private String medTel = "";
    private String medCedula = "";

    public String getMedCedula() {
        return medCedula;
    }

    public void setMedCedula(String medCedula) {
        this.medCedula = medCedula;
    }
    

    public String getMedEmail() {
        return medEmail;
    }

    public void setMedEmail(String medEmail) {
        this.medEmail = medEmail;
    }

    public String getMedTel() {
        return medTel;
    }

    public void setMedTel(String medTel) {
        this.medTel = medTel;
    }

    public String getMedNombre() {
        return medNombre;
    }

    public void setMedNombre(String medNombre) {
        this.medNombre = medNombre;
    }

    public String getMedPrimerAp() {
        return medPrimerAp;
    }

    public void setMedPrimerAp(String medPrimerAp) {
        this.medPrimerAp = medPrimerAp;
    }

    public String getMedSegundoAp() {
        return medSegundoAp;
    }

    //Datos de Medico
    public void setMedSegundoAp(String medSegundoAp) {
        this.medSegundoAp = medSegundoAp;
    }
    

   
}
