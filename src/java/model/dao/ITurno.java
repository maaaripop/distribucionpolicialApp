/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.pojo.Turno;

/**
 *
 * @author Mariella
 */
public interface ITurno {
    List<Turno> getAll();
    int save(Turno td);
    void update(Turno td);
    void delete(int id);
    Turno queryById(int id);
}
