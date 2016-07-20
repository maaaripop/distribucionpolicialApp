/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.pojo.Delito;

/**
 *
 * @author Mariella
 */
public interface IDelito {
    List<Delito> getAll();
    int save(Delito d);
    void update(Delito d);
    void delete(int id);
    Delito queryById(int id);
}
