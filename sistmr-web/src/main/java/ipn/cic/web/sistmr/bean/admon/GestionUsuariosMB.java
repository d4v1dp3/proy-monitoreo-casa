/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.admon;

import ipn.cic.sistmr.exception.CatalogoException;
import ipn.cic.sistmr.exception.GeneroException;
import ipn.cic.sistmr.exception.UpdateEntityException;
import ipn.cic.sistmr.exception.UsuarioException;
import ipn.cic.sistmr.modelo.EntGenero;
import ipn.cic.sistmr.modelo.EntPersona;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.CatalogoSBLocal;
import ipn.cic.sistmr.sesion.GeneroSBLocal;
import ipn.cic.sistmr.sesion.PersonaSBLocal;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import ipn.cic.web.sistmr.util.Mensaje;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 * ManageBean que se utiliza para carga de usuarios en el sistema
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Named(value = "gestionUsuariosMB")
@ViewScoped
public class GestionUsuariosMB implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(GestionUsuariosMB.class.getName());

    @EJB
    private UsuarioSBLocal usuarioSB;
    @EJB
    private PersonaSBLocal personaSB;
    @EJB
    private GeneroSBLocal generoSB;
    @EJB
    private UtilWebSBLocal utilWebSB;
    @EJB
    CatalogoSBLocal catalogoSB;

    private List<EntUsuario> usuariosComp;
    private EntUsuario usuarioEditar;
    private List<EntGenero> catGenero;    
    
    private long idUsuario;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String curp;
    private short idgenero;
    private int edad;
    private String usuario;
    private String contrasenia;
    private Boolean activo;
    private String correo;
    

    @PostConstruct
    public void cargaUsuarios() {
        FacesMessage msg=null;
        try {
            logger.log(Level.INFO,"Entra a cargar usuarios.");
            usuariosComp = usuarioSB.getUsuarios();
        } catch (UsuarioException ex) {
            logger.log(Level.SEVERE, "Error en MB al guardar hospital : {0}", ex.getMessage());
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error usuarios:",
                            "Error al intentar recuperar los usuarios intente más tarde",
                            FacesMessage.SEVERITY_ERROR);
        }
        
        try {
            setCatGenero((List<EntGenero>) catalogoSB.getCatalogo("EntGenero"));
        } catch (CatalogoException ex) {
            logger.log(Level.SEVERE, "Imposible recuperar Datos de Genero :{0} ",ex.getMessage());
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "No es posible recuperar catálogo de género :" + ex.getMessage(),
                            FacesMessage.SEVERITY_ERROR);
            utilWebSB.addMsg("frmAltaPaciente:msgAltaPassGral", msg);
        }
        
        if(msg==null){
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("",
                            "Usuarios cargados correctamente",
                            FacesMessage.SEVERITY_INFO);
        }
