/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.bean.comun;

import ipn.cic.sistmr.exception.MenuException;
import ipn.cic.sistmr.exception.UsuarioException;
import ipn.cic.sistmr.modelo.EntMenu;
import ipn.cic.sistmr.modelo.EntMenuOpcion;
import ipn.cic.sistmr.modelo.EntRol;
import ipn.cic.sistmr.sesion.MenuSBLocal;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import ipn.cic.sistmr.util.Constantes;
import ipn.cic.web.sistmr.util.UtilWebSBLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.component.menubar.Menubar;
import org.primefaces.component.panelmenu.PanelMenu;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

/**
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
@Named(value="menuMB")
@SessionScoped
public class MenuMB implements Serializable{
    private static final Logger logger = Logger.getLogger(MenuMB.class.getName());
    private static final long serialVersionUID = 1L;
    
    @EJB
    private UtilWebSBLocal utilWebSB;
    @EJB
    private MenuSBLocal menuSB;
    @EJB
    private UsuarioSBLocal usuarioSB;
     
    private PanelMenu panelMenu;
    private Menubar barraMenu;
    private MenuModel menuModel;
    
    /**
     * Constructor del menu
     */
    public MenuMB() {
        init();
    }

    /**
     * Inicializa datos del MenuMB
     */
    private void init() {
        panelMenu = null;
        barraMenu = null;
    }
    
    /**
     * @return the panelMenu
     */
    public PanelMenu getPanelMenu() {
        if (panelMenu == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            //menuToolBar = createToolBar(fc);
            panelMenu = (PanelMenu) fc.getApplication().createComponent(fc, PanelMenu.COMPONENT_TYPE,
                    "org.primefaces.component.panelmenu.PanelMenu");
            cargarMenu(fc, panelMenu);
        }
        panelMenu.setStyleClass("nav side-menu");

        return panelMenu;
    }
    
    /**
     * Carga el menu de la aplicacion para un ambiente XHTML
     *
     * @param fc Instancia del contexto de faces
     * @param toolbar Barra de herramientas del menu de la aplicacion
     */
    private void cargarMenuHTML(FacesContext fc) {
        Constantes constantes = Constantes.getInstance();
        int idMenuGeneral = constantes.getInt("ID_MENU_GENERAL");
        // Obtener los roles del usuario autenticado
        List<EntRol> rolesUsu = null;
        try {
            rolesUsu = usuarioSB.getRoles(utilWebSB.getUsrAutenticado().getIdUsuario());
        } catch (UsuarioException ex) {
            logger.log(Level.SEVERE, "Error al obtener los roles del Usuario", ex);
        }
        int contadorGeneral = 0;
        // Obtener las opciones de menu por cada rol
        for (EntRol rol : rolesUsu) {
            logger.log(Level.INFO, "ROL: {0}", rol.getDescripcion());
            List<EntMenu> menuRol = null;
            try {
                menuRol = menuSB.getMenuByRol(rol.getIdRol());
            } catch (MenuException ex) {
                logger.log(Level.SEVERE, "Error al obtener el menu de rol", ex);
            }

            // Generar el menu recursivamente por cada menu principal
            //AQUI VAMMOS A MODIFICAR PARA USAR PRIMEFACES .. eso espero.
            for (EntMenu menuPrincipal : menuRol) {
                if ((menuPrincipal.getIdMenu()== idMenuGeneral) && (contadorGeneral == 0)) {
                    DefaultSubMenu dropDownMenu = createSubMenu(menuPrincipal.getDescripcion(), menuPrincipal.getRutaIcono());
                    menuModel.getElements().add(dropDownMenu);
                    cargarMenuBD(menuPrincipal.getIdMenu(), dropDownMenu, fc);
                    contadorGeneral++;
                } else {
                    if (menuPrincipal.getIdMenu() != idMenuGeneral) {
                        DefaultSubMenu dropDownMenu = createSubMenu(menuPrincipal.getDescripcion(), menuPrincipal.getRutaIcono());
                        menuModel.getElements().add(dropDownMenu);
                        cargarMenuBD(menuPrincipal.getIdMenu(), dropDownMenu, fc);
                    }
                }
            }
        }
        panelMenu.setModel(menuModel);
    }

    /**
     * Carga el menu de la aplicacion
     *
     * @param fc Instancia del contexto de faces
     * @param toolbar Barra de herramientas del menu de la aplicacion
     */
    private void cargarMenu(FacesContext fc, PanelMenu toolbar) {
        Constantes constantes = Constantes.getInstance();
        int idMenuGeneral = constantes.getInt("ID_MENU_GENERAL");
        menuModel = new DefaultMenuModel();
        // Obtener los roles del usuario autenticado
        List<EntRol> rolesUsu = null;
        try {
            rolesUsu = usuarioSB.getRoles(utilWebSB.getUsrAutenticado().getIdUsuario());
        } catch (UsuarioException ex) {
            logger.log(Level.SEVERE, "Error al obtener los roles del Usuario", ex);
        }
        int contadorGeneral = 0;
        // Obtener las opciones de menu por cada rol
        for (EntRol rol : rolesUsu) {
            logger.log(Level.INFO, "ROL: {0}", rol.getDescripcion());
            List<EntMenu> menuRol = null;
            try {
                menuRol = menuSB.getMenuByRol(rol.getIdRol());
            } catch (MenuException ex) {
                logger.log(Level.SEVERE, "Error al obtener el menu de rol", ex);
            }

            // Generar el menu recursivamente por cada menu principal
            //AQUI VAMMOS A MODIFICAR PARA USAR PRIMEFACES .. eso espero.
            for (EntMenu menuPrincipal : menuRol) {
                if ((menuPrincipal.getIdMenu() == idMenuGeneral) && (contadorGeneral == 0)) {
                    DefaultSubMenu dropDownMenu = createSubMenu(menuPrincipal.getDescripcion(), menuPrincipal.getRutaIcono());
                    menuModel.getElements().add(dropDownMenu);
                    cargarMenuBD(menuPrincipal.getIdMenu(), dropDownMenu, fc);
                    contadorGeneral++;
                } else {
                    if (menuPrincipal.getIdMenu() != idMenuGeneral) {
                        DefaultSubMenu dropDownMenu = createSubMenu(menuPrincipal.getDescripcion(), menuPrincipal.getRutaIcono());
                        menuModel.getElements().add(dropDownMenu);
                        cargarMenuBD(menuPrincipal.getIdMenu(), dropDownMenu, fc);
                    }
                }
            }
        }
        toolbar.setModel(menuModel);
    }

    /**
     * Carga el menu de la aplicacion
     *
     * @param fc Instancia del contexto de faces
     * @param toolbar Barra de herramientas del menu de la aplicacion
     */
    private void cargarMenu(FacesContext fc, Menubar toolbar) {
        Constantes constantes = Constantes.getInstance();
        int idMenuGeneral = constantes.getInt("ID_MENU_GENERAL");
        menuModel = new DefaultMenuModel();
        ExternalContext exc = fc.getExternalContext();

        menuModel.getElements().add(createMenuItem("Inicio",
                "fa fa-home",
                "/faces/facelets/comun/Bienvenida.xhtml",exc));
        // Obtener los roles del usuario autenticado
        List<EntRol> rolesUsu = null;
        try {
            rolesUsu = usuarioSB.getRoles(utilWebSB.getUsrAutenticado().getIdUsuario());
        } catch (UsuarioException ex) {
            logger.log(Level.SEVERE, "Error al obtener los roles del Usuario", ex);
        }
        int contadorGeneral = 0;
        // Obtener las opciones de menu por cada rol
        for (EntRol rol : rolesUsu) {
            logger.log(Level.INFO, "ROL: {0}", rol.getDescripcion());
            List<EntMenu> menuRol = null;
            try {
                menuRol = menuSB.getMenuByRol(rol.getIdRol());
            } catch (MenuException ex) {
                logger.log(Level.SEVERE, "Error al obtener el menu de rol", ex);
            }

            // Generar el menu recursivamente por cada menu principal
            //AQUI VAMMOS A MODIFICAR PARA USAR PRIMEFACES .. eso espero.
            for (EntMenu menuPrincipal : menuRol) {
                if ((menuPrincipal.getIdMenu() == idMenuGeneral) && (contadorGeneral == 0)) {
                    DefaultSubMenu dropDownMenu = createSubMenu(menuPrincipal.getDescripcion(), menuPrincipal.getRutaIcono());
                    menuModel.getElements().add(dropDownMenu);
                    cargarMenuBD(menuPrincipal.getIdMenu(), dropDownMenu, fc);
                    contadorGeneral++;
                } else {
                    if (menuPrincipal.getIdMenu() != idMenuGeneral) {
                        DefaultSubMenu dropDownMenu = createSubMenu(menuPrincipal.getDescripcion(), menuPrincipal.getRutaIcono());
                        menuModel.getElements().add(dropDownMenu);
                        cargarMenuBD(menuPrincipal.getIdMenu(), dropDownMenu, fc);
                    }
                }
            }
        }
        toolbar.setModel(menuModel);
    }

    private DefaultSubMenu createSubMenu(String descripcion, String rutaIcono) {
        DefaultSubMenu dropDownMenu = new DefaultSubMenu();

        dropDownMenu.setIcon(rutaIcono);
        dropDownMenu.setLabel(descripcion);
        //dropDownMenu.setStyleClass("nav child_menu");
        return dropDownMenu;
    }

    /**
     * Carga las opciones de un menu desde base de datos.
     *
     * @param idMenu Identificador unico del menu
     * @param padre Submenu a ser cargado con opciones
     * @param fc Instancia del contexto de faces
     */
    private void cargarMenuBD(Short idMenu, DefaultSubMenu padre, FacesContext fc) {
        ExternalContext exc = fc.getExternalContext();
        List<EntMenuOpcion> opciones = new ArrayList<EntMenuOpcion>();
        try {
            opciones = menuSB.getOpcsMenu(idMenu);
        } catch (MenuException ex) {
            logger.log(Level.SEVERE, "Error al obtener las opciones del menu", ex);
        }

        for (EntMenuOpcion opcion : opciones) {
            // ¿Es una accion?
            if (opcion.getIdOpcion() != null) {
                padre.getElements().add(createMenuItem(opcion.getIdOpcion().getDescripcion(),
                        opcion.getIdOpcion().getRutaIcono(),
                        opcion.getIdOpcion().getAccion(),exc));
            } else if (opcion.getIdSubMenu() != null) {
                DefaultSubMenu subMenu = createSubMenu(opcion.getIdSubMenu().getDescripcion(),
                        opcion.getIdSubMenu().getRutaIcono());
                padre.getElements().add(subMenu);
                cargarMenuBD(opcion.getIdSubMenu().getIdMenu(), subMenu, fc);
            }
        }
    }

    private DefaultMenuItem createMenuItem(String etiqueta, String rutaIcono, 
                                           String action, ExternalContext exc) {
        DefaultMenuItem menuItem = new DefaultMenuItem();
        menuItem.setValue(etiqueta);
        if (rutaIcono != null) {
            menuItem.setIcon(rutaIcono);
        }
        menuItem.setAjax(true);
        if (action == null) {
            menuItem.setOnclick("alert('No se ha especificado un accion para la opcion:' + etiqueta)");
        } else {
            if (action.contains("#")) {
                menuItem.setCommand(action);
            } else {
                //String accion = action+"?faces-redirect=true"; // PrimeFaces 6.2
                String accion = exc.getRequestContextPath()+action+"?faces-redirect=true"; // Prime 7.0
                menuItem.setUrl(accion);
            }
        }
        //
        return menuItem;
    }

    /**
     * @param panelMenu the panelMenu to set
     */
    public void setPanelMenu(PanelMenu panelMenu) {
        this.panelMenu = panelMenu;
    }

    /**
     * @return the barraMenu
     */
    public Menubar getBarraMenu() {
        if (barraMenu == null) {
            FacesContext fc = FacesContext.getCurrentInstance();
            //menuToolBar = createToolBar(fc);
            barraMenu = (Menubar) fc.getApplication().createComponent(fc, Menubar.COMPONENT_TYPE,
                    "org.primefaces.component.menubar.Menubar");
            cargarMenu(fc, barraMenu);
        }
        barraMenu.setStyleClass("nav side-menu");

        return barraMenu;
    }

    /**
     * @param barraMenu the barraMenu to set
     */
    public void setBarraMenu(Menubar barraMenu) {
        this.barraMenu = barraMenu;
    }
    
}
