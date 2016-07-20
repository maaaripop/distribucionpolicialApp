/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.pojo.Tipodelito;

/**
 *
 * @author USER
 */
public interface ITipodelito {
    List<Tipodelito> getAll();
    int save(Tipodelito td);
    void update(Tipodelito td);
    void delete(int id);
    Tipodelito queryById(int id);
}
