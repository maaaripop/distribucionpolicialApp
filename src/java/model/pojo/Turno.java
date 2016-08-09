package model.pojo;
// Generated 01-ago-2016 22:20:49 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Turno generated by hbm2java
 */
public class Turno  implements java.io.Serializable {


     private int idTurno;
     private String horaInicio;
     private String horaFin;
     private Set delitos = new HashSet(0);

    public Turno() {
    }

	
    public Turno(int idTurno) {
        this.idTurno = idTurno;
    }
    public Turno(int idTurno, String horaInicio, String horaFin, Set delitos) {
       this.idTurno = idTurno;
       this.horaInicio = horaInicio;
       this.horaFin = horaFin;
       this.delitos = delitos;
    }
   
    public int getIdTurno() {
        return this.idTurno;
    }
    
    public void setIdTurno(int idTurno) {
        this.idTurno = idTurno;
    }
    public String getHoraInicio() {
        return this.horaInicio;
    }
    
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public String getHoraFin() {
        return this.horaFin;
    }
    
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    public Set getDelitos() {
        return this.delitos;
    }
    
    public void setDelitos(Set delitos) {
        this.delitos = delitos;
    }




}


