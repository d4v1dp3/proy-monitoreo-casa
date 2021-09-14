/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.comun;

import ipn.cic.sistmr.exception.UsuarioException;
import ipn.cic.sistmr.modelo.EntRol;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import ipn.cic.sistmr.util.Constantes;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author J. Perez
 */
@Named(value="gestionBienvenidaMB")
@ViewScoped
public class GestionBienvenidaMB implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(GestionBienvenidaMB.class.getName());
    
    @EJB
    private UtilWebSBLocal utilWebSB;
    @EJB
    private UsuarioSBLocal usuarioSB;
    
    
    @PostConstruct
    public void inicio(){
    logger.log(Level.INFO, "MB Iniciado");
    }

    
    public String redirecciona() throws UsuarioException {
        int rol_paciente= Constantes.getInstance().getInt("ROL_PACIENTE");
        int rol_medico= Constantes.getInstance().getInt("ROL_MEDICO");
        int rol_administrador= Constantes.getInstance().getInt("ROL_ADMINISTRADOR");
        
        String ruta="";
        
        List<EntRol> rolesUsu = null;
        try {
            rolesUsu = usuarioSB.getRoles(utilWebSB.getUsrAutenticado().getIdUsuario());
        } catch (UsuarioException ex) {
            logger.log(Level.SEVERE, "Error al obtener los roles del Usuario", ex);
        }
        
        for(EntRol rol: rolesUsu){
            if(rol.getIdRol()==rol_paciente){
                ruta = "/facelets/paciente/inicioPaciente.xhtml?faces-redirect=true";        
            }else if(rol.getIdRol()==rol_medico){
                ruta = "/facelets/medico/inicioMedico.xhtml?faces-redirect=true";
            }else if(rol.getIdRol()==rol_administrador){
                ruta = "/facelets/admon/inicioAdmin.xhtml?faces-redirect=true";
            }
        }       
        return ruta;
    }    
}
