/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.daoImpl;

import java.util.List;
import java.util.Set;
import model.dao.ITipodelito;
import model.pojo.Delito;
import model.pojo.Tipodelito;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author USER
 */
public class TipodelitoImpl implements ITipodelito {

    private static TipodelitoImpl instance = null;

    protected TipodelitoImpl() {

    }

    public static TipodelitoImpl getInstance() {
        if (instance == null) {
            instance = new TipodelitoImpl();
        }
        return instance;
    }

    @Override
    public List<Tipodelito> getAll() {
        List<Tipodelito> lst = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from Tipodelito";
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
    public int save(Tipodelito td) {

        Session session = null;
        Transaction tx=null;
        try {
            
            session = HibernateUtil.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.save(td);
            tx.commit();
        } catch (Exception e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }

        return td.getIdTipoDelito();
    }

    @Override
    public void update(Tipodelito td) {

        Session session = null;
        Transaction tx=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx=session.beginTransaction();
            session.update(td);
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
    public Tipodelito queryById(int id) {
        Tipodelito td = null;
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
           
            td = (Tipodelito) session.load(Tipodelito.class, id);            
            td.setDelitos(null);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!HibernateUtil.getSessionFactory().isClosed() || session != null) {
                session.close();
            }
        }
        return td;
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
