/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo;

import java.util.ArrayList;
import java.util.Date;
import model.dao.IComisaria;
import model.dao.IDelito;
import model.daoImpl.ComisariaImpl;
import model.daoImpl.DelitoImpl;
import model.pojo.Comisaria;
import model.pojo.Delito;
import util.LongLatService;

/**
 *
 * @author Mariella
 */
public class Distribucion {
    IDelito delitoService = DelitoImpl.getInstance();
    IComisaria comisariaService = ComisariaImpl.getInstance();
    Mapa mapa =null;
    ArrayList<Delito> delitolst=null;
    ArrayList<Comisaria> comisarialst=null;
    public int [][][][] delitos;
    public int cantPeriodo=10;
    public int cantMeses=1;
    public int [] fechasPeriodo;
    public double smoothFactor=0.25;
    public double [][][] delitosForecast;
    public int [][] comisarias;
    public double beta=0;
    public double tau=0;
    public Distribucion(){
        delitolst=(ArrayList<Delito>)delitoService.getAll();
        comisarialst=(ArrayList<Comisaria>)comisariaService.getAll();
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
        //grasp();
        
    }
    public void grasp(int numIteraciones, double alpha,int idTurno){
        
        for(int i=0;i<numIteraciones;i++){
            faseConstructiva(alpha,idTurno);
            
        
        }
    }
    
    public void faseConstructiva(double alpha, int idTurno){
        LongLatService longLatService = new LongLatService();
        comisarias = new int [mapa.cantH][mapa.cantV];
        for(int j=0;j<mapa.cantH;j++){
            for(int i=0;i<mapa.cantV;i++){
                comisarias[j][i]=-1;
            }
        }
        for(int j=0;j<mapa.cantH-1;j++){
            for(int i=0;i<mapa.cantV-1;i++){
                ArrayList<String> distritos= new ArrayList<String>();
                double lat =mapa.latLng[j][i][0];
                double lng =mapa.latLng[j][i][1];
                // d1
                distritos.add(longLatService.obtenerDistrito(lat, lng));
                lat =mapa.latLng[j][i+1][0];
                lng=mapa.latLng[j][i+1][1];
                // d2
                distritos.add(longLatService.obtenerDistrito(lat, lng));
                lat =mapa.latLng[j+1][i+1][0];
                lng=mapa.latLng[j+1][i+1][1];
                // d3
                distritos.add(longLatService.obtenerDistrito(lat, lng));
                lat =mapa.latLng[j+1][i][0];
                lng=mapa.latLng[j+1][i][1];
                // d4
                distritos.add(longLatService.obtenerDistrito(lat, lng));
                distritos = limpiarDuplicados(distritos);
                ArrayList<Comisaria> comisariasXdistritos = buscarComisarias(distritos);
                if(comisariasXdistritos.size()>0){
                    inicializar(i,j,comisariasXdistritos,idTurno);
                    ArrayList<Comisaria> comisariasLCR = obtenerLCR(i,j,alpha,idTurno,comisariasXdistritos);
                    int random= (int) (Math.random()*comisariasLCR.size());
                    int idComisaria =  comisariasLCR.get(random).getIdComisaria();
                    comisarias[j][i]=idComisaria;
                }
                else comisarias[j][i]=-1;
            }
        }
    }
    public void inicializar(int i, int j, ArrayList<Comisaria> comisariasXdistritos, int idTurno){
        beta = 0;
        tau= 0;
        int flag=0;
        double distancia;
        double delitos;
        double costoAux;
        for( Comisaria c : comisariasXdistritos){
            delitos=delitosForecast[j][i][idTurno];
            distancia=mapa.distanciaCoord(mapa.latLng[j][i][0], mapa.latLng[j][i][1], c.getLatitud(), c.getLongitud());
            costoAux=delitos/distancia;
            if(costoAux>0){
                if (flag == 0){
                    tau= costoAux;
                    beta = costoAux;
                    flag =1;
                }else
                {
                    if (costoAux<beta) 
                            beta=costoAux;
                    if (costoAux>tau) 
                            tau=costoAux;
                }
            }
        }

// System.out.println("Este ees el costo calculado " + costoAux);  
// beta es el mejor valor es decir el minimo
// tau es el peor valor es decir el maximo 
//System.out.println(costoAux);
//      System.out.println("tau " + tau + " beta " + beta);
  }
    public ArrayList<Comisaria> obtenerLCR(int i, int j,double alpha, int idTurno, ArrayList<Comisaria> comisariasXdistritos){
        ArrayList<Comisaria> comisariasLCR =new ArrayList<Comisaria>();
        double costoAux = 0;
        double delitos;
        double distancia;
        //maximo peor valor tau
        //minimo mejor valor beta
        for( Comisaria c : comisariasXdistritos){
            delitos=delitosForecast[j][i][idTurno];
            distancia=mapa.distanciaCoord(mapa.latLng[j][i][0], mapa.latLng[j][i][1], c.getLatitud(), c.getLongitud());
            costoAux=delitos/distancia;      
            //minizar la funcion 
            // maximizar la funcion (tesis)
            if ( beta<=costoAux  &&costoAux<=( beta + alpha*(tau-beta))){
                //System.out.println("Costo aux LCR " + costoAux);
                comisariasLCR.add(c);
            }
      
        }
        //System.out.println("tamaño " + nuevoPedidos.size());
        return comisariasLCR;
     
     
     
    } 
    public ArrayList<Comisaria> buscarComisarias(ArrayList<String> distritos){
        ArrayList<Comisaria> comisariasXdistritos= new ArrayList<Comisaria>();
        for(String d: distritos){
            for(Comisaria c: comisarialst ){
                if(c.getDistrito().getNombre().equals(d)){
                    comisariasXdistritos.add(c);
                }

            }
        }
        return comisariasXdistritos;
    
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
            if(mes+cantMeses<=12){//12+6 = 18
                mes=+cantMeses;
                fechasPeriodo[i]=anho*100+mes;
            }
            else {
                mes=cantMeses;//6
                anho=+1;
                fechasPeriodo[i]=anho*100+mes;
            }
            
        }
    }
    public ArrayList<String> limpiarDuplicados(ArrayList<String> cadenas){
        ArrayList <String> unicos = new ArrayList<String>();
        for( String s : cadenas){
            if(!unicos.contains(s)) unicos.add(s);
        }
        return unicos;
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
