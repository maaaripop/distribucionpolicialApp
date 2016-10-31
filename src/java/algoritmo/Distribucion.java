/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import model.dao.IComisaria;
import model.dao.IDelito;
import model.dao.IDistrito;
import model.dao.IDistritoxbloque;
import model.dao.IVehiculoxcomisaria;
import model.daoImpl.ComisariaImpl;
import model.daoImpl.DelitoImpl;
import model.daoImpl.DistritoImpl;
import model.daoImpl.DistritoxbloqueImpl;
import model.daoImpl.VehiculoxcomisariaImpl;
import model.pojo.Comisaria;
import model.pojo.Delito;
import model.pojo.Distrito;
import model.pojo.Distritoxbloque;
import model.pojo.Vehiculo;
import model.pojo.Vehiculoxcomisaria;
import util.LongLatService;

/**
 *
 * @author Mariella
 */
public class Distribucion {
    IDelito delitoService = DelitoImpl.getInstance();
    IDistritoxbloque distritoxbloqueService = DistritoxbloqueImpl.getInstance();
    IVehiculoxcomisaria vehiculoxcomisariaService = VehiculoxcomisariaImpl.getInstance();
    IComisaria comisariaService = ComisariaImpl.getInstance();
    IDistrito distritoService = DistritoImpl.getInstance();
    Mapa mapa =null;
    ArrayList<Delito> delitolst=null;
    ArrayList<Comisaria> comisarialst=null;
    public ArrayList<Distrito> distritolst=null;
    ArrayList<Distritoxbloque> distritoxbloquelst=null;
    ArrayList<Distritoxbloque> bloquelstAux=null;
    ArrayList<Distritoxbloque> bloquelst=null;
    
    ArrayList<int[]> bloquelstSol= new ArrayList<int[]>();
    ArrayList<String> vehiculoSol=new  ArrayList<String>();
    
    ArrayList<int[]> bloquelstSol1= new ArrayList<int[]>();
    ArrayList<String> vehiculoSol1=new  ArrayList<String>();
    
    ArrayList<int[]> bloquelstSol2= new ArrayList<int[]>();
    ArrayList<String> vehiculoSol2=new  ArrayList<String>();
    
    ArrayList<int[]> bloquelstSol3= new ArrayList<int[]>();
    ArrayList<String> vehiculoSol3=new  ArrayList<String>();
    
    ArrayList<Vehiculoxcomisaria> vehiculoxcomisarialst=null;
    ArrayList<Vehiculoxcomisaria> vehiculoxcomisariaAux=null;
    
    Comisaria comisaria = null;
    Comisaria comisariaAux= null;
    public int [][][][] delitos;
    public int cantPeriodo=10;
    public int cantMeses=1;
    public int [] fechasPeriodo;
    public double smoothFactor=0.25;
    public double [][][] delitosForecast;
    public String [][] vehiculosSolucion1;
    public String [][] vehiculosSolucion2;
    public String [][] vehiculosSolucion3;
    public int [][] distritos;
    public double beta=0;
    public double tau=0;
    LongLatService longLatService = new LongLatService();
    public int idTurno=1;
    public int idDistrito=26;//surquillo
    public int idComisaria=1;
    ArrayList<int[]> bloquesDelitos =null;
    ArrayList<int[]> bloques =null;
    public double [] latDistritos;
    public double [] lngDistritos;
    
