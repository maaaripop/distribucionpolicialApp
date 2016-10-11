/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import model.pojo.Usuario;

/**
 *
 * @author Mariella
 */
public interface IUsuario {
    
    int save(Usuario u);
    void update(Usuario u);
    Usuario queryById(int id);
    Usuario login(String correo, String contrasenha);
}
