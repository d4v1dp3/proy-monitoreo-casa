/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.webservice.servicios;

import ipn.cic.web.sistmr.bean.vo.MedidasVO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import ipn.cic.sistmr.exception.MedidasException;
import ipn.cic.sistmr.exception.NoExistePacienteException;
import ipn.cic.web.sistmr.delegate.MedidasBDLocal;
import java.util.Calendar;
import javax.ejb.EJB;

/**
 *
 * @author J.PEREZ
 */

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

@Path("persistemedicion")
public class ServiciosMovilWS {
    
    @EJB
    private MedidasBDLocal medidasDB;
    
    @POST
    public JsonObject recibeMedidas(JsonObject datos) throws MedidasException, NoExistePacienteException{
        /*Estructura JSON Recibida
        {
            "idPaciente": 20178,
            "saturacionOxigeno": 98.0,
            "temperatura": 26.0,
            "frecCardiaca": 62, 
            "frecRespiratoria": 19,
            "alerta": 0, 
            "preArtSistolica":  110,
            "preArtDiastolica": 78
        }*/

        JsonObject respuesta;
        
        try{
            Gson gson= new Gson();
            MedidasVO med = gson.fromJson(datos.toString(), MedidasVO.class);
            med.setFechaMedicion(Calendar.getInstance().getTime());
            
            respuesta = medidasDB.guardarMedidas(med);
        }catch(NoExistePacienteException ex){
            respuesta = Json.createObjectBuilder()
            .add("Respuesta", "1")
            .add("Error", "No existe paciente.")
            .build();
        }catch(MedidasException ex){
            respuesta = Json.createObjectBuilder()
            .add("Respuesta", "3")
            .add("Error", "Intentelo mas tarde.")
            .build();
        }catch(JsonSyntaxException ex){
            respuesta = Json.createObjectBuilder()
            .add("Respuesta", "5")
            .add("Error", "Documento JSON mal formado. : "+ex.getMessage())
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

        /*RESPUESTA EN JSON
        JsonObject respuesta = Json.createObjectBuilder()
        .add("IDPaciente", med.getIdPaciente())
        //.add("Fecha", med.getFechaMedicion().toString())
        .add("Oxigeno", med.getSaturacionOxigeno())   
        .add("temperatura", med.getTemperatura())  
        .add("FCardiaca", med.getFrecCardiaca())  
        .add("FRespiratoria", med.getFrecRespiratoria())  
        .add("Alerta", med.isAlerta())  
        .add("preArtSistolica", med.getPreArtSistolica())  
        .add("preArtDiastolic", med.getPreArtDiastolica()) 
        .build();*/ 
