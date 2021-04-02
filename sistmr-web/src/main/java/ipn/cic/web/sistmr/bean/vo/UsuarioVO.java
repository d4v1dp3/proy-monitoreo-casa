/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.vo;

import java.io.Serializable;


/**
 *
 * @author J.PEREZ
 */
public class UsuarioVO implements Serializable {

    private String idUsuario;
    private String contrasenia;
    private Boolean activo;
    private String email;
    private Integer idPersona;

    public UsuarioVO() {
    }

    public UsuarioVO(String idUsuario, String contrasenia, Boolean activo, String email, Integer idPersona) {
        this.idUsuario = idUsuario;
        this.contrasenia = contrasenia;
        this.activo = activo;
        this.email = email;
        this.idPersona = idPersona;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

}
