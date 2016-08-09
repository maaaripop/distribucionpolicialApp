/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.pojo.Comisaria;

/**
 *
 * @author Mariella
 */
public interface IComisaria {
    List<Comisaria> getAll();
    int save(Comisaria c);
    void update(Comisaria c);
    void delete(int id);
    Comisaria queryById(int id);
}
