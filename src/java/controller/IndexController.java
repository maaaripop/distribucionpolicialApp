/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import model.dao.IDelito;
import model.dao.ITipodelito;
import model.dao.ITurno;
import model.daoImpl.DelitoImpl;
import model.daoImpl.TipodelitoImpl;
import model.daoImpl.TurnoImpl;
import model.pojo.Delito;
import model.pojo.Tipodelito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *
 * @author Mariella
 */
@Controller
public class IndexController {
    ITipodelito tipodelitoService = TipodelitoImpl.getInstance();
    IDelito delitoService = DelitoImpl.getInstance();
    ITurno turnoService = TurnoImpl.getInstance();
     
    @RequestMapping(value = "/mapas", method = RequestMethod.GET)
    public String cargar(Model model) {
        model.addAttribute("delito", new Delito());
        model.addAttribute("tipodelitoLst", this.tipodelitoService.getAll());
        model.addAttribute("delitoLst", this.delitoService.getAll());
        model.addAttribute("turnoLst", this.turnoService.getAll());
        return "delito";
    }
    
    @RequestMapping(value = "/mapas/agregarDelito", method = RequestMethod.POST)
    public String agregarDelito(@ModelAttribute("delito") Delito td) {

        delitoService.save(td);
        return "redirect:/mapas";

    }
}
