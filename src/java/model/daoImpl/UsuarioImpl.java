/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.daoImpl;

import java.util.List;
import model.dao.IComisaria;
import model.dao.IUsuario;
import model.pojo.Comisaria;
import model.pojo.Usuario;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Mariella
 */
public class UsuarioImpl implements IUsuario {

    private static UsuarioImpl instance = null;

    protected UsuarioImpl() {

    }

    public static UsuarioImpl getInstance() {
        if (instance == null) {
            instance = new UsuarioImpl();
        }
        return instance;
    }

   @Override
    public int save(Usuario u) {

        Session session = null;
        Transaction tx=null;
        try {
            
            session = HibernateUtil.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.save(u);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }

        return u.getIdUsuario();
    }

    @Override
    public void update(Usuario u) {

        Session session = null;
        Transaction tx=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.update(u);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }
    }

    @Override
    public Usuario queryById(int id) {
        Usuario u = null;
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
           
            u = (Usuario) session.load(Comisaria.class, id);            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }
        return u;
    }
    
    public Usuario login(String correo, String contrasenha) {
        Usuario u = null;
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("from Usuario where correo = :correo and contrasenha = :contrasenha ");
            query.setParameter("correo", correo); 
            query.setParameter("contrasenha", contrasenha); 
            if(query.list().size()!=0){
                 u=(Usuario)query.list().get(0);
            }
           
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }
        return u;
    }

   
    
}
