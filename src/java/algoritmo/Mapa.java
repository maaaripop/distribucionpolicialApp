/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo;

/**
 *
 * @author Mariella
 */
public class Mapa {
    
    public double[] NE;
    public double[] SW;
    public double[] NW;
    public double[] SE;
    public int cantV;
    public int cantH;
    public double [][][] latLng;
    public double [][][][] distancias;
    
    public Mapa(double [] ne, double [] sw, double [] nw, double []se){
       
        NE=ne;
        SW=sw;
        NW=nw;
        SE=se;
        cantV=(int)distanciaCoord(nw[0],nw[1],sw[0],sw[1]);
        cantH=(int)distanciaCoord(nw[0],nw[1],ne[0],ne[1]);
        double [][][] latLng  = new double[cantH+1][cantV+1][2];
        double [][][][] distancias  = new double[cantH+1][cantV+1][cantH+1][cantV+1];
        
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
    
    public double[][][] latLng(double distancia){
        
        //NW[0],NW[1],1.0,(int)distanciaV,(int)distanciaH
        double latOrig= NW[0];
        double lngOrig= NW[1];
        // debo agregar los 10 ultimos meses (por ejemplo)
        double [][][] coordenadas  = new double[cantH+1][cantV+1][2];
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
        latLng=coordenadas;
        calcularDistancias();
        return coordenadas;
        
    }
    public void calcularDistancias(){
        
        for(int j=0;j<cantH;j++){
            for(int i=0;i<cantV;i++){
                double []coord1=latLng[j][i];
                for(int k=0;k<cantH;k++){
                    for(int l=0;l<cantV;l++){
                        double [] coord2= latLng[k][l];
                        distancias[j][i][k][l]=distanciaCoord(coord1[0],coord1[1],coord2[0],coord2[1]);
                    }
                }
            }
        }
    }

}
