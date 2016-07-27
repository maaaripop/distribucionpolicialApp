/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo;

import java.util.ArrayList;
import java.util.Date;
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
    public int [][][][] delitos;
    public int cantPeriodo=10;
    public int cantMeses=1;
    public int [] fechasPeriodo;
    public double smoothFactor=0.25;
    public double [][][] delitosForecast;
    public Distribucion(){
        delitolst=(ArrayList<Delito>)delitoService.getAll();
        double[] NE={-11.805910334098213,-76.75915908813477};
        double[] SW={-12.277709407228006,-77.18620922875971};
        double[] NW={-11.805910334098213,-77.18620922875971};
        double[] SE={-12.277709407228006,-76.75915908813477};
        mapa= new Mapa(NE,SW,NW,SE);
        mapa.latLng(1.0);
        iniciarFechas();
        delitos = new int [mapa.cantH][mapa.cantV][cantPeriodo][3];
        // se usara para el contador de delitos de acuerdo al periodo que pertenezcan
        // y al turno
        for(int j=0;j<mapa.cantH;j++){
            for(int i=0;i<mapa.cantV;i++){
                for(int k=0;k<cantPeriodo;k++){
                    for(int l=0;l<3;l++){
                        delitos[j][i][k][l]=0;
                    }
                }
            }
        }
        
        delitosForecast = new double [mapa.cantH][mapa.cantV][3];
        for(int j=0;j<mapa.cantH;j++){
            for(int i=0;i<mapa.cantV;i++){
                for(int k=0;k<3;k++){
                    delitosForecast[j][i][k]=0;
                }
            }
        }
        
        forecasting();
        
    }
    public void iniciarFechas(){
        int cantMesesTotal = cantMeses*cantPeriodo;
        Date fechaPrimerPeriodo = new Date(2016,2,1);
        Date fechaHoy = new Date();
        int mesHoy= fechaHoy.getMonth();
        int anhoHoy= fechaHoy.getYear();
        int cantAnho=cantMesesTotal/12;
        int cantMesesRestantes= cantMesesTotal%12;
        int mes=0;
        int anho=anhoHoy-cantAnho;
        if(cantMesesRestantes<mesHoy) mes=mesHoy-cantMesesRestantes;
        else mes=12-(mesHoy-cantMesesRestantes);
        int codmes=anho*100+mes;
        fechasPeriodo[0]=codmes;
        for(int i=1;i<cantPeriodo;i++){
            if(mes+cantMeses<=12){
                mes=+cantMeses;
                fechasPeriodo[i]=anho*100+mes;
            }
            else {
                mes=1;
                anho=+1;
                fechasPeriodo[i]=anho*100+mes;
            }
            
        }
    }
    public void poblarDelitos(){
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
            int numPeriodo=buscarPeriodoPorDelito(d.getFecha());
            if(numPeriodo!=-1){
                delitos[distanciaH-1][distanciaV-1][d.getTurno().getIdTurno()][numPeriodo]=+1;
                // aumento la cantidad de delitos en ese cuadrado
            }
        }
    }
    
    public int buscarPeriodoPorDelito(Date fecha){
        int numPeriodo=-1;
        int mesDelito=fecha.getMonth();
        int anhoDelito=fecha.getYear();
        int codmesDelito=anhoDelito*100+mesDelito;
        if(fechasPeriodo[0]>codmesDelito) return numPeriodo;
        for(int j=0;j<cantPeriodo;j++){
            if(fechasPeriodo[j]>codmesDelito){
                numPeriodo=j;
                break;
            }
        }
        return numPeriodo;
    }
    
    public void forecasting(){
        // inicializar 
        for(int j=0;j<mapa.cantH;j++){
            for(int i=0;i<mapa.cantV;i++){
                for(int l=0;l<3;l++){
                        delitosForecast[j][i][l]=delitos[j][i][0][l];
                }
            }
        }
        for(int j=0;j<mapa.cantH;j++){
            for(int i=0;i<mapa.cantV;i++){
                for(int l=0;l<3;l++){
                    for(int k=1;k<cantPeriodo-1;k++){
                        delitosForecast[j][i][l]=(delitos[j][i][k-1][l])*smoothFactor+(delitosForecast[j][i][l])*(1-smoothFactor);
                    }
                }
            }
        }
    }
}
