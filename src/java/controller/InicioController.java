/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.util.ArrayList;
import model.dao.IComisaria;
import model.dao.IVehiculoxcomisaria;
import model.daoImpl.ComisariaImpl;
import model.daoImpl.VehiculoxcomisariaImpl;
import model.pojo.Comisaria;
import model.pojo.Delito;
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
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String cargar(Model model) {
        
       /*
        List<Delito> delitos = this.delitoService.getAll();
        LongLatService longLatService = new LongLatService();
        for (Delito d : delitos) {
                longLatService.obtenerDistrito(d.getLatitud(), d.getLongitud());
            }
        */
        return "inicio";
    }
}
