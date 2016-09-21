/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.pojo.Distritoxbloque;

/**
 *
 * @author Mariella
 */
public interface IDistritoxbloque {
    List<Distritoxbloque> getAll();
    int save(Distritoxbloque d);
    List<Distritoxbloque> queryByIdDistrito(int id);
}
