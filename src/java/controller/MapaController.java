/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import algoritmo.Distribucion;
import algoritmo.Mapa;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.dao.IComisaria;
import model.dao.IDelito;
import model.dao.ITipodelito;
import model.dao.ITurno;
import model.daoImpl.ComisariaImpl;
import model.daoImpl.DelitoImpl;
import model.daoImpl.TipodelitoImpl;
import model.daoImpl.TurnoImpl;
import model.pojo.Delito;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author Mariella
 */
@Controller
public class MapaController {

    ITipodelito tipodelitoService = TipodelitoImpl.getInstance();
    IDelito delitoService = DelitoImpl.getInstance();
    ITurno turnoService = TurnoImpl.getInstance();
    IComisaria comisariaService = ComisariaImpl.getInstance();
    Mapa mapa= null;
    Distribucion distribucion= null;
    @RequestMapping(value = "/mapa", method = RequestMethod.GET)
    public void cargar(Model model) {
        model.addAttribute("turnoLst", this.turnoService.getAll());
        model.addAttribute("comisariaLst", this.comisariaService.getAll());
        /*limites de lima metropolitana*/

       
        double[] NE={-11.556918,-76.673105};
        //-11.551536, -76.669895
        
        double[] SW={-12.466486,-77.254585};
      
        double[] NW={-11.556918,-77.254585};
        
        
        double[] SE={-12.466486,76.673105};
        //-12.466095, -76.673105
        mapa= new Mapa(NE,SW,NW,SE);
        //-11.731853, -77.254585
        
        //-12.335244, -76.330360
//        double NELat=-11.805910334098213;
//        double NELong=-76.75915908813477;
//        double SWLat=-12.277709407228006;
//        double SWLong=-77.18620922875971;
//        double NWLat=-11.805910334098213;
//        double NWLong=-77.18620922875971; 
//        double SELat=-12.277709407228006;
//        double SELong=-76.75915908813477;
        
        model.addAttribute("latOrig",NW[0]);
        model.addAttribute("lngOrig",NW[1]);
        model.addAttribute("latLng",mapa.latLng(1.0));
        double latlng[]=mapa.latlngEsquina(mapa.NW[0],mapa.NW[1],1.0);
        distribucion= new Distribucion(mapa);
        model.addAttribute("cantV", mapa.cantV);
        model.addAttribute("cantH", mapa.cantH);
        model.addAttribute("latFin",latlng[0]);
        model.addAttribute("lngFin",latlng[1]);
        model.addAttribute("distritos",distribucion.distritos);
        model.addAttribute("idDistrito",distribucion.idDistrito);
        model.addAttribute("distritolst",distribucion.distritolst);
        model.addAttribute("vehiculosSolucion1",distribucion.vehiculosSolucion1);
        model.addAttribute("vehiculosSolucion2",distribucion.vehiculosSolucion2);
        model.addAttribute("vehiculosSolucion3",distribucion.vehiculosSolucion3);
       
    }


    

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }
    
    

}
