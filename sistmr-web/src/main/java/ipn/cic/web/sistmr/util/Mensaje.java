/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Singleton que genera un mensaje formateado con los parametros usando un
 * ResourceBundle para extraer los mensajes.
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
public class Mensaje {
    private static final Logger logger = Logger.getLogger(Mensaje.class.getSimpleName());
    private static Mensaje mensaje;
    private FacesContext facesContext;
    private ResourceBundle resourceBundle;
    
    public static Mensaje getInstance(){
        if(mensaje == null){
            mensaje = new Mensaje();
        }
        return mensaje;
    }
    
    /**
    * Constructor privado
    */
    private Mensaje(){
        facesContext = FacesContext.getCurrentInstance();
        String msgBundle = facesContext.getApplication().getMessageBundle();
        resourceBundle = ResourceBundle.getBundle(msgBundle);
    }
    
     /**
     * Obtiene un mensaje del resource bundle y lo formatea con los parametros
     * especificados.
     *
     * @param msg Nombre identificador en ApplicationMessages
     * @param params Valores que sustituiran las llaves del
     * mensaje({0},{1},etc.)
     * @param severity Severidad del mensaje
     * @return Mesaje formateado
     */
    public FacesMessage getMensaje(String msg, Object[] params, FacesMessage.Severity severity) {
        String patternMsg = "";
        try {
            patternMsg = resourceBundle.getString(msg);
        } catch (MissingResourceException mre) {
            logger.log(Level.SEVERE, "No se encontro el mensaje con llave: " + msg, mre);
            return new FacesMessage("");
        }
        MessageFormat mf = new MessageFormat(patternMsg);
        return new FacesMessage(severity, mf.format(params), mf.format(params));
    }
    
    /**
     * Obtiene un mensaje del resource bundle y crea dicho mensaje con la
     * severitdad especificada.
     *
     * @param msg Nombre identificador en ApplicationMessages
     * @param severity Severidad del mensaje
     * @return Mesaje
     */
    public FacesMessage getMensaje(String msg, FacesMessage.Severity severity) {
        String patternMsg = "";
        try {
            patternMsg = resourceBundle.getString(msg);
        } catch (MissingResourceException mre) {
            logger.log(Level.SEVERE, "No se encontro el mensaje con llave: " + msg, mre);
            return new FacesMessage("");
        }
        return new FacesMessage(severity, patternMsg, patternMsg);
    }

    /**
     * Obtiene un mensaje resumido y completo del resource bundle y crea dicho mensaje con la
     * severitdad especificada.
     * 
     * @param msgResum nombre mensaje resumen en el ApplicationMessages
     * @param msgComp  nombre mensaje completo en el ApplicationMessages
     * @param severity Severidad del mensaje
     * @return Mensaje
     */
    public FacesMessage getMensaje(String msgResum, String msgComp, FacesMessage.Severity severity) {
        String patternMsgResum = "";
        String patternMsgComp = "";
        try {
            patternMsgResum = resourceBundle.getString(msgResum);
            patternMsgComp = resourceBundle.getString(msgComp);
        } catch (MissingResourceException mre) {
            logger.log(Level.SEVERE, "No se encontro el mensaje con llave: " + msgResum +":"+msgComp, mre);
            return new FacesMessage("");
        }
        return new FacesMessage(severity,patternMsgResum,patternMsgComp);
    }
    
    /**
     * Crea un mensaje personalizado con la severidad especificada.
     * 
     * @param msgResum Resumen del mensaje
     * @param msgComp Mensaje completo
     * @param severity Severidad del mensaje
     * @return FacesMessage con el mensaje indicado.
     */
    public FacesMessage getMensajeAdaptado(String msgResum, String msgComp, FacesMessage.Severity severity) {
        return new FacesMessage(severity,msgResum,msgComp);
    }
    
    
}
