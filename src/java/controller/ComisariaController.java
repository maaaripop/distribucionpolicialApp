/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.dao.IComisaria;
import model.daoImpl.ComisariaImpl;
import model.pojo.Comisaria;
import model.pojo.Delito;
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
public class ComisariaController {
    
    IComisaria comisariaService = ComisariaImpl.getInstance();
 // @RequestMapping(value = "/delitos", method = RequestMethod.GET)   
    @RequestMapping(value = "/comisarias", method = RequestMethod.GET)
    public String cargar(Model model) {
        model.addAttribute("comisaria", new Comisaria());
        model.addAttribute("comisariaLst", this.comisariaService.getAll());
       /*
        List<Delito> delitos = this.delitoService.getAll();
        LongLatService longLatService = new LongLatService();
        for (Delito d : delitos) {
                longLatService.obtenerDistrito(d.getLatitud(), d.getLongitud());
            }
        */
        return "comisaria";
    }

 }
