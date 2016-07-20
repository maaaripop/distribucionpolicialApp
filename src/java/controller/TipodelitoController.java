/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.dao.ITipodelito;
import model.daoImpl.TipodelitoImpl;
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
 * @author USER
 */
@Controller
public class TipodelitoController {

    ITipodelito tipodelitoService = TipodelitoImpl.getInstance();

    /*
    @Override
    public ModelAndView handleRequest(HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        ModelAndView mv= new ModelAndView("tipodelito");
        
        try{
            List<Tipodelito> lst = TipodelitoImpl.getInstance().getAll();
            mv.addObject("tipodelitoLst",lst);
        }catch(Exception e){
            e.printStackTrace();
        }
        return mv;
    }
     */
    @RequestMapping(value = "/tipodelitos", method = RequestMethod.GET)
    public String listTipodelitos(Model model) {
        model.addAttribute("tipodelito", new Tipodelito());
        model.addAttribute("tipodelitoLst", this.tipodelitoService.getAll());
        return "tipodelito";
    }

    @RequestMapping(value = "/tipodelito/add", method = RequestMethod.POST)
    public String addTipodelito(@ModelAttribute("tipodelito") Tipodelito td) {

        if (td.getIdTipoDelito() == 0) {
            
            tipodelitoService.save(td);
        } else {
            
            tipodelitoService.update(td);
        }

        return "redirect:/tipodelitos";

    }

    @RequestMapping(value="/tipodelitos/remove/{id}",method = RequestMethod.GET)
    public String removeTipodelito(@PathVariable("id") int id) {

        tipodelitoService.delete(id);
        return "redirect:/tipodelitos";
    }

    @RequestMapping(value="/tipodelitos/edit/{id}",method = RequestMethod.GET)
    public String editTipodelito(@PathVariable("id") int id, Model model) {
        model.addAttribute("tipodelito", tipodelitoService.queryById(id));
        model.addAttribute("tipodelitoLst", tipodelitoService.getAll());
        return "tipodelito";
    }

}
