/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmo;

import java.util.ArrayList;
import model.dao.IDelito;
import model.daoImpl.DelitoImpl;
import model.pojo.Delito;

/**
 *
 * @author Mariella
 */
public class Distribucion {
    IDelito delitoService = DelitoImpl.getInstance();
    
    public Distribucion(){
        ArrayList<Delito> delitolst=(ArrayList<Delito>)delitoService.getAll();
    
    }
}
