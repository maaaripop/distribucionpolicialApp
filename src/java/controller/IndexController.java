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
        /*limites de lima metropolitana*/
        double NELat=-11.805910334098213;
        double NELong=-76.75915908813477;
        double SWLat=-12.277709407228006;
        double SWLong=-77.18620922875971;
        double NWLat=-11.805910334098213;
        double NWLong=-77.18620922875971; 
        double SELat=-12.277709407228006;
        double SELong=-76.75915908813477;
        double distanciaV=distanciaCoord(NWLat,NWLong,SWLat,SWLong);
        double distanciaH=distanciaCoord(NWLat,NWLong,NELat,NELong);
        model.addAttribute("latOrig",NWLat);
        model.addAttribute("longOrig",NWLong);
        model.addAttribute("cantV", distanciaV);
        model.addAttribute("cantH", distanciaH);
        
        return "delito";
    }
    
    @RequestMapping(value = "/mapas/agregarDelito", method = RequestMethod.POST)
    public String agregarDelito(@ModelAttribute("delito") Delito td) {

        delitoService.save(td);
        return "redirect:/mapas";

    }
    
    public static double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {  
        //double radioTierra = 3958.75;//en millas  
        double radioTierra = 6371;//en kilómetros  
        double dLat = Math.toRadians(lat2 - lat1);  
        double dLng = Math.toRadians(lng2 - lng1);  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));  
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
        double distancia = radioTierra * va2;  
   
        return distancia;  
    } 
    
    public static double distanciaCoordX(double lat1, double lng1, double lat2, double lng2) {  
        //double radioTierra = 3958.75;//en millas  
        double radioTierra = 6371;//en kilómetros  
        double dLat = Math.toRadians(lat2 - lat1);  
        double dLng = Math.toRadians(lng2 - lng1);  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));  
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));  
        double distancia = radioTierra * va2;  
   
        return distancia;  
    } 
    
}