    class MyComparator  implements Comparator<int[]> {
    @Override
    public int compare(int[] o1, int[] o2) {
        if (delitosForecast[o1[0]][o1[1]][idTurno] > delitosForecast[o2[0]][o2[1]][idTurno] ) {
            
            return -1;
        } else if (delitosForecast[o1[0]][o1[1]][idTurno] < delitosForecast[o2[0]][o2[1]][idTurno] ) {
            return 1;
        }
        return 0;
    }}
    
    
    public Distribucion(Mapa mapaVar){
        /***/
        int j,k,l,i=0;
        idTurno=1;
        delitolst=(ArrayList<Delito>)delitoService.getAll();
        comisarialst=(ArrayList<Comisaria>)comisariaService.queryByIdDistrito(idDistrito);
        comisaria = (Comisaria) comisariaService.queryById(idComisaria);
        lngDistritos= new double [comisarialst.size()+1];
        latDistritos= new double [comisarialst.size()+1];
        vehiculoxcomisarialst= (ArrayList<Vehiculoxcomisaria>)vehiculoxcomisariaService.queryByIdComisaria(idComisaria);
        vehiculoxcomisariaAux = new ArrayList<Vehiculoxcomisaria>();
        distritolst=(ArrayList<Distrito>)distritoService.getAll();
        double[] NE={-11.592316,-77.350372};
        double[] SW={-13.050655,-75.202545};
        double[] NW={-11.592316,-75.202545};
        double[] SE={-13.050655,-77.350372};
        mapa= mapaVar;
        fechasPeriodo= new int [cantPeriodo];
        iniciarFechas();
        delitos = new int [mapa.cantH][mapa.cantV][cantPeriodo][3];
        bloquesDelitos =new ArrayList<int[]>();
        bloques = new ArrayList<int[]>();
        
        distritos  = new int [mapa.cantH][mapa.cantV];
        vehiculosSolucion1  = new String [mapa.cantH][mapa.cantV];
        vehiculosSolucion2  = new String [mapa.cantH][mapa.cantV];
        vehiculosSolucion3  = new String [mapa.cantH][mapa.cantV];
        
        // se usara para el contador de delitos de acuerdo al periodo que pertenezcan
        // y al turno
        for(j=0;j<mapa.cantH;j++){
            for(i=0;i<mapa.cantV;i++){
                distritos [j][i]=-1;
                vehiculosSolucion1[j][i]=" ";
                vehiculosSolucion2[j][i]=" ";
                vehiculosSolucion3[j][i]=" ";
                for(k=0;k<cantPeriodo;k++){
                    for(l=0;l<3;l++){
                        delitos[j][i][k][l]=0;
                    }
                }
            }
        }
       
        distritoxbloquelst= (ArrayList<Distritoxbloque>)distritoxbloqueService.getAll();
        bloquelst= (ArrayList<Distritoxbloque>)distritoxbloqueService.queryByIdDistrito(idDistrito);
        if(distritoxbloquelst.size()==0){
            obtenerDistritos();
            // tiene un limite de consultas al servicio google map
            distritoxbloquelst= (ArrayList<Distritoxbloque>)distritoxbloqueService.getAll();
        }
        poblarBloques();
        
        poblarDelitos();
        //latLng  = new double[cantH+1][cantV+1][2];
        delitosForecast = new double [mapa.cantH][mapa.cantV][3];
        for(j=0;j<mapa.cantH;j++){
            for(i=0;i<mapa.cantV;i++){
                for(k=0;k<3;k++){
                    delitosForecast[j][i][k]=0;
                }
            }
        }
        forecasting();
        obtenerDelitosDistritos();
        //ordenarDistritosXDelitos();
        imprimirDelitos();
        grasp(10,0.8);
        
    }

    
    public void ordenarDistritosXDelitos(){
        ArrayList<int []> lstOrdenar=bloquesDelitos;
        // ordenar por delitoforecast
        Collections.sort(lstOrdenar, new MyComparator());
        lstOrdenar=bloquesDelitos;
    }
    public void imprimirDelitos(){
        int i=0;
        System.out.println("imprimir delitos");
        int idTurnoMenos= this.idTurno-1;
        for(i=0;i<bloquesDelitos.size();i++){
            int coord[]= new int[2];
            coord=bloquesDelitos.get(i);
            System.out.println("j " + coord[0] + "  i " + coord[1] +  " num delitos "  +delitosForecast[coord[0]][coord[1]][idTurnoMenos]);
        }
    }
    public int buscarDistrito(int j, int i){
        for( Distritoxbloque d: distritoxbloquelst){
            if(d.getI()==i && d.getJ()==j) return d.getDistrito().getIdDistrito();
        }
        return -1;
    }
    public void poblarBloques(){
        int j,i=0;
        for( Distritoxbloque d: distritoxbloquelst){
            j=d.getJ();
            i=d.getI();
            //if(d.getDistrito().getIdDistrito()==idDistrito) System.out.println("Es este distrito");
            distritos[j][i]=d.getDistrito().getIdDistrito();
        }
    }
    public void obtenerDistritos(){
        int j,i,l,k,aux,id=0;
        String distrito;
        double [] coordenada = new double [2];
        for(j=0;j<mapa.cantH;j++){
            //if(j==34){
            //    aux=28;
            //} else aux=0;
            for(i=0;i<mapa.cantV;i++){
                coordenada[0]=mapa.latLng[j][i][0];
                coordenada[1]=mapa.latLng[j][i][1];
                distrito= longLatService.obtenerDistrito(coordenada[0],coordenada[1]);
                distrito= distrito.replace(" District","");
                //google maps tiene SJL en vez de San juan de lurigancho
                // lo mismo con lurigancho y chosica
                if(distrito.equals("SJL")) distrito="San Juan de Lurigancho";
                if(distrito.equals("Lurigancho")) distrito="Lurigancho-Chosica";
                if(distrito.equals("Chosica")) distrito="Lurigancho-Chosica";
                System.out.println(distrito);
                id=buscarIdDistrito(cleanString(distrito));
                if(id!=-1){
                    /*if(delitosForecast[j][i][idTurno]>0){
                        if(id==idDistrito){
                            distritosDelitos.add(num);
                        }
                    }*/
                    //insertar en la bd
                    System.out.println(id);
                    distritoxbloqueService.save(new Distritoxbloque(distritoService.queryById(id),i,j));
                }
            }
        }
    }
    public static String cleanString(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return texto;
    }
    public void obtenerDelitosDistritos(){
        // inicializar
        int j,i=0;
        int idTurnoMenos=this.idTurno-1;
        for(j=0;j<mapa.cantH;j++){
            for(i=0;i<mapa.cantV;i++){
                if(delitosForecast[j][i][idTurnoMenos]>0){
                    int id= distritos[j][i];
                    //System.out.println("idDistrito " + id  +" "+  j + " " + i );
                    if(id==idDistrito){
                        int [] num = new int [2];
                        num[0]=j;
                        num[1]=i;
                        System.out.println("idDistrito " + id  +" "+  num[0] + " " + num[1] );
                        bloquesDelitos.add(num);
                    }
                }
            }
        }
        
        // aplicativo movil (registro de delitos)
        // aplicativo web (registro de delitos, algoritmo, registro de comisarias de vehiculos, configuracion)
        
    }
    public int buscarIdDistrito(String distritoABuscar){
        for( Distrito d : distritolst){
            String distrito = cleanString(d.getNombre().toLowerCase());
            if(distrito.equals(distritoABuscar.toLowerCase())){
                return d.getIdDistrito();
            }
        }
        return -1;
    }
    public double costoTotal(){
        int i,j=0;
        double costo=0;
        double costoAux=0;
        double delitos=0;
        double distancia=0;
        int idTurnoMenos = this.idTurno-1;
        /*for(Distritoxbloque d: bloquelst){
            j=d.getJ();
            i=d.getI();
            delitos=delitosForecast[j][i][idTurno];
            distancia=mapa.distanciaCoord(mapa.latLng[j][i][0], mapa.latLng[j][i][1], comisaria.getLatitud(), comisaria.getLongitud());
            costoAux=delitos/distancia;
            costo=costo+costoAux;
        }
        */
        for(int[] d: bloquelstSol){
            j=d[0];
            i=d[1];
            delitos=delitosForecast[j][i][idTurnoMenos];
            distancia=mapa.distanciaCoord(mapa.latLng[j][i][0], mapa.latLng[j][i][1], comisaria.getLatitud(), comisaria.getLongitud());
            costoAux=delitos/distancia;
            costo=costo+costoAux;
        }
        return costo;
    }
    public void grasp(int numIteraciones, double alpha){
        int i=0;
        double costo1=0;
        double costo2=0;
        double costo3=0;
        double costoActual=0;
        for(i=0;i<numIteraciones;i++){
            
            bloquelstSol= new ArrayList<int[]>();
            vehiculoSol=new  ArrayList<String>();
            faseConstructiva(alpha);
            costoActual=costoTotal();
            System.out.println("*************iteracion******" + i + " costo: " + costoActual );
            if(costo1<costoActual){
                costo1=costoActual;
                //cargo solucion a solucion1
                cargoSolucion(1);
            } else {
            
                if (costo2<costoActual){
                    costo2=costoActual;
                    //cargo solucion a solucion2
                    cargoSolucion(2);
                }
                else {
                    if (costo3<costoActual){
                        costo3=costoActual;
                        cargoSolucion(3);
                    }
                
                }
            
            }
        }
        System.out.println("*************costo 1******" + costo1 );
        System.out.println("*************costo 2******" + costo2 );
        System.out.println("*************costo 3******" + costo3 );
        convertir();
        
    }
    
