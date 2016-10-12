/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Collections;
import java.util.Map;
import model.dao.IUsuario;
import model.daoImpl.UsuarioImpl;
import model.pojo.Turno;
import model.pojo.Usuario;
import model.rest.UserLoginRest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author USER
 */
@Controller
public class AndroidRestController {
    /***user interaction***/
    IUsuario usuarioService = UsuarioImpl.getInstance();
    @RequestMapping(value="/user/login", method = RequestMethod.POST)
    public @ResponseBody Usuario getTurnoJson(@RequestBody UserLoginRest userLogin){
        Usuario user= new Usuario();
        user.setCorreo(userLogin.getUserId());
        user.setContrasenha(userLogin.getPassword());
        user.setNombre("Daekef");
        return user;
    }
    
    
    /***end user interaction***/
}
