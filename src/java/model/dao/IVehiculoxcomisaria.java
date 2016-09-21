/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.pojo.Vehiculoxcomisaria;

/**
 *
 * @author Mariella
 */
public interface IVehiculoxcomisaria {
    List<Vehiculoxcomisaria> getAll();
    int save(Vehiculoxcomisaria d);
    List<Vehiculoxcomisaria> queryByIdComisaria(int id);
    
}
