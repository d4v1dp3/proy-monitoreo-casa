/*
 * Instituto Politécnico Nacional
 * Centro de Investigación en Computación (CIC-IPN)
 * Laboratorio de Robótica y Mecatrónica
 * Todos los derechos reservados
 */
package ipn.cic.web.sistmr.delegate;


import ipn.cic.sistmr.exception.MedidasException;
import ipn.cic.sistmr.exception.NoExistePacienteException;
import ipn.cic.sistmr.exception.NoExisteValoresRefException;
import ipn.cic.sistmr.modelo.EntMedico;
import ipn.cic.sistmr.modelo.EntMedidas;
import ipn.cic.sistmr.modelo.EntMedidasPK;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.sistmr.modelo.EntPersona;
import ipn.cic.sistmr.modelo.EntValoresReferencia;
import ipn.cic.sistmr.sesion.MedicoSBLocal;
import ipn.cic.sistmr.sesion.MedidasSBLocal;
import ipn.cic.sistmr.sesion.PacienteSBLocal;
import ipn.cic.sistmr.sesion.PersonaSBLocal;
import ipn.cic.sistmr.sesion.ValoresReferenciaSBLocal;
import ipn.cic.sistmr.util.correo.CorreoSBLocal;
import ipn.cic.web.sistmr.bean.vo.MedidasVO;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.mail.Session;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 *
 * @author iliaco
 */
@Stateless
@PermitAll
@SecurityDomain("other")
public class MedidasBD implements MedidasBDLocal {

private static final Logger logger = Logger.getLogger(MedidasBDLocal.class.getName());

    @EJB
    private PacienteSBLocal pacienteSB;

    @EJB
    private MedidasSBLocal medidasSB;
    
    @EJB
    private MedicoSBLocal medicoSB;
    
    @EJB
    private PersonaSBLocal personaSB;
    
    @EJB
    private CorreoSBLocal correoSB;
    
    @EJB
    private ValoresReferenciaSBLocal valoresSB;
        
    @Resource(name = "java:jboss/mail/sisMCasaMail")
    private Session mailSesion;
            
    private EntPaciente cargarPaciente(long idPaciente) throws NoExistePacienteException {
        EntPaciente paciente = pacienteSB.getPaciente(idPaciente);
        logger.log(Level.INFO, "\tPaciente {0} recuperado.", idPaciente);
        return paciente;
    }

