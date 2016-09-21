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
    ArrayList<Distritoxbloque> bloquelst=null;
    ArrayList<Vehiculoxcomisaria> vehiculoxcomisarialst=null;
    Comisaria comisaria = null;
    public int [][][][] delitos;
    public int cantPeriodo=10;
    public int cantMeses=1;
    public int [] fechasPeriodo;
    public double smoothFactor=0.25;
    public double [][][] delitosForecast;
    public int [][] vehiculosSolucion;
    public int [][] distritos;
    public double beta=0;
    public double tau=0;
    LongLatService longLatService = new LongLatService();
    public int idTurno=0;
    public int idDistrito=26;//surquillo
    public int idComisaria=1;
    ArrayList<int[]> bloquesDelitos =null;
    
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
        vehiculoxcomisarialst= (ArrayList<Vehiculoxcomisaria>)vehiculoxcomisariaService.queryByIdComisaria(idComisaria);
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
        distritos  = new int [mapa.cantH][mapa.cantV];
        vehiculosSolucion  = new int [mapa.cantH][mapa.cantV];
        // se usara para el contador de delitos de acuerdo al periodo que pertenezcan
        // y al turno
        for(j=0;j<mapa.cantH;j++){
            for(i=0;i<mapa.cantV;i++){
                distritos [j][i]=-1;
                vehiculosSolucion[j][i]=-1;
                for(k=0;k<cantPeriodo;k++){
                    for(l=0;l<3;l++){
                        delitos[j][i][k][l]=0;
                    }
                }
            }
        }
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
        distritoxbloquelst= (ArrayList<Distritoxbloque>)distritoxbloqueService.getAll();
        bloquelst= (ArrayList<Distritoxbloque>)distritoxbloqueService.queryByIdDistrito(idDistrito);
        if(distritoxbloquelst.size()==0){
            obtenerDistritos();
            // tiene un limite de consultas al servicio google map
            distritoxbloquelst= (ArrayList<Distritoxbloque>)distritoxbloqueService.getAll();
        }
        poblarBloques();
        obtenerDelitosDistritos();
        ordenarDistritosXDelitos();
        imprimirDelitos();
        grasp(1,0.8);
        
    }
    public void ordenarDistritosXDelitos(){
        ArrayList<int []> lstOrdenar=bloquesDelitos;
        // ordenar por delitoforecast
        Collections.sort(lstOrdenar, new MyComparator());
        lstOrdenar=bloquesDelitos;
    }
    public void imprimirDelitos(){
        int i=0;
        for(i=0;i<bloquesDelitos.size();i++){
            int coord[]= new int[2];
            coord=bloquesDelitos.get(i);
            System.out.println("j " + coord[0] + "  i " + coord[1] +  " num delitos "  +delitosForecast[coord[0]][coord[1]][idTurno]);
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
        
        for(j=0;j<mapa.cantH;j++){
            for(i=0;i<mapa.cantV;i++){
                if(delitosForecast[j][i][idTurno]>0){
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
        for(Distritoxbloque d: bloquelst){
            j=d.getJ();
            i=d.getI();
            delitos=delitosForecast[j][i][idTurno];
            distancia=mapa.distanciaCoord(mapa.latLng[j][i][0], mapa.latLng[j][i][1], comisaria.getLatitud(), comisaria.getLongitud());
            costoAux=delitos/distancia;
            costo=costo+costoAux;
        }
        return costo;
    }
    public void grasp(int numIteraciones, double alpha){
        int i=0;
        for(i=0;i<numIteraciones;i++){
            faseConstructiva(alpha);
        }
    }
    public void faseConstructiva(double alpha){
        longLatService = new LongLatService();
        int j,i=0;
        int idPlaca=0;
        int cantDelitos=bloquesDelitos.size();
        int cantVehiculo= vehiculoxcomisarialst.size();
        while( cantDelitos!=0 && (quedaVehiculos() || quedaVehiculosSerenazgo())){
            idPlaca=buscarVehiculo();
            inicializar();
            ArrayList<int[]> bloquesLCR = obtenerLCR(alpha);
            int random= (int) (Math.random()*bloquesLCR.size());
            int delito[] =  bloquesLCR.get(random);
            eliminarBloque(delito[0],delito[1]);
            vehiculosSolucion[delito[0]][delito[1]]=idPlaca;
            cantDelitos=bloquesDelitos.size();
        }
    }
    public void eliminarBloque (int j, int i){
        int y=0;
        for(int[] bloque: bloquesDelitos){
            if(bloque[0]==j && bloque[1]==i){
                bloquesDelitos.remove(y);
                return;
            }
            y++;
        }
    }
    public int buscarVehiculo(){
        // si hay alguna patrulla (policia) lo bota primero, sino botara un serenazgo
        // eliminar el vehiculo seleccionado
        int cantVehiculo=vehiculoxcomisarialst.size();
        int random= (int) (Math.random()*vehiculoxcomisarialst.size());
        boolean hayPatrullas = quedaVehiculos();
        while(hayPatrullas && !(vehiculoxcomisarialst.get(random).getVehiculo().getTipovehiculo().getIdtipoVehiculo()==1)){
            random= (int) (Math.random()*vehiculoxcomisarialst.size());
        }
        if(hayPatrullas) {
            vehiculoxcomisarialst.remove(random);
            return vehiculoxcomisarialst.get(random).getVehiculo().getIdvehiculo();
        }
        vehiculoxcomisarialst.remove(random);
        return vehiculoxcomisarialst.get(random).getVehiculo().getIdvehiculo();
    }
    public void inicializar(){
        int i=0,j=0;
        beta = 0;
        tau= 0;
        int flag=0;
        double distancia;
        double delitos;
        double costoAux;
        for( int[] bloque : bloquesDelitos){
            j=bloque[0];
            i=bloque[1];
            delitos=delitosForecast[j][i][idTurno];
            distancia=mapa.distanciaCoord(mapa.latLng[j][i][0], mapa.latLng[j][i][1], comisaria.getLatitud(), comisaria.getLongitud());
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
    public ArrayList<int[]> obtenerLCR(double alpha){
        ArrayList<int[]> bloquesLCR =new ArrayList<int[]>();
        double costoAux,delitos,distancia = 0;
        int j,i=0;
        //maximo peor valor tau
        //minimo mejor valor beta
        for( int[] bloque : bloquesDelitos){
            j=bloque[0];
            i=bloque[1];
            delitos=delitosForecast[j][i][idTurno];
            distancia=mapa.distanciaCoord(mapa.latLng[j][i][0], mapa.latLng[j][i][1], comisaria.getLatitud(), comisaria.getLongitud());
            costoAux=delitos/distancia;      
            //minizar la funcion 
            // maximizar la funcion (tesis)
            if ( beta<=costoAux  &&costoAux<=( beta + alpha*(tau-beta))){
                //System.out.println("Costo aux LCR " + costoAux);
                bloquesLCR.add(bloque);
            }
        }
        //System.out.println("tamaño " + nuevoPedidos.size());
        return bloquesLCR;
    } 
    public void actualizarCantPatrulla(){
        comisaria.setCantPatrulla( comisaria.getCantPatrulla()-1);
    }
    public void actualizarCantPatrullaSerenazgo(){
         comisaria.setCantPatrullaSerenazgo( comisaria.getCantPatrullaSerenazgo()-1);
    }
    public boolean quedaVehiculos(){
        if(comisaria.getCantPatrulla()>0) return true;
        else return false;
    }
    public boolean quedaVehiculosSerenazgo(){
        if(comisaria.getCantPatrullaSerenazgo()>0) return true;
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
        int mesHoy = cal.get(Calendar.MONTH)+1;
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
                int idTurno = d.getTurno().getIdTurno()-1;
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
            if(numPeriodo!=-1){
                delitos[distanciaH][distanciaV][numPeriodo][idTurno]=delitos[distanciaH][distanciaV][numPeriodo][idTurno]+1;
                System.out.println("periodo: " +numPeriodo + " Lat: " + d.getLatitud() + " Long: " + d.getLongitud() +" " + distanciaH--+ " " + distanciaV-- +"" );
                // aumento la cantidad de delitos en ese cuadrado
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
                    
                    if(delitosForecast[j][i][l]>0) System.out.println("Forecast " + i + " " + j+ "Turno " + l +" Mayor a cero");
                }
            }
        }
    }
}
