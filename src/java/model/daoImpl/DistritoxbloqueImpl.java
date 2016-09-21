/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.daoImpl;

import java.util.List;
import model.dao.IDistritoxbloque;
import model.pojo.Distritoxbloque;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Mariella
 */
public class DistritoxbloqueImpl implements IDistritoxbloque {

    private static DistritoxbloqueImpl instance = null;

    protected DistritoxbloqueImpl() {

    }

    public static DistritoxbloqueImpl getInstance() {
        if (instance == null) {
            instance = new DistritoxbloqueImpl();
        }
        return instance;
    }

    @Override
    public List<Distritoxbloque> getAll() {
        List<Distritoxbloque> lst = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from Distritoxbloque";
            Query query = session.createQuery(hql);
            lst = query.list();
            for (Distritoxbloque del : lst) {
                Hibernate.initialize(del.getDistrito());
            }
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
    public int save(Distritoxbloque d) {
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

        return d.getIdDistritoXbloque();
    }
    public List<Distritoxbloque> queryByIdDistrito(int id) {
        List<Distritoxbloque> lst = null;
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
           
            Query query = session.createQuery("from Distritoxbloque where idDistrito = :code ");
            query.setParameter("code", id); 
            lst = query.list();            
            for (Distritoxbloque del : lst) {
                Hibernate.initialize(del.getDistrito());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }
        return lst;
    }
    
}
