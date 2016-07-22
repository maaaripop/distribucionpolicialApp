/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo;

import java.util.ArrayList;
import model.dao.IDelito;
import model.daoImpl.DelitoImpl;
import model.pojo.Delito;

/**
 *
 * @author Mariella
 */
public class Distribucion {
    IDelito delitoService = DelitoImpl.getInstance();
    Mapa mapa =null;
    ArrayList<Delito> delitolst=null;
    public Distribucion(){
        delitolst=(ArrayList<Delito>)delitoService.getAll();
        double[] NE={-11.805910334098213,-76.75915908813477};
        double[] SW={-12.277709407228006,-77.18620922875971};
        double[] NW={-11.805910334098213,-77.18620922875971};
        double[] SE={-12.277709407228006,-76.75915908813477};
        mapa= new Mapa(NE,SW,NW,SE);
        mapa.latLng(1.0);
        
    }
    
    public void contarDelitos(){
        
        for ( Delito d : delitolst ) {
            int idTurno = d.getTurno().getIdTurno();
            double [] coord = {d.getLatitud(),d.getLongitud()} ;
            double [] coordIntermedia= {coord[0],mapa.NW[1]};
            /*distanciaV
            /*
            /*
            /* * * *
            /distanciaH 
            */
            int distanciaV=(int) mapa.distanciaCoord(mapa.NW[0], mapa.NW[1], coordIntermedia[0], coordIntermedia[1]);
            int distanciaH=(int) mapa.distanciaCoord(coordIntermedia[0], coordIntermedia[1],coord[0],coord[1]);
            mapa.latLng[distanciaH-1][distanciaV-1][3]=+1; // aumento la cantidad de delitos en ese cuadrado
            
        }
       
    
    
    }

}
