/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.daoImpl;

import java.util.List;
import model.dao.IComisaria;
import model.dao.IDistrito;
import model.pojo.Comisaria;
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
public class ComisariaImpl implements IComisaria {

    private static ComisariaImpl instance = null;

    protected ComisariaImpl() {

    }

    public static ComisariaImpl getInstance() {
        if (instance == null) {
            instance = new ComisariaImpl();
        }
        return instance;
    }

    @Override
    public List<Comisaria> getAll() {
        List<Comisaria> lst = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from Comisaria";
            Query query = session.createQuery(hql);
            lst = query.list();
            for (Comisaria com : lst) {
                Hibernate.initialize(com.getDistrito());
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
    public int save(Comisaria c) {

        Session session = null;
        Transaction tx=null;
        try {
            
            session = HibernateUtil.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.save(c);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }

        return c.getIdComisaria();
    }

    @Override
    public void update(Comisaria c) {

        Session session = null;
        Transaction tx=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.update(c);
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
    public Comisaria queryById(int id) {
        Comisaria c = null;
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
           
            c = (Comisaria) session.load(Comisaria.class, id);            
            Hibernate.initialize(c.getDistrito());
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }
        return c;
    }

    @Override
    public void delete(int id) {
        Comisaria c = null;
        Session session = null;
        Transaction tx=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            c = (Comisaria) session.load(Comisaria.class, id);
            tx=session.beginTransaction();
            session.delete(c);
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
