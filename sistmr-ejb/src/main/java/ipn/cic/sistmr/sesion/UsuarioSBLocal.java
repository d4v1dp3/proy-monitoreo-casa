/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.sesion;

import ipn.cic.sistmr.exception.IDUsuarioException;
import ipn.cic.sistmr.exception.SaveEntityException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.exception.UsuarioException;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPersona;
import ipn.cic.sistmr.modelo.EntRol;
import ipn.cic.sistmr.modelo.EntUsuario;
import java.util.List;
import javax.ejb.Local;

/**
 * Interfaz Local de Stateless Session Bean para manejo de usuarios
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Local
public interface UsuarioSBLocal {

    /**
     * Verifica si el Id de usuario existe en la base de datos, de ser así retorna true.
     * 
     * @param idUsuario identificador a revisar.
     * @return boolean True si el identificador existe.
     * 
     */
    boolean existeIdUsiario(String idUsuario); 
    
    /**
     * Persiste la entidad usuario en base de datos, retorna la entidad usuario 
     * persistida
     * 
     * @param usuario entindad a salvar.
     * @return EntUsuario entidad salvada.
     * @throws ipn.cic.sistmr.exception.SaveEntityException
     * 
     */
    EntUsuario saveUsuario(EntUsuario usuario) throws SaveEntityException, IDUsuarioException; 
    
    /**
     * Obtiene al usuario que tiene el nombre de usuario especificado como
     * parametro (debe ser unico)
     *
     * @param nomUsuario Nombre de usuario unico
     * @return Entidad EntUsuario
     * @throws UsuarioException
     */
    public EntUsuario getUsuario(String nomUsuario) throws UsuarioException;

    /**
     * Obtiene todos los roles del usuario identificado por el parametro
     * idUsuario en orden ascendente por el campo descripcion del rol
     *
     * @param idUsuario Identificador unico del usuario
     * @return Lista de entidades EntRol
     * @throws UsuarioException
     */
    public List<EntRol> getRoles(String idUsuario) throws UsuarioException;
    
    /**
     * Obtiene todos los usuarios de la aplicación
     * @return Lista de entidades usuarios.
     * @throws UsuarioException
     */
    public List<EntUsuario> getUsuarios()throws UsuarioException;
    public EntUsuario updateUsuario(EntUsuario usuario) throws UpdateEntityException;
    public EntPersona getPersonaDeUsuario(EntUsuario usuario) throws UsuarioException;
    public EntUsuario getUsuariobyEmail(String email);
    public EntUsuario getUsuarioDeMedico(EntMedico medico);
    public EntUsuario getUsuarioDePaciente(EntPaciente paciente);
    public EntUsuario getUsuarioCifrado(String idusuario,String cifra);
}
