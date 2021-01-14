/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.vo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author J.PEREZ
 */
public class PersonaVO implements Serializable{

    private Integer idPersona;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String curp;
    private Integer idGenero;
    
    public PersonaVO(){}
    
    public PersonaVO(Integer idPersona, String nombre, String primerApellido, String segundoApellido, String curp, Integer idGenero) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.curp = curp;
        this.idGenero = idGenero;
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

//    public List<EntMedico> getEntMedicoList() {
//        return entMedicoList;
//    }
//
//    public void setEntMedicoList(List<EntMedico> entMedicoList) {
//        this.entMedicoList = entMedicoList;
//    }

    public Integer getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(Integer idGenero) {
        this.idGenero = idGenero;
    }

//    public List<EntUsuario> getEntUsuarioList() {
//        return entUsuarioList;
//    }
//
//    public void setEntUsuarioList(List<EntUsuario> entUsuarioList) {
//        this.entUsuarioList = entUsuarioList;
//    }
//
//    public List<EntPaciente> getEntPacienteList() {
//        return entPacienteList;
//    }
//
//    public void setEntPacienteList(List<EntPaciente> entPacienteList) {
//        this.entPacienteList = entPacienteList;
//    }
    
}
