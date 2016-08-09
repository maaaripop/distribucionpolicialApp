/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.daoImpl;

import java.util.List;
import model.dao.IDistrito;
import model.pojo.Distrito;
import model.pojo.Tipodelito;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Mariella
 */
public class DistritoImpl implements IDistrito {

    private static DistritoImpl instance = null;

    protected DistritoImpl() {

    }

    public static DistritoImpl getInstance() {
        if (instance == null) {
            instance = new DistritoImpl();
        }
        return instance;
    }

    @Override
    public List<Distrito> getAll() {
        List<Distrito> lst = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from Distrito";
            Query query = session.createQuery(hql);
            lst = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }

        return lst;
    }

    @Override
    public int save(Distrito d) {

        Session session = null;
        Transaction tx=null;
        try {
            
            session = HibernateUtil.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.save(d);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }

        return d.getIdDistrito();
    }

    @Override
    public void update(Distrito d) {

        Session session = null;
        Transaction tx=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.update(d);
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
    public Distrito queryById(int id) {
        Distrito d = null;
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
           
            d = (Distrito) session.load(Distrito.class, id);            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }
        return d;
    }

    @Override
    public void delete(int id) {
        Tipodelito td = null;
        Session session = null;
        Transaction tx=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            td = (Tipodelito) session.load(Tipodelito.class, id);
            tx=session.beginTransaction();
            session.delete(td);
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

    
}
