/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.sistmr.util;

import java.util.ResourceBundle;

/**
 * Clase que provee los valores de aquellas constantes a las que se debe atar el sistema a
 * fin de implantar la logica del negocio. La mayor parte de estas son registros
 * de catalogos en la base de datos. Implementa el patron Singleton
 *
 * @author Iliac Huerta Trujillo <ihuertat@ipn.mx>
 */
public class Constantes {
    private final ResourceBundle bundle;
    private static Constantes instancia=null;
    
    private Constantes(){
        bundle = ResourceBundle.getBundle("constantes");
    }
    
    public static Constantes getInstance(){
        if(instancia==null){
            instancia = new Constantes();
        }
        return instancia;
    }
    
    public String getString(String nombre){
        return bundle.getString(nombre);
    }
    
    public int getInt(String nombre){
        return Integer.parseInt(bundle.getString(nombre));
    }
    
}
