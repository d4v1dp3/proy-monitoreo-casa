/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipn.cic.web.sistmr.delegate;

import javax.ejb.Local;
import javax.json.JsonObject;

/**
 *
 * @author marcos
 */
@Local
public interface ValidaUsuarioBDLocal {
    JsonObject validaUsuario(JsonObject datos);
}