    public void cargoSolucion(int numSolucion){
        int i=0;
        if(numSolucion==1){
            bloquelstSol1= new ArrayList<int[]>();
            vehiculoSol1=new  ArrayList<String>();
            for(int[] d: bloquelstSol){
                bloquelstSol1.add(d);
                vehiculoSol1.add(vehiculoSol.get(i));
                i++;
            }
            return;
        }
        
        if(numSolucion==2){
            bloquelstSol2= new ArrayList<int[]>();
            vehiculoSol2=new  ArrayList<String>();
            for(int[] d: bloquelstSol){
                bloquelstSol2.add(d);
                vehiculoSol2.add(vehiculoSol.get(i));
                i++;
            }
            return;
        }
         
        if(numSolucion==3){
            
            bloquelstSol3= new ArrayList<int[]>();
            vehiculoSol3=new  ArrayList<String>();
            for(int[] d: bloquelstSol){
                bloquelstSol3.add(d);
                vehiculoSol3.add(vehiculoSol.get(i));
                i++;
            }
            return;
        }
    
    }
    public void faseConstructiva(double alpha){
        longLatService = new LongLatService();
        int j,i=0;
        String placa=" ";
        comisariaAux = new Comisaria(comisaria.getIdComisaria(), comisaria.getDistrito(), comisaria.getNombre(), comisaria.getLatitud(), comisaria.getLongitud(), comisaria.getCantPatrulla(), comisaria.getCantPatrullaSerenazgo(),null,null ,null);
        bloques = new ArrayList<int[]>();
        vehiculoxcomisariaAux= new ArrayList<Vehiculoxcomisaria>();
        for(int[] d: bloquesDelitos){
            bloques.add(d);
        }
        for(Vehiculoxcomisaria v: vehiculoxcomisarialst){
            vehiculoxcomisariaAux.add(v);
        }
        int cantDelitos=bloques.size();
        System.out.println("cantDelitos : " + cantDelitos);
        int cantVehiculo= vehiculoxcomisariaAux.size();
        while( cantDelitos!=0 && (quedaVehiculos() || quedaVehiculosSerenazgo())){
            placa=buscarVehiculo();
            System.out.println("placa : " + placa);
            inicializar();
            ArrayList<int[]> bloquesLCR = obtenerLCR(alpha);
           
            int random= (int) (Math.random()*bloquesLCR.size());
            int delito[] =  bloquesLCR.get(random);
            eliminarBloque(delito[0],delito[1]);
            cantDelitos=bloques.size();
            //voy guardando la solución
            bloquelstSol.add(delito);
            vehiculoSol.add(placa);
            
        }
    }
    