    @Override
    public JsonObject guardarMedidas(MedidasVO med) throws MedidasException, NoExistePacienteException {

        EntPaciente paciente;
        EntMedidas medidas = new EntMedidas();
        EntMedico medico;
        EntPersona persona;
        JsonObject respuesta;
        
        try {

            paciente = cargarPaciente(med.getIdPaciente());
            EntMedidasPK pkMed = new EntMedidasPK();
            pkMed.setIdPaciente(paciente.getIdPaciente());
            pkMed.setIdCareta(paciente.getIdCareta().getIdCareta());
            medidas.setEntMedidasPK(pkMed);
            medidas.setEntPaciente(paciente);
            medidas.setEntCareta(paciente.getIdCareta());
            medidas.setFechaMedicion(med.getFechaMedicion());
            medidas.setSaturacionOxigeno(med.getSaturacionOxigeno());
            medidas.setTemperatura(med.getTemperatura());
            medidas.setFrecCardiaca(med.getFrecCardiaca());
            medidas.setFrecRespiratoria(med.getFrecRespiratoria());
            medidas.setAlerta(med.getAlerta());
            medidas.setPreArtSistolica(med.getPreArtSistolica());
            medidas.setPreArtDiastolica(med.getPreArtDiastolica());

            medidas = medidasSB.guardaMedidas(medidas);
            logger.log(Level.INFO, "Medidas guardadas.");

            medico = medicoSB.getMedicoDePaciente(paciente);                  
            logger.log(Level.INFO, "Correo medico: {0}",medico.getEmail()); 
            
            persona = personaSB.getPersonaDePaciente(paciente.getIdPaciente());
            
            String asunto = "Parametros fuera del rango";
            String cuerpo = "Paciente: "+ persona.getPrimerApellido() 
                                        + " " + persona.getSegundoApellido()
                                        + " " + persona.getNombre();
   
            logger.log(Level.INFO, "Entrando a revision de medidas");
            if(revisarMedidas(medidas)){
                logger.log(Level.INFO, "Parametros fuera de los rangos");
                correoSB.enviarCorreo(mailSesion,medico.getEmail(),asunto,cuerpo);
            }else{
                logger.log(Level.INFO, "Parametros dentro de los rangos");
                logger.log(Level.INFO, "satOxg: {0}",medidas.getSaturacionOxigeno());
                logger.log(Level.INFO, "temp: {0}",medidas.getTemperatura());
                logger.log(Level.INFO, "fresp: {0}",medidas.getFrecRespiratoria());
                logger.log(Level.INFO, "fcard: {0}",medidas.getFrecCardiaca());
                logger.log(Level.INFO, "pSis: {0}",medidas.getPreArtSistolica());
                logger.log(Level.INFO, "pDias: {0}",medidas.getPreArtDiastolica());
            }
            
            respuesta = Json.createObjectBuilder()
                    .add("Respuesta", "0")
                    .add("Descripción", "Medidas almacenadas correctamente.")
                    .build();
        } catch (NoExistePacienteException ex) {
            logger.log(Level.SEVERE, "Error, paciente no econtrado : {0}", ex.getMessage());

            respuesta = Json.createObjectBuilder()
                    .add("Respuesta", "1")
                    .add("Descripción", "No existe paciente.")
                    .build();
        } catch (MedidasException ex) {
            logger.log(Level.SEVERE, "Error al guardar las medidas : {0}", ex.getMessage());

            respuesta = Json.createObjectBuilder()
                    .add("Respuesta", "3")
                    .add("Descripción", "Itente mas tarde.")
                    .build();
        }catch(Exception ex){
            logger.log(Level.SEVERE, "Error inesperado del sistema : {0}", ex.getMessage());

            respuesta = Json.createObjectBuilder()
                    .add("Respuesta", "4")
                    .add("Descripción", "Error inesperado del sistema : "+ex.getMessage())
                    .build();
        }        
        return respuesta;
    }
   
    public Boolean revisarMedidas(EntMedidas medidas){
        boolean oxgWarning;
        boolean tempWarning;
        boolean frespWarning;  
        boolean fcardWarning; 
        boolean pSistWarning;
        boolean pDiasWarning;
        try {
            EntValoresReferencia valoresRef;
            valoresRef = valoresSB.getValoresReferenciaId(new Short("1"));
            
            oxgWarning= medidas.getSaturacionOxigeno()<valoresRef.getSatOxigenoMin() || medidas.getSaturacionOxigeno()>valoresRef.getSatOxigenoMax();  
            tempWarning = medidas.getTemperatura()<valoresRef.getTemperaturaMin() || medidas.getTemperatura()>valoresRef.getTemperaturaMax();
            frespWarning = medidas.getFrecRespiratoria()<valoresRef.getFrecRespiratoriaMin() || medidas.getFrecRespiratoria()>valoresRef.getFrecRespiratoriaMax();
            fcardWarning = medidas.getFrecCardiaca()<valoresRef.getFrecCardiacaMin() || medidas.getFrecCardiaca()>valoresRef.getFrecCardiacaMax();
            pSistWarning = medidas.getPreArtSistolica()<valoresRef.getPreArtSistolicaMin() || medidas.getPreArtSistolica()>valoresRef.getPreArtSistolicaMax();
            pDiasWarning = medidas.getPreArtDiastolica()<valoresRef.getPreArtDiastolicaMin() || medidas.getPreArtDiastolica()>valoresRef.getPreArtDiastolicaMax();
            
            if(oxgWarning || tempWarning || frespWarning || fcardWarning || pSistWarning || pDiasWarning){
                return Boolean.TRUE;
            }else{
                return Boolean.FALSE;
            }
            
        } catch (NoExisteValoresRefException ex) {
            logger.log(Level.INFO, "Error al obtener valores de referencia");
        }
        return null;
    }
    
}
