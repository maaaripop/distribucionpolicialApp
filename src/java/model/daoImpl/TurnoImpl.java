/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.daoImpl;

import java.util.List;
import model.dao.ITurno;
import model.pojo.Tipodelito;
import model.pojo.Turno;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Mariella
 */
public class TurnoImpl implements ITurno {
    private static TurnoImpl instance = null;

    protected TurnoImpl() {

    }

    public static TurnoImpl getInstance() {
        if (instance == null) {
            instance = new TurnoImpl();
        }
        return instance;
    }

    @Override
    public List<Turno> getAll() {
        List<Turno> lst = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from Turno";
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
    public int save(Turno td) {

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

        return td.getIdTurno();
    }

    @Override
    public void update(Turno td) {

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
    public Turno queryById(int id) {
        Turno td = null;
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
           
            td = (Turno) session.load(Turno.class, id);            
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
        Turno td = null;
        Session session = null;
        Transaction tx=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            td = (Turno) session.load(Turno.class, id);
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
