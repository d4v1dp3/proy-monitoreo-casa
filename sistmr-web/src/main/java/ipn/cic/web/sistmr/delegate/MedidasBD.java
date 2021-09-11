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
import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.modelo.EntValoresReferencia;
import ipn.cic.sistmr.sesion.MedicoSBLocal;
import ipn.cic.sistmr.sesion.MedidasSBLocal;
import ipn.cic.sistmr.sesion.PacienteSBLocal;
import ipn.cic.sistmr.sesion.PersonaSBLocal;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
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
    private UsuarioSBLocal usuarioSB;

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
        EntUsuario usuario;
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
            medico = medicoSB.getMedicoDePaciente(paciente);
            usuario = usuarioSB.getUsuarioDeMedico(medico);
            persona = personaSB.getPersonaDePaciente(paciente.getIdPaciente());

            String reporteMedidas = revisaMedidas(medidas);

            if (reporteMedidas.isBlank()) {
                logger.log(Level.INFO, "Medidas dentro de los rangos:");
                logger.log(Level.INFO, "satOxg: {0}", medidas.getSaturacionOxigeno());
                logger.log(Level.INFO, "temp: {0}", medidas.getTemperatura());
                logger.log(Level.INFO, "fresp: {0}", medidas.getFrecRespiratoria());
                logger.log(Level.INFO, "fcard: {0}", medidas.getFrecCardiaca());
                logger.log(Level.INFO, "pSis: {0}", medidas.getPreArtSistolica());
                logger.log(Level.INFO, "pDias: {0}", medidas.getPreArtDiastolica());
            } else {
                logger.log(Level.INFO, "Medidas fuera de los rangos");
                String pac = "Paciente: " + persona.getPrimerApellido()
                        + " " + persona.getSegundoApellido()
                        + " " + persona.getNombre();
                correoSB.enviarCorreo(mailSesion, usuario.getEmail(), "Notificacion Medidas ", pac + reporteMedidas);

            }

            if (errorSensores(medidas) == 0) {
                respuesta = Json.createObjectBuilder()
                        .add("Respuesta", "0")
                        .add("Descripción", "Medidas almacenadas correctamente.")
                        .build();
            } else {
                respuesta = Json.createObjectBuilder()
                        .add("Respuesta", "7")
                        .add("Descripción", "Error sensores.")
                        .build();
            }

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
                    .add("Descripción", "Intente mas tarde.")
                    .build();
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Error inesperado del sistema : {0}", ex.getMessage());

            respuesta = Json.createObjectBuilder()
                    .add("Respuesta", "4")
                    .add("Descripción", "Error inesperado del sistema : " + ex.getMessage())
                    .build();
        }
        return respuesta;
    }

    private int errorSensores(EntMedidas medidas) {
        if (medidas.getSaturacionOxigeno() < 0 || medidas.getTemperatura() < 0
                || medidas.getFrecRespiratoria() < 0 || medidas.getFrecCardiaca() < 0
                || medidas.getPreArtSistolica() < 0 || medidas.getPreArtDiastolica() < 0) {
            return 1;
        }
        return 0;
    }

    private String revisaMedidas(EntMedidas medidas) {
        try {
            EntValoresReferencia valoresRef;
            valoresRef = valoresSB.getValoresReferenciaId(new Short("1"));
            String reporte = "";

            if (medidas.getSaturacionOxigeno() < 0) {
                reporte += " \nError sensor: Saturacion oxigeno";
            } else if (medidas.getSaturacionOxigeno() < valoresRef.getSatOxigenoAlertMax() || medidas.getSaturacionOxigeno() >= valoresRef.getSatOxigenoNormalMax()) {
                reporte += " \nSaturacion de Oxigeno: " + Float.toString(medidas.getSaturacionOxigeno());
            }

            if (medidas.getTemperatura() < 0) {
                reporte += " \nError sensor: Temperatura";
            } else if (medidas.getTemperatura() < valoresRef.getTemperaturaNormalMin() || medidas.getTemperatura() >= valoresRef.getTemperaturaAlertMin()) {
                reporte += " \nTemperatura: " + Float.toString(medidas.getTemperatura());
            }

            if (medidas.getFrecRespiratoria() < 0) {
                reporte += " \nError sensor: Frecuencia Respiratoria";
            } else if (medidas.getFrecRespiratoria() < valoresRef.getFrecRespiratoriaNormalMin() || medidas.getFrecRespiratoria() >= valoresRef.getFrecRespiratoriaAlertMin()) {
                reporte += " \nFrecuencia respiratoria: " + Short.toString(medidas.getFrecRespiratoria());
            }

            if (medidas.getFrecCardiaca() < 0) {
                reporte += " \nError sensor: Frecuencia Cardiaca";
            } else if (medidas.getFrecCardiaca() < valoresRef.getFrecCardiacaNormalMin() || medidas.getFrecCardiaca() >= valoresRef.getFrecCardiacaAlertMin()) {
                reporte += " \nFrecuencia cardiaca: " + Short.toString(medidas.getFrecCardiaca());
            }

            if (medidas.getPreArtSistolica() < 0) {
                reporte += " \nError sensor: Presion Arterial Sistolica";
            } else if (medidas.getPreArtSistolica() < valoresRef.getPreArtSistolicaNormalMin() || medidas.getPreArtSistolica() >= valoresRef.getPreArtSistolicaAlertMin()) {
                reporte += " \nPresion Arterial Sistolica: " + Integer.toString(medidas.getPreArtSistolica());
            }

            if (medidas.getPreArtDiastolica() < 0) {
                reporte += " \nError sensor: Presion Arterial Diastolica";
            } else if (medidas.getPreArtDiastolica() < valoresRef.getPreArtDiastolicaNormalMin() || medidas.getPreArtDiastolica() >= valoresRef.getPreArtDiastolicaAlertMin()) {
                reporte += " \nPresion Arterial Diastolica: " + Integer.toString(medidas.getPreArtDiastolica());
            }

            return reporte;
        } catch (NoExisteValoresRefException ex) {
            logger.log(Level.INFO, "Error al obtener valores de referencia");
        }
        return "";
    }

}
