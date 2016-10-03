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
public class ComisariaController {
    
    IComisaria comisariaService = ComisariaImpl.getInstance();
    IVehiculoxcomisaria vehiculoxcomisariaService = VehiculoxcomisariaImpl.getInstance();
    String [][] placasSerenazgo ;
    String [][] placasPatrullas ;
 // @RequestMapping(value = "/delitos", method = RequestMethod.GET)   
    @RequestMapping(value = "/comisarias", method = RequestMethod.GET)
    public String cargar(Model model) {
        model.addAttribute("comisaria", new Comisaria());
        ArrayList<Comisaria> comisariaLst = (ArrayList<Comisaria>)  this.comisariaService.getAll();
        model.addAttribute("comisariaLst",comisariaLst);
        obtenerPlacas(comisariaLst);
        model.addAttribute("vehiculoLst",placasPatrullas);
        model.addAttribute("serenazgoLst",placasSerenazgo);
       /*
        List<Delito> delitos = this.delitoService.getAll();
        LongLatService longLatService = new LongLatService();
        for (Delito d : delitos) {
                longLatService.obtenerDistrito(d.getLatitud(), d.getLongitud());
            }
        */
        return "comisaria";
    }
    
    public void obtenerPlacas(ArrayList<Comisaria> comisariaLst){
        int cantComisarias = comisariaLst.size();
        placasPatrullas = new String [cantComisarias+1][];  
        placasSerenazgo = new String [cantComisarias+1][]; 
        int i=0,j=0;
        for(Comisaria c: comisariaLst ){
            ArrayList<Vehiculoxcomisaria> patrullas = new ArrayList<Vehiculoxcomisaria>();
            ArrayList<Vehiculoxcomisaria> serenazgo = new ArrayList<Vehiculoxcomisaria>();
            ArrayList<Vehiculoxcomisaria> vehiculos =  (ArrayList<Vehiculoxcomisaria>)this.vehiculoxcomisariaService.queryByIdComisaria(c.getIdComisaria());
            for(Vehiculoxcomisaria v:vehiculos ){
                if(v.getVehiculo().getTipovehiculo().getIdtipoVehiculo()==1){
                    patrullas.add(v);
                } else serenazgo.add(v);
            }
            placasPatrullas[i]= new String[patrullas.size()+1];
            placasSerenazgo[i]= new String[serenazgo.size()+1];
            j=0;
            for(Vehiculoxcomisaria p: patrullas){
                placasPatrullas[i][j]=p.getVehiculo().getPlaca();
                j++;
            }
            j=0;
            for(Vehiculoxcomisaria p: serenazgo){
                placasSerenazgo[i][j]=p.getVehiculo().getPlaca();
                j++;
            }
        
        }
    
    }

 }
