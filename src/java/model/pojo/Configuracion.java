package model.pojo;
// Generated 06-oct-2016 1:13:50 by Hibernate Tools 4.3.1



/**
 * Configuracion generated by hbm2java
 */
public class Configuracion  implements java.io.Serializable {


     private int idConfiguracion;
     private Turno turno;
     private Double coefRelaj;
     private Double factSmooth;
     private Integer cantMeses;

    public Configuracion() {
    }

	
    public Configuracion(int idConfiguracion, Turno turno) {
        this.idConfiguracion = idConfiguracion;
        this.turno = turno;
    }
    public Configuracion(int idConfiguracion, Turno turno, Double coefRelaj, Double factSmooth, Integer cantMeses) {
       this.idConfiguracion = idConfiguracion;
       this.turno = turno;
       this.coefRelaj = coefRelaj;
       this.factSmooth = factSmooth;
       this.cantMeses = cantMeses;
    }
   
    public int getIdConfiguracion() {
        return this.idConfiguracion;
    }
    
    public void setIdConfiguracion(int idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }
    public Turno getTurno() {
        return this.turno;
    }
    
    public void setTurno(Turno turno) {
        this.turno = turno;
    }
    public Double getCoefRelaj() {
        return this.coefRelaj;
    }
    
    public void setCoefRelaj(Double coefRelaj) {
        this.coefRelaj = coefRelaj;
    }
    public Double getFactSmooth() {
        return this.factSmooth;
    }
    
    public void setFactSmooth(Double factSmooth) {
        this.factSmooth = factSmooth;
    }
    public Integer getCantMeses() {
        return this.cantMeses;
    }
    
    public void setCantMeses(Integer cantMeses) {
        this.cantMeses = cantMeses;
    }




}


