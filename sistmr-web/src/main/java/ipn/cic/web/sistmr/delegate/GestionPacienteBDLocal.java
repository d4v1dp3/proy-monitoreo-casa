/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipn.cic.web.sistmr.delegate;

import ipn.cic.sistmr.exception.IDUsuarioException;
import ipn.cic.sistmr.exception.PacienteException;
import ipn.cic.sistmr.modelo.EntPaciente;
import ipn.cic.web.sistmr.bean.vo.AntecedentesVO;
import ipn.cic.web.sistmr.bean.vo.PacienteVO;
import ipn.cic.web.sistmr.bean.vo.PersonaVO;
import ipn.cic.web.sistmr.bean.vo.UsuarioVO;
import javax.ejb.Local;

/**
 *
 * @author J.PEREZ
 */
@Local
public interface GestionPacienteBDLocal {
     public EntPaciente guardarPacienteNuevo(PacienteVO paciente, PersonaVO persona, AntecedentesVO antecedentes, 
                                        UsuarioVO usuario) throws PacienteException, IDUsuarioException;
}
