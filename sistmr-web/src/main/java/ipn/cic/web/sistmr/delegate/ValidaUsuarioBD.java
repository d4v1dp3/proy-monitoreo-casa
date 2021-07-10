/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipn.cic.web.sistmr.delegate;

import ipn.cic.sistmr.modelo.EntUsuario;
import ipn.cic.sistmr.sesion.UsuarioSBLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import org.jboss.ejb3.annotation.SecurityDomain;

/**
 *
 * @author marcos
 */
@Stateless
@PermitAll
@SecurityDomain("other")
public class ValidaUsuarioBD implements ValidaUsuarioBDLocal {

    private static final Logger logger = Logger.getLogger(MedidasBDLocal.class.getName());
    @EJB
    private UsuarioSBLocal usuarioSB;

    @Override
    public JsonObject validaUsuario(JsonObject datos) {

        JsonObject respuesta = null;

        try {
            logger.log(Level.INFO, "idusuario {0}", datos.getString("idusuario"));
            EntUsuario usuario = usuarioSB.getUsuarioCifrado(datos.getString("idusuario"), datos.getString("cifra"));

            if (usuario != null) {

                if (usuario.getActivo()) {
                    respuesta = Json.createObjectBuilder()
                            .add("Respuesta", "0")
                            .add("Mensaje", "Usuario Activo")
                            .build();
                } else {
                    respuesta = Json.createObjectBuilder()
                            .add("Respuesta", "1")
                            .add("Mensaje", "Usuario No Activo")
                            .build();
                }

            } else {
                respuesta = Json.createObjectBuilder()
                        .add("Respuesta", "2")
                        .add("Mensaje", "No existe usuario/ No acceso")
                        .build();
            }
        } catch (Exception e) {
            respuesta = Json.createObjectBuilder()
                    .add("Respuesta", "3")
                    .add("Error", "Formato JSON incorrecto")
                    .build();
        }

        return respuesta;

    }

}
