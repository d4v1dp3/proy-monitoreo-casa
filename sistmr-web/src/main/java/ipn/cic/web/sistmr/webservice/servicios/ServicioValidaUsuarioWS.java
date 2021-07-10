/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.webservice.servicios;

import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import ipn.cic.web.sistmr.delegate.ValidaUsuarioBDLocal;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author marcos
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

@Path("validausuario")
public class ServicioValidaUsuarioWS {

    @EJB
    UsuarioSBLocal usuarioSB;
    
    @EJB 
    ValidaUsuarioBDLocal validaBD;

    @POST
    public JsonObject recibeIdUsuario(JsonObject datos) {
        /*Estructura JSON Recibida
        {
            "idusuario": "********",
            "cifra": "********"
        }*/
        
        JsonObject respuesta;
        respuesta = validaBD.validaUsuario(datos);
        return respuesta;
    }

    @GET
    public JsonObject validaUsuario() {

        JsonObject respuesta = Json.createObjectBuilder()
        .add("Mensaje", "Directiva disponible.")
        .build();
        return respuesta;

    }
}

