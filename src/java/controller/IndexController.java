/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import algoritmo.Mapa;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.dao.IDelito;
import model.dao.ITipodelito;
import model.dao.ITurno;
import model.daoImpl.DelitoImpl;
import model.daoImpl.TipodelitoImpl;
import model.daoImpl.TurnoImpl;
import model.pojo.Delito;
import model.pojo.Tipodelito;
import model.pojo.Turno;
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
public class IndexController {

    ITipodelito tipodelitoService = TipodelitoImpl.getInstance();
    IDelito delitoService = DelitoImpl.getInstance();
    ITurno turnoService = TurnoImpl.getInstance();
    Mapa mapa= null;

    @RequestMapping(value = "/delitos", method = RequestMethod.GET)
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
        model.addAttribute("lngOrig",NWLong);
        model.addAttribute("cantV", distanciaV);
        model.addAttribute("cantH", distanciaH);
        model.addAttribute("latLng",latLng(NWLat,NWLong,1.0,(int)distanciaV,(int)distanciaH));
        double latlng[]=latlngEsquina(NWLat,NWLong,1.0);
        model.addAttribute("latFin",latlng[0]);
        model.addAttribute("lngFin",latlng[1]);
        /*
        List<Delito> delitos = this.delitoService.getAll();
        LongLatService longLatService = new LongLatService();
        for (Delito d : delitos) {
                longLatService.obtenerDistrito(d.getLatitud(), d.getLongitud());
            }
        */
        return "delito";
    }

    @RequestMapping(value = "/delitos/nuevo", method = RequestMethod.POST)
    public String agregarDelito(@ModelAttribute("delito") Delito td) {
        // no debe de permitir ingresar registros fuera del area de lima metropolitana
        delitoService.save(td);
        return "redirect:/delitos";

    }
    @RequestMapping(value="/tipodelitos/{idTipoDelito}", method = RequestMethod.GET)
    public @ResponseBody Tipodelito getTipoDelitoJson(@PathVariable("idTipoDelito") int idTipoDelito){
        Tipodelito td=tipodelitoService.queryById(idTipoDelito);
        return td;
    }
    
    @RequestMapping(value="/tipodelitos/all", method = RequestMethod.GET)
    public @ResponseBody List<Tipodelito> getTipoDelitoJson(){
        List<Tipodelito> td=tipodelitoService.getAll();
        return td;
    }
    
    @RequestMapping(value="/turnos/{idTurno}", method = RequestMethod.GET)
    public @ResponseBody Turno getTurnoJson(@PathVariable("idTurno") int idTurno){
        Turno td=turnoService.queryById(idTurno);
        return td;
    }
    
    @RequestMapping(value="/turnos/all", method = RequestMethod.GET)
    public @ResponseBody List<Turno> getTurnoJson(){
        List<Turno> td=turnoService.getAll();
        return td;
    }
    
    @RequestMapping(value="/delitos/all", method = RequestMethod.GET)
    public @ResponseBody List<Delito> getDelitoJson(){
        List<Delito> td=delitoService.getAll();
        return td;
    }
    
    @RequestMapping(value="/delitos/{idDelito}", method = RequestMethod.GET)
    public @ResponseBody Delito getDelitoJson(@PathVariable("idDelito") int idDelito){
        Delito td=delitoService.queryById(idDelito);
        return td;
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

    }
    
    public double[] latlngEsquina(double lat1, double lng1, double distancia){
        double latlng[]=latLngDistancia(lat1,lng1,distancia,180.0);
        return latLngDistancia(latlng[0],latlng[1],distancia,90.0);
    
    }
    public  double distanciaCoord(double lat1, double lng1, double lat2, double lng2) {  
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
    

    public double[] latLngDistancia(double lat1, double lng1, double distancia, double bearing){
        
	double theta = Math.toRadians(bearing);
	double R = 6371;
        double distanciaR= distancia*1.0/R;
        lat1=Math.toRadians(lat1);
        lng1=Math.toRadians(lng1);
        double lat2 = Math.asin( Math.sin(lat1)*Math.cos(distanciaR) +
                        Math.cos(lat1)*Math.sin(distanciaR)*Math.cos(theta) );
        double lng2 = lng1 + Math.atan2(Math.sin(theta)*Math.sin(distanciaR)*Math.cos(lat1),
                             Math.cos(distanciaR)-Math.sin(lat1)*Math.sin(lat2));
        
        double latlng[]= new double[2];
        latlng[0]=Math.toDegrees(lat2);
        latlng[1]=Math.toDegrees(lng2);
        return latlng;
//    
    }
    
    public double[][][] latLng(double latOrig, double lngOrig, double distancia, int cantV,int cantH){
        double [][][] latLng  = new double[cantH+1][cantV+1][2];
        latLng[0][0][0]=latOrig;
        latLng[0][0][1]=lngOrig;
        for(int j=1;j<cantH;j++){
            double [] unidad=latLngDistancia(latLng[j-1][0][0],latLng[j-1][0][1],distancia,180.0);
            latLng[j][0][0]=unidad[0];
            latLng[j][0][1]=unidad[1];
        }
        for(int i=1;i<cantV;i++){
            double [] unidad=latLngDistancia(latLng[0][i-1][0],latLng[0][i-1][1],distancia,90.0);
            latLng[0][i][0]=unidad[0];
            latLng[0][i][1]=unidad[1];
        }
        for(int j=1;j<cantH;j++){
            for(int i=1;i<cantV;i++){
                double [] unidad=latlngEsquina(latLng[j-1][i-1][0],latLng[j-1][i-1][1],distancia);
                latLng[j][i][0]=unidad[0];
                latLng[j][i][1]=unidad[1];
            }
        }
        
        return latLng;
    }
    
}
