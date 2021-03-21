/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.webservice.servicios;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.JsonSyntaxException;
import ipn.cic.sistmr.exception.MedidasException;
import ipn.cic.sistmr.exception.NoExistePacienteException;
import ipn.cic.sistmr.exception.UsuarioException;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPersona;
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.PacienteSBLocal;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import javax.ejb.EJB;

/**
 *
 * @author J.PEREZ
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

@Path("getidpaciente")
public class ServicioIdPacienteWS {
    
    @EJB
    UsuarioSBLocal usuarioSB;
    @EJB
    PacienteSBLocal pacienteSB;

    
    @POST
    public JsonObject recibeIdUsuario(JsonObject datos) throws MedidasException, NoExistePacienteException{
        /*Estructura JSON Recibida
        {
            "idusuario": "paciente1"
        }*/

        JsonObject respuesta;
        
        try{
            String idusuario = datos.getString("idusuario");
            
            EntUsuario usuario = usuarioSB.getUsuario(idusuario);
            EntPersona persona = usuarioSB.getPersonaDeUsuario(usuario);
            EntPaciente paciente =pacienteSB.getPaciente(persona);

            respuesta = Json.createObjectBuilder()
            .add("idusuario", idusuario)
                    .add("idpaciente", paciente.getIdPaciente())
                    .build();;

        }catch(JsonSyntaxException ex){
            respuesta = Json.createObjectBuilder()
            .add("Respuesta", "5")
            .add("Error", "Documento JSON mal formado. : "+ex.getMessage())
            .build();
        } catch (UsuarioException ex) {
            respuesta = Json.createObjectBuilder()
            .add("Respuesta", "6")
            .add("Error", "Usuario no encontrado.")
            .build();
        }

        return respuesta;
    }
    
    @GET
    public JsonObject confirmaConexion(){
     
        JsonObject respuesta = Json.createObjectBuilder()
        .add("Mensaje", "Directiva disponible.")
        .build();
        return respuesta;
    }
}