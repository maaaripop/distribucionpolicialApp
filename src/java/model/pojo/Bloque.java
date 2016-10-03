package model.pojo;
// Generated 21-sep-2016 0:55:08 by Hibernate Tools 4.3.1



/**
 * Bloque generated by hbm2java
 */
public class Bloque  implements java.io.Serializable {


     private int idBloque;
     private Solucion solucion;
     private Double latitud;
     private Double longitud;

    public Bloque() {
    }

	
    public Bloque(int idBloque, Solucion solucion) {
        this.idBloque = idBloque;
        this.solucion = solucion;
    }
    public Bloque(int idBloque, Solucion solucion, Double latitud, Double longitud) {
       this.idBloque = idBloque;
       this.solucion = solucion;
       this.latitud = latitud;
       this.longitud = longitud;
    }
   
    public int getIdBloque() {
        return this.idBloque;
    }
    
    public void setIdBloque(int idBloque) {
        this.idBloque = idBloque;
    }
    public Solucion getSolucion() {
        return this.solucion;
    }
    
    public void setSolucion(Solucion solucion) {
        this.solucion = solucion;
    }
    public Double getLatitud() {
        return this.latitud;
    }
    
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }
    public Double getLongitud() {
        return this.longitud;
    }
    
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }




}

