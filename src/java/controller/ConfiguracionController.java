/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.util.ArrayList;
import model.dao.IComisaria;
import model.dao.IDelito;
import model.dao.IUsuario;
import model.dao.IVehiculoxcomisaria;
import model.daoImpl.ComisariaImpl;
import model.daoImpl.DelitoImpl;
import model.daoImpl.UsuarioImpl;
import model.daoImpl.VehiculoxcomisariaImpl;
import model.pojo.Comisaria;
import model.pojo.Delito;
import model.pojo.Usuario;
import model.pojo.Vehiculoxcomisaria;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**

/**
 *
 * @author Mariella
 */
@Controller
public class ConfiguracionController {
    
    @RequestMapping(value = "/configuracion", method = RequestMethod.GET)
    public String cargar(Model model) {
       //model.addAttribute("Configuracion", new Configuracion());
       model.addAttribute ("edit",false);
       return "configuracion";
    }
    
    @RequestMapping(value = "/configuracionEdit", method = RequestMethod.GET)
    public String cargarEdit(Model model) {
       //model.addAttribute("Configuracion", new Configuracion());
       
       return "configuracionEdit";
    }
}