//        utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
        //PrimeFaces.current().ajax().update("frGestUsuarios:msgsGU");
    }
    
    public void altaMedico(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 700);
        options.put("height", 640);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        PrimeFaces.current().dialog().openDynamic("usuarios/dialAltaMedico", options, null);
    }
    
    public void retornoAltaMedico(SelectEvent event){
        FacesMessage msg = null;

        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();
            utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
            cargaUsuarios();
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo",
                            "Diálogo cerrado sin aplicar cambios",
                            FacesMessage.SEVERITY_INFO);
            utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
        }
    }
    
    
    public void altaPaciente(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 750);
        options.put("height", 640);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        PrimeFaces.current().dialog().openDynamic("usuarios/dialAltaPaciente", options, null);
    }
    
    public void retornoAltaPaciente(SelectEvent event){
        FacesMessage msg = null;

        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();
            utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
            cargaUsuarios();
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo",
                            "Diálogo cerrado sin aplicar cambios",
                            FacesMessage.SEVERITY_INFO);
            utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
        }
    }
    
    public void altaAdministrador(){
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 700);
        options.put("height", 640);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");

        PrimeFaces.current().dialog().openDynamic("usuarios/dialAltaAdministrador", options, null);
    }
    
    public void retornoAltaAdministrador(SelectEvent event){
        FacesMessage msg = null;

        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();
            utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
            cargaUsuarios();
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo",
                            "Diálogo cerrado sin aplicar cambios",
                            FacesMessage.SEVERITY_INFO);
            utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
        }
    }
    
    public void editarUsuario() {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("width", 750);
        options.put("height", 680);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
        options.put("headerElement", "customheader");
        
        //Envio de Parametros
        Map<String, List<String>> parametros = new HashMap<>();

        List<String> idusuario = new ArrayList<>();
        idusuario.add(usuarioEditar.getIdUsuario() + "");
        
        List<String> nombre = new ArrayList<>();
        nombre.add(usuarioEditar.getIdPersona().getNombre()+ "");
        
        List<String> primerapellido = new ArrayList<>();
        primerapellido.add(usuarioEditar.getIdPersona().getPrimerApellido()+ "");
        
        List<String> segundoapellido = new ArrayList<>();
        segundoapellido.add(usuarioEditar.getIdPersona().getSegundoApellido()+ "");
        
        List<String> curp = new ArrayList<>();
        curp.add(usuarioEditar.getIdPersona().getCurp()+ "");
        
        List<String> edad = new ArrayList<>();
        edad.add(usuarioEditar.getIdPersona().getEdad()+ "");
        
        List<String> usuario = new ArrayList<>();
        usuario.add(usuarioEditar.getIdUsuario()+ "");
        
        List<String> contrasenia = new ArrayList<>();
        contrasenia.add(usuarioEditar.getContrasenia()+ "");
        
        List<String> activo = new ArrayList<>();
        activo.add(usuarioEditar.getActivo()+ "");
        
        List<String> genero = new ArrayList<>();
        genero.add(usuarioEditar.getIdPersona().getIdGenero().getIdGenero()+ "");
        
        List<String> correo = new ArrayList<>();
        correo.add(usuarioEditar.getEmail()+ "");

        parametros.put("idUsuario", idusuario);
        parametros.put("nombre", nombre);
        parametros.put("primerapellido", primerapellido);
        parametros.put("segundoapellido", segundoapellido);
        parametros.put("curp", curp);
        parametros.put("edad", edad);
        parametros.put("usuario", usuario);
        parametros.put("contrasenia", contrasenia);
        parametros.put("activo", activo);  
        parametros.put("idgenero", genero);  
        parametros.put("correo", correo);  

        PrimeFaces.current().dialog().openDynamic("usuarios/dialEditaUsuario", options, parametros);
    }

    public void retornoEditaUsuario(SelectEvent event) {
        FacesMessage msg = null;

        if (event.getObject() != null) {
            msg = (FacesMessage) event.getObject();
            cargaUsuarios();
        } else {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Diálogo ",
                            "Diálogo cerrado sin aplicar cambios",
                            FacesMessage.SEVERITY_INFO);
        }
        
        utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
    }
    
    public void guardarCambiosUsuario() {

        logger.log(Level.INFO, "Entrando a actualizar datos de usuario.");
        FacesMessage msg = null;

        try {
            usuarioEditar = usuarioSB.getUsuario(usuario);
            
            logger.log(Level.INFO, "Usuario recuperado {0}.", usuarioEditar.getIdUsuario());
            
            usuarioEditar.setIdUsuario(usuario);
            usuarioEditar.setContrasenia(contrasenia);
            usuarioEditar.setActivo(activo);
            usuarioEditar.setEmail(correo);
            
            usuarioEditar = usuarioSB.updateUsuario(usuarioEditar);
            logger.log(Level.INFO, "Datos de sesion actualizados correctamente {0}.", usuarioEditar.getIdUsuario());
            
            EntPersona persona = usuarioSB.getPersonaDeUsuario(usuarioEditar);
            
            persona.setNombre(nombre);
            persona.setPrimerApellido(primerApellido);
            persona.setSegundoApellido(segundoApellido);
            persona.setCurp(curp);
            persona.setEdad(edad);
            EntGenero genero = generoSB.getGeneroID(idgenero);
            persona.setIdGenero(genero);

            persona = personaSB.updatePersona(persona);
            logger.log(Level.INFO, "Datos de usuario actualizado correctamente {0}.", persona.getIdPersona());
            
           
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Exíto",
                            "Datos actualizados correctamente. ",
                            FacesMessage.SEVERITY_INFO);

        } catch (UsuarioException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Imposible recuperar datos de usuario, intente más tarde",
                            FacesMessage.SEVERITY_ERROR);
            cerrarDialogo(msg);
        }catch (UpdateEntityException ex) {
            msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Imposible actualizar datos de usuario, intente más tarde",
                            FacesMessage.SEVERITY_ERROR);
        } catch (GeneroException ex) {
             msg = Mensaje.getInstance()
                    .getMensajeAdaptado("Error",
                            "Error al actualizar genero, intente más tarde",
                            FacesMessage.SEVERITY_ERROR);
        }

        utilWebSB.addMsg("frGestUsuarios:msgsGU", msg);
        cerrarDialogo(msg);
    }
    
    public void cerrarDialogo(){
        logger.log(Level.INFO,"Invocando cerrar dialogo.");
        cerrarDialogo(null);
    }
    
//    public void cerrarDialogo(){
//        FacesMessage mensaje = Mensaje.getInstance()
//                                      .getMensaje("CERRANDO_DIALOGO", "CERRANDO_CORRECTAMENTE",
//                                                   FacesMessage.SEVERITY_INFO);
//        PrimeFaces.current().dialog().closeDynamic(mensaje);
//    }
    
    public void cerrarDialogo(FacesMessage mensaje) {
        PrimeFaces.current().dialog().closeDynamic(mensaje);
    }

    /**
     * @return the usuariosComp
     */
    public List<EntUsuario> getUsuariosComp() {
        return usuariosComp;
    }

    /**
     * @param usuariosComp the usuariosComp to set
     */
    public void setUsuariosComp(List<EntUsuario> usuariosComp) {
        this.usuariosComp = usuariosComp;
    }

    /**
     * @return the usuarioEditar
     */
    public EntUsuario getUsuarioEditar() {
        return usuarioEditar;
    }

    /**
     * @param usuarioEditar the usuarioEditar to set
     */
    public void setUsuarioEditar(EntUsuario usuarioEditar) {
        this.usuarioEditar = usuarioEditar;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public List<EntGenero> getCatGenero() {
        return catGenero;
    }

    public void setCatGenero(List<EntGenero> catGenero) {
        this.catGenero = catGenero;
    }

    public short getIdgenero() {
        return idgenero;
    }

    public void setIdgenero(short idgenero) {
        this.idgenero = idgenero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
}