package model.pojo;
// Generated 06-oct-2016 1:13:50 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Delito generated by hbm2java
 */
public class Delito  implements java.io.Serializable {


     private int idDelito;
     private Tipodelito tipodelito;
     private Turno turno;
     private String descripcion;
     private Double latitud;
     private Double longitud;
     private Date fecha;

    public Delito() {
    }

	
    public Delito(int idDelito, Tipodelito tipodelito, Turno turno) {
        this.idDelito = idDelito;
        this.tipodelito = tipodelito;
        this.turno = turno;
    }
    public Delito(int idDelito, Tipodelito tipodelito, Turno turno, String descripcion, Double latitud, Double longitud, Date fecha) {
       this.idDelito = idDelito;
       this.tipodelito = tipodelito;
       this.turno = turno;
       this.descripcion = descripcion;
       this.latitud = latitud;
       this.longitud = longitud;
       this.fecha = fecha;
    }
    
    public Delito(Tipodelito tipodelito, Turno turno, String descripcion, Double latitud, Double longitud, Date fecha) {
       
       this.tipodelito = tipodelito;
       this.turno = turno;
       this.descripcion = descripcion;
       this.latitud = latitud;
       this.longitud = longitud;
       this.fecha = fecha;
    }
   
   
    public int getIdDelito() {
        return this.idDelito;
    }
    
    public void setIdDelito(int idDelito) {
        this.idDelito = idDelito;
    }
    public Tipodelito getTipodelito() {
        return this.tipodelito;
    }
    
    public void setTipodelito(Tipodelito tipodelito) {
        this.tipodelito = tipodelito;
    }
    public Turno getTurno() {
        return this.turno;
    }
    
    public void setTurno(Turno turno) {
        this.turno = turno;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
    public Date getFecha() {
        return this.fecha;
    }
    
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }




}