    public void convertir(){
        int i=0;
        for(int[] d: bloquelstSol1){
                vehiculosSolucion1[d[0]][d[1]]= vehiculoSol1.get(i);
                i++;
        }
        i=0;
        for(int[] d: bloquelstSol2){
                vehiculosSolucion2[d[0]][d[1]]= vehiculoSol2.get(i);
                i++;
        }
        i=0;
        for(int[] d: bloquelstSol3){
                vehiculosSolucion3[d[0]][d[1]]= vehiculoSol3.get(i);
                i++;
        }
        
        //esta funcion llena este arreglo para mostrarlo en los view 
        //vehiculosSolucion[delito[0]][delito[1]]=placa;
    }
    public void eliminarBloque (int j, int i){
        int y=0;
        for(int[] bloque: bloques){
            if(bloque[0]==j && bloque[1]==i){
                
                bloques.remove(y);
                return;
            }
            y++;
        }
    }
    public String buscarVehiculo(){
        // si hay alguna patrulla (policia) lo bota primero, sino botara un serenazgo
        // eliminar el vehiculo seleccionado
        String placa=null;
        int cantVehiculo=vehiculoxcomisariaAux.size();
        
        int random= (int) (Math.random()*vehiculoxcomisariaAux.size());
        boolean hayPatrullas = quedaVehiculos();
        while(hayPatrullas && !(vehiculoxcomisariaAux.get(random).getVehiculo().getTipovehiculo().getIdtipoVehiculo()==1)){
            random= (int) (Math.random()*vehiculoxcomisariaAux.size());
        }
        if(hayPatrullas) {
            placa=vehiculoxcomisariaAux.get(random).getVehiculo().getPlaca();
            vehiculoxcomisariaAux.remove(random);
            actualizarCantPatrulla();
            return placa;
        
        }
        
        placa=vehiculoxcomisariaAux.get(random).getVehiculo().getPlaca();
        vehiculoxcomisariaAux.remove(random);
        actualizarCantPatrullaSerenazgo();
        return placa;
    }
    public void inicializar(){
        int i=0,j=0;
        beta = 0;
        tau= 0;
        int flag=0;
        double distancia;
        double delitos;
        double costoAux;
        int idTurnoMenos=this.idTurno-1;
        for( int[] bloque : bloques){
            j=bloque[0];
            i=bloque[1];
            delitos=delitosForecast[j][i][idTurnoMenos];
            distancia=mapa.distanciaCoord(mapa.latLng[j][i][0], mapa.latLng[j][i][1], comisaria.getLatitud(), comisaria.getLongitud());
            costoAux=delitos/distancia;
            if(costoAux>0){
                if (flag == 0){
                    tau= costoAux;
                    beta = costoAux;
                    flag =1;
                }else
                { //beta es el maximo, tau es el minimo
                    if (costoAux<tau) 
                            tau=costoAux;
                    if (costoAux>beta) 
                            beta=costoAux;
                }
            }
        }

// System.out.println("Este ees el costo calculado " + costoAux);  
// beta es el mejor valor es decir el minimo
// tau es el peor valor es decir el maximo 
//System.out.println(costoAux);
//      System.out.println("tau " + tau + " beta " + beta);
        
  }
    public ArrayList<int[]> obtenerLCR(double alpha){
        ArrayList<int[]> bloquesLCR =new ArrayList<int[]>();
        double costoAux,delitos,distancia = 0;
        int j,i=0;
        int idTurnoMenos=this.idTurno-1;
        //min peor valor tau
        //maxima mejor valor beta
        for( int[] bloque : bloques){
            j=bloque[0];
            i=bloque[1];
            delitos=delitosForecast[j][i][idTurnoMenos];
            distancia=mapa.distanciaCoord(mapa.latLng[j][i][0], mapa.latLng[j][i][1], comisaria.getLatitud(), comisaria.getLongitud());
            costoAux=delitos/distancia;      

            // maximizar la funcion (tesis)
            if ( tau + alpha*(beta-tau) <=costoAux  &&costoAux<=beta){
                //minimizar funcion
                //System.out.println("Costo aux LCR " + costoAux);
                bloquesLCR.add(bloque);
            }
        }
        //System.out.println("tamaño " + nuevoPedidos.size());
        return bloquesLCR;
    } 
    public void actualizarCantPatrulla(){
        comisariaAux.setCantPatrulla( comisariaAux.getCantPatrulla()-1);
    }
    public void actualizarCantPatrullaSerenazgo(){
         comisariaAux.setCantPatrullaSerenazgo( comisariaAux.getCantPatrullaSerenazgo()-1);
    }
    public boolean quedaVehiculos(){
        if(comisariaAux.getCantPatrulla()>0) return true;
        else return false;
    }
    public boolean quedaVehiculosSerenazgo(){
        if(comisariaAux.getCantPatrullaSerenazgo()>0) return true;
        else return false;
    }
    public ArrayList<Comisaria> buscarComisarias(){
        ArrayList<Comisaria> comisariasXdistritos= new ArrayList<Comisaria>();
            for(Comisaria c: comisarialst ){
                if(c.getDistrito().getIdDistrito()==idDistrito){
                    comisariasXdistritos.add(c);
                }
            }
        return comisariasXdistritos;
    
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
        Date fechaHoy = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaHoy);
        int mesHoy = cal.get(Calendar.MONTH)+2;
        int anhoHoy= cal.get(Calendar.YEAR);
        int cantAnho=cantMesesTotal/12;
        int cantMesesRestantes= cantMesesTotal%12;
        int mes=0;
        int anho=anhoHoy-cantAnho;
        if(cantMesesRestantes<mesHoy){
            mes=mesHoy-cantMesesRestantes;
        }
        else {
            anho--;
            mes=12-(cantMesesRestantes-mesHoy);
        }
        int codmes=anho*100+mes;
        fechasPeriodo[0]=codmes;
        System.out.println(fechasPeriodo[0]);
        for(int i=1;i<cantPeriodo;i++){
            if(mes+cantMeses<=12){//12+6 = 18
                mes=mes+cantMeses;
                fechasPeriodo[i]=anho*100+mes;
            }
            else {
                mes=cantMeses;//6
                anho=anho+1;
                fechasPeriodo[i]=anho*100+mes;
            }
            System.out.println(fechasPeriodo[i]);
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
            int distanciaH=(int) mapa.distanciaCoord(mapa.NW[0], mapa.NW[1], coordIntermedia[0], coordIntermedia[1]);
            int distanciaV=(int) mapa.distanciaCoord(coordIntermedia[0], coordIntermedia[1],coord[0],coord[1]);
            int numPeriodo=buscarPeriodoPorDelito(d.getFecha());
            int id= distritos[distanciaH][distanciaV];
                
            if(id==this.idDistrito){
                
                System.out.println("Delito:  " + d.getIdDelito());
                System.out.println(" fecha: " + d.getFecha() + "distrito " + id + " periodo: " +numPeriodo + " turno: " + idTurno +" lat: " + d.getLatitud() + " long: " + d.getLongitud() + " " + distanciaH+ " " + distanciaV ); 
            
                
                if(numPeriodo!=-1 && id==this.idDistrito){

                    //System.out.println("periodo: " +numPeriodo + " Lat: " + d.getLatitud() + " Long: " + d.getLongitud() +" " + distanciaH--+ " " + distanciaV-- +"" );
                    if (idTurno==this.idTurno) {

                        idTurno--;
                        int cantDelitos=delitos[distanciaH][distanciaV][numPeriodo][idTurno];
                        delitos[distanciaH][distanciaV][numPeriodo][idTurno]=cantDelitos+1;
                        System.out.println(" fecha: " + d.getFecha() + " periodo: " +numPeriodo + " turno: " + this.idTurno +" " + distanciaH+ " " + distanciaV +" numDelitos: "+ delitos[distanciaH][distanciaV][numPeriodo][idTurno]); 
                    }
                    // aumento la cantidad de delitos en ese cuadrado
                }
                
            }
                
        }
    }
    public int buscarPeriodoPorDelito(Date fecha){
        int numPeriodo=-1;
        
        /*
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaHoy);
        int mesHoy = cal.get(Calendar.MONTH)+1;
        */
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        int mesDelito=cal.get(Calendar.MONTH)+1;
        int anhoDelito=cal.get(Calendar.YEAR);
        int codmesDelito=anhoDelito*100+mesDelito;
        if(fechasPeriodo[0]>codmesDelito) return numPeriodo;
        for(int j=0;j<cantPeriodo;j++){
            if(fechasPeriodo[j]>=codmesDelito){
                numPeriodo=j;
                break;
            }
        }
        return numPeriodo;
    }
    public void forecasting(){
        
        int j,i,l,k=0;
        // inicializar 
        for(j=0;j<mapa.cantH;j++){
            for(i=0;i<mapa.cantV;i++){
                for(l=0;l<3;l++){
                        delitosForecast[j][i][l]=delitos[j][i][0][l];
                }
            }
        }
        for(j=0;j<mapa.cantH;j++){
            for(i=0;i<mapa.cantV;i++){
                for(l=0;l<3;l++){
                    for(k=1;k<cantPeriodo;k++){
                        //if(delitos[j][i][k][l]>0) System.out.println("Periodo: "+ k + ","+ i + " " + j+ " Mayor a cero");
                        delitosForecast[j][i][l]=(delitos[j][i][k-1][l])*smoothFactor+(delitosForecast[j][i][l])*(1-smoothFactor);
                    }
                    int idTurnoMenos=this.idTurno-1;
                    if(delitosForecast[j][i][l]>0 && l==idTurnoMenos) {
                        System.out.println("Forecast: " + i + " " + j+ " Turno: " + this.idTurno + " predDelitos " +delitosForecast[j][i][l]);
                    }
                }
            }
        }
    }
}
