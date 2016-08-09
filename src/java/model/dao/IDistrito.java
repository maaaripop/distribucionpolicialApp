/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.pojo.Distrito;

/**
 *
 * @author Mariella
 */
public interface IDistrito {
    List<Distrito> getAll();
    int save(Distrito d);
    void update(Distrito d);
    void delete(int id);
    Distrito queryById(int id);
}
