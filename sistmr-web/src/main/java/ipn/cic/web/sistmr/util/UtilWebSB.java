/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.util;

import ipn.cic.sistmr.exception.UsuarioException;
import ipn.cic.sistmr.modelo.Auditable;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Singleton
@Lock(LockType.READ)
@PermitAll
@SecurityDomain("other")
public class UtilWebSB implements UtilWebSBLocal {
    private static final Logger logger = Logger.getLogger(UtilWebSB.class.getName());
    
    @EJB
    private UsuarioSBLocal usuarioSB;
    
    /**
     * Obtiene la direccion ip del cliente que realizo el request
     *
     * @return Direccion IP Remota
     */
    @Override
    public String getIPUsrRemoto() {
        HttpServletRequest request = getRequest();
        return request.getRemoteAddr();
    }

    /**
     * Obtiene el response del contexto de Faces.
     *
     * @return response
     */
    @Override
    public HttpServletResponse getResponse() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse httpServletResponse = (HttpServletResponse)context.getExternalContext().getResponse();
        return httpServletResponse;
    }

    /**
     * Obtiene el request del contexto de Faces.
     *
     * @return request
     */
    @Override
    public HttpServletRequest getRequest() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) context.getExternalContext().getRequest();
        return httpServletRequest;
    }
    
    
    /**
     * Obtiene el parametro especificado del Request
     *
     * @param nomParam Nombre del parametro buscado
     * @return Objeto de request, null si no se encuentra
     */
    @Override
    public String getParamRequest(String nomParam) {
        HttpServletRequest request = getRequest();
        return request.getParameter(nomParam);
    }
    
    /**
     * Obtiene el atributo especificado del Request
     *
     * @param nomAttr Nombre del atributo buscado
     * @return Objeto de request, null si no se encuentra
     */
    @Override
    public Object getAttrRequest(String nomAttr) {
        HttpServletRequest request = getRequest();
        return request.getAttribute(nomAttr);
    }
    
    
    /**
     * Pone en request un atributo.
     *
     * @param attrName Nombre del objeto
     * @param attr Objeto que se pone en sesion
     */
    @Override
    public void setAttrRequest(String attrName, Object attr) {
        HttpServletRequest request = getRequest();
        request.setAttribute(attrName, attr);
    }
    
    /**
     * Obtiene la sesion del contexto de Faces.
     *
     * @return sesion
     */
    @Override
    public HttpSession getSession() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        return session;
    }

    /**
     * Obtiene un objeto de sesion.
     *
     * @param attrName Nombre del atributo en sesion
     * @return Objeto en sesion
     */
    @Override
    public Object getAttrSession(String attrName) {
        HttpSession session = getSession();
        return session.getAttribute(attrName);
    }
    
    /**
     * Pone en sesion un atributo.
     *
     * @param attrName Nombre del objeto
     * @param attr Objeto que se pone en sesion
     */
    @Override
    public void setAttrSession(String attrName, Object attr) {
        HttpSession session = getSession();
        session.setAttribute(attrName, attr);
    }

    
    /**
     * Obtiene el nombre de usuario que esta actualmente autenticado
     *
     * @return Obtiene el nombre de usuario que esta actualmente autenticado, si
     * no existe devuelve null
     *
     */
    @Override
    public String getNameUserPrincipal() {
        Principal principal = getRequest().getUserPrincipal();
        if (principal == null) {
            return null;
        } else {
            return principal.getName().toUpperCase();
        }
    }

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
    @Override
    public String agregarCaracter(String cadena, char caracter, boolean antes, int tamTotal) {
        if (cadena == null) {
            return null;
        }
        String relleno = "";
        if (antes) {
            for (int i = 0; i < tamTotal - cadena.length(); i++) {
                relleno += caracter;
            }
            cadena = relleno + cadena;
        } else { // se rellena despues de la cadena
            for (int i = cadena.length(); i < tamTotal; i++) {
                cadena += caracter;
            }
        }
        return cadena;
    }

    /**
     * Formatea una fecha con el formato especificado
     *
     * @param fecha Fecha
     * @param formato Formato ej. dd/MM/yyyy
     * @return String de la fecha formateada
     */
    @Override
    public String formatDate(Date fecha, String formato) {
        DateFormat df = new SimpleDateFormat(formato, new Locale("es", "MX"));
        return df.format(fecha);
    }

    /**
     * Envia al response como attachment el archivo para ser descargado
     *
     * @param nombreArchivo Nombre del archivo
     * @param documento Archivo en si como un arreglo de bytes
     * @throws Exception
     */
    @Override
    public void descargarArchivo(String nombreArchivo, byte[] documento) throws Exception {
        HttpServletResponse response = getResponse();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\"");
        response.setHeader("Cache-Control", "max-age=30");
        response.setContentLength(documento.length);
        response.getOutputStream().write(documento);
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Envia al response como attachment el archivo para ser descargado
     *
     * @param nombreArchivo Nombre del archivo
     * @param documento Archivo en si como un arreglo de bytes
     * @throws Exception
     */
    @Override
    public void verArchivo(String nombreArchivo, byte[] documento) throws Exception {
        HttpServletResponse response = getResponse();
        response.setContentType("application/pdf");
        response.setHeader("Cache-Control", "max-age=30");
        response.setContentLength(documento.length);
        response.getOutputStream().write(documento);
        response.getOutputStream().close();
        FacesContext.getCurrentInstance().responseComplete();
    }

    /**
     * Verifica si existe un usuario autenticado por medio del request con el
     * user pricipal
     *
     * @return true - si existe un usuario autenticado, false - si no hay
     * usuario autenticado
     */
    @Override
    public Boolean existeUsrAutenticado() {
        return getNameUserPrincipal() != null;
    }

    /**
     * Convierte a mayusculas la cadena en formato de mexico
     *
     * @param cadena Cadena de entrada
     * @return Cadena en mayusculas
     */
    @Override
    public String toUpperMX(String cadena) {
        return cadena.toUpperCase(new Locale("es", "MX"));
    }

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
    @Override
    public void addMsg(String idPathComponent, String nomMsg, 
                       FacesMessage.Severity severity) {
        FacesContext.getCurrentInstance()
                    .addMessage(idPathComponent,Mensaje.getInstance()
                                .getMensaje(nomMsg, severity));
    }

    @Override
    public void addMsg(String idPathComponent, String nomMsg, Object[] paramsMsg, FacesMessage.Severity severity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       /**
     * Agrega el mensaje identificado con el parametro datail del archivo
     * ApplicationMessages.properties al componente identificado por el
     * parametro idPathComponent con la severidad especificada.
     *
     * @param idPathComponent Ruta de componentes separados por el caracter : a
     * partir del nombre del formulario actual. Ej:"frmAltaUsuario:txtNomUsuario"
     * @param message Objeto FacesMessage que se agregará al componente
     */
    @Override
    public void addMsg(String idPathComponent, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(idPathComponent, message);
    }

    /**
     * Agrega el mensaje identificado con el parametro datail del archivo
     * ApplicationMessages.properties al componente identificado por el
     * parametro idPathComponent con la severidad especificada.
     *
     * @param idPathComponent Ruta de componentes separados por el caracter : a
     * partir del nombre del formulario actual. Ej:"frmAltaUsuario:txtNomUsuario"
     * @param summary Resumen
     * @param detail Mensaje
     * @param severity Severidad del mensaje
     */
    @Override
    public void addMsg(String idPathComponent, String summary, String detail, FacesMessage.Severity severity) {
        FacesMessage message = new FacesMessage(severity, summary, detail);
        FacesContext.getCurrentInstance().addMessage(idPathComponent, message);
    }


    @Override
    public EntUsuario getUsrAutenticado() {
        EntUsuario usuario = null;
        try{
            usuario = usuarioSB.getUsuario(getNameUserPrincipal());
        }catch(UsuarioException ex){
            logger.log(Level.SEVERE, "Error al obtener usuario autenticado",ex);
        }
        return usuario;
    }

    /**
     * Verifica si el usuario autenticado en el sistema coincide con el rol
     * especificado como parametro
     *
     * @param rolConstantes Rol del usuario
     * @return true: si el parametro rolConstantes se encuentra autenticado en
     * el sistema, false: de lo contrario
     */
    @Override
    public boolean isUserInRole(String rolConstantes) {
        try {
            return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(rolConstantes);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void registraMovimientoAudit(Auditable entidad, String usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
