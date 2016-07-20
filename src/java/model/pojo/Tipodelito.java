package model.pojo;
// Generated 18/07/2016 11:48:08 PM by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Tipodelito generated by hbm2java
 */
public class Tipodelito  implements java.io.Serializable {


     private int idTipoDelito;
     private String nombre;
     private Set delitos = new HashSet(0);

    public Tipodelito() {
    }

	
    public Tipodelito(int idTipoDelito) {
        this.idTipoDelito = idTipoDelito;
    }
    public Tipodelito(int idTipoDelito, String nombre, Set delitos) {
       this.idTipoDelito = idTipoDelito;
       this.nombre = nombre;
       this.delitos = delitos;
    }
   
    public int getIdTipoDelito() {
        return this.idTipoDelito;
    }
    
    public void setIdTipoDelito(int idTipoDelito) {
        this.idTipoDelito = idTipoDelito;
    }
    public String getNombre() {
        return this.nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Set getDelitos() {
        return this.delitos;
    }
    
    public void setDelitos(Set delitos) {
        this.delitos = delitos;
    }




}


