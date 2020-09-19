/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.util;

import ipn.cic.sistmr.modelo.Auditable;
import ipn.cic.sistmr.modelo.EntUsuario;
import java.util.Date;
import javax.ejb.Local;
import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Interfaz Local de Singleton Session Bean para para manejo de metodos
 * utilitarios de la aplicacion como web y sesion
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Local
public interface UtilWebSBLocal {
     /**
     * Obtiene la direccion ip del cliente que realizo el request
     *
     * @return Direccion IP Remota
     */
    public String getIPUsrRemoto();

    /**
     * Obtiene el response del contexto de Faces.
     *
     * @return response
     */
    public HttpServletResponse getResponse();

    /**
     * Obtiene el request del contexto de Faces.
     *
     * @return request
     */
    public HttpServletRequest getRequest();

    /**
     * Obtiene el parametro especificado del Request
     *
     * @param nomParam Nombre del parametro buscado
     * @return Objeto de request, null si no se encuentra
     */
    public String getParamRequest(String nomParam);

    /**
     * Obtiene el atributo especificado del Request
     *
     * @param nomAttr Nombre del atributo buscado
     * @return Objeto de request, null si no se encuentra
     */
    public Object getAttrRequest(String nomAttr);

    /**
     * Pone en request un atributo.
     *
     * @param attrName Nombre del objeto
     * @param attr Objeto que se pone en sesion
     */
    public void setAttrRequest(String attrName, Object attr);

    /**
     * Obtiene la sesion del contexto de Faces.
     *
     * @return sesion
     */
    public HttpSession getSession();

    /**
     * Obtiene un objeto de sesion.
     *
     * @param attrName Nombre del atributo en sesion
     * @return Objeto en sesion
     */
    public Object getAttrSession(String attrName);

    /**
     * Pone en sesion un atributo.
     *
     * @param attrName Nombre del objeto
     * @param attr Objeto que se pone en sesion
     */
    public void setAttrSession(String attrName, Object attr);

    /**
     * Obtiene el nombre de usuario del usuario que esta actualmente autenticado
     *
     * @return Obtiene el nombre de usuario que esta actualmente autenticado, si
     * no existe devuelve null
     *
     */
    public String getNameUserPrincipal();

    /**
     * Agrega caracteres antes o despues de la cadena especificada
     *
     * @param cadena Cadena a agregarle caracteres
     * @param caracter Caracter para rellenar
     * @param antes Si es true antes de la cadena y si es false se rellena
     * despues de la cadena
     * @param tamTotal Longitud total de la cadena incluyendo el relleno
     * @return Cadena rellena de caracteres de tamano total. Si el parametro
     * tamTotal es menor que la longitud de la cadena entonces se regresa la
     * misma cadena sin cambio alguno. Si la cadena es null se regresa null.
     */
    public String agregarCaracter(String cadena, char caracter, boolean antes, int tamTotal);

    /**
     * Formatea una fecha con el formato especificado
     *
     * @param fecha Fecha
     * @param formato Formato ej. dd/MM/yyyy
     * @return String de la fecha formateada
     */
    public String formatDate(Date fecha, String formato);

    /**
     * Envia al response como attachment el archivo para ser descargado
     *
     * @param nombreArchivo Nombre del archivo
     * @param documento Archivo en si como un arreglo de bytes
     * @throws Exception
     */
    public void descargarArchivo(String nombreArchivo, byte[] documento) throws Exception;

    /**
     * Envia al response como attachment el archivo para ser descargado
     *
     * @param nombreArchivo Nombre del archivo
     * @param documento Archivo en si como un arreglo de bytes
     * @throws Exception
     */
    public void verArchivo(String nombreArchivo, byte[] documento) throws Exception;

    /**
     * Verifica si existe un usuario autenticado por medio del request con el
     * user pricipal
     *
     * @return true - si existe un usuario autenticado, false - si no hay
     * usuario autenticado
     */
    public Boolean existeUsrAutenticado();

    /**
     * Convierte a mayusculas la cadena en formato de mexico
     *
     * @param cadena Cadena de entrada
     * @return Cadena en mayusculas
     */
    public String toUpperMX(String cadena);

    /**
     * Agrega el mensaje identificado con el parametro nomMsg del archivo
     * ApplicationMessages.properties al componente identificado por el
     * parametro idPathComponent con la severidad especificada.
     *
     * @param idPathComponent Ruta de componentes separados por el caracter : a
     * partir del nombre del formulario actual. Ej:
     * "frmAltaUsuario:txtNomUsuario"
     * @param nomMsg Identifica la llave del archivo ApplicationMessage para
     * obtener dicho mensaje
     * @param severity Severidad del mensaje
     */
    public void addMsg(String idPathComponent, String nomMsg, FacesMessage.Severity severity);

    /**
     * Agrega el mensaje identificado con el parametro nomMsg del archivo
     * ApplicationMessages.properties al componente identificado por el
     * parametro idPathComponent con la severidad especificada.
     *
     * @param idPathComponent Ruta de componentes separados por el caracter : a
     * partir del nombre del formulario actual. Ej:
     * "frmAltaUsuario:txtNomUsuario"
     * @param nomMsg Identifica la llave del archivo ApplicationMessage para
     * obtener dicho mensaje
     * @param paramsMsg Valores que sustituiran las llaves {0}, {1}, etc. dentro
     * del mensaje
     * @param severity Severidad del mensaje
     */
    public void addMsg(String idPathComponent, String nomMsg, Object[] paramsMsg, FacesMessage.Severity severity);
    
    /**
     * Agrega el mensaje identificado con el parametro nomMsg del archivo
     * ApplicationMessages.properties al componente identificado por el
     * parametro idPathComponent con la severidad especificada.
     *
     * @param idPathComponent Ruta de componentes separados por el caracter : a
     * partir del nombre del formulario actual. Ej:
     * "frmAltaUsuario:txtNomUsuario"
     * @param summary resumen
     * @param detail mensaje a mostrar
     * @param severity Severidad del mensaje
     */
    public void addMsg(String idPathComponent, String summary, String detail, FacesMessage.Severity severity );
    
    /**
     * Agrega el mensaje identificado con el parametro nomMsg del archivo
     * ApplicationMessages.properties al componente identificado por el
     * parametro idPathComponent con la severidad especificada.
     *
     * @param idPathComponent Ruta de componentes separados por el caracter : a
     * partir del nombre del formulario actual. Ej:
     * "frmAltaUsuario:txtNomUsuario"
     * @param message FacesMessage con el mensaje a agregar.
     */
    public void addMsg(String idPathComponent, FacesMessage message);
    

    /**
     * Obtiene a la entidad usuario autenticado
     *
     * @return usuario autenticado
     */
    public EntUsuario getUsrAutenticado();
   
     /**
     * Verifica si el usuario autenticado en el sistema coincide con el rol especificado
     * como parametro
     * @param rolConstantes Rol del usuario
     * @return true: si el parametro rolConstantes se encuentra autenticado en
     * el sistema, false: de lo contrario
     */
    public boolean isUserInRole(String rolConstantes);
    
    /**
     * Agrega los campos de auditoria para registro de algún movimiento
     * @param entidad entidad que implemente la interfaz auditable
     * @param usuario usuario que registra el movimiento.
     */
    public void registraMovimientoAudit(Auditable entidad, String usuario);
}
