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
 *
 * @author Mariella
 */
@Controller
public class InicioController {
    IUsuario usuarioService = UsuarioImpl.getInstance();
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String cargar(Model model) {
        model.addAttribute("usuario", new Usuario());
       /*
        List<Delito> delitos = this.delitoService.getAll();
        LongLatService longLatService = new LongLatService();
        for (Delito d : delitos) {
                longLatService.obtenerDistrito(d.getLatitud(), d.getLongitud());
            }
        */
        return "inicio";
    }
    
    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String cargarInicio(Model model) {
        model.addAttribute("usuario", new Usuario());
       /*
        List<Delito> delitos = this.delitoService.getAll();
        LongLatService longLatService = new LongLatService();
        for (Delito d : delitos) {
                longLatService.obtenerDistrito(d.getLatitud(), d.getLongitud());
            }
        */
        return "inicio";
    }
    
     @RequestMapping(value = "/usuario", method = RequestMethod.POST)
    public String agregarUsuario(@ModelAttribute("usuario") Usuario u) {
        usuarioService.save(u);
        return "inicio";

    }
    
    @RequestMapping(value = "/sesion", method = RequestMethod.POST)
    public String iniciarSesion(@ModelAttribute("usuario") Usuario u) {
        Usuario uLogin= usuarioService.login(u.getCorreo(), u.getContrasenha());
        if(uLogin!=null){
            if(uLogin.getComisaria()==null){
                // es ciudadano
                 return "redirect:/delitos";
            }
            else  return "redirect:/comisaria";
        
        }
        else return "inicio";

    }
}
