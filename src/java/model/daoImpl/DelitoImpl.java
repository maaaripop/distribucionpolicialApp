/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.daoImpl;
import java.util.List;
import java.util.Set;
import model.dao.IDelito;
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
 * @author Mariella
 */
public class DelitoImpl implements IDelito{
    
    private static DelitoImpl instance = null;

    protected DelitoImpl() {

    }

    public static DelitoImpl getInstance() {
        if (instance == null) {
            instance = new DelitoImpl();
        }
        return instance;
    }

    @Override
    public List<Delito> getAll() {
        List<Delito> lst = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from Delito";
            Query query = session.createQuery(hql);
            lst = query.list();
            for (Delito del : lst) {
                Hibernate.initialize(del.getTurno());
                Hibernate.initialize(del.getTipodelito());
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

    public List<Delito> getAllSurquillo() {
        List<Delito> lst = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from Delito where idDelito in (1,9,10,11,13,14,15,16,17,19)";
            Query query = session.createQuery(hql);
            lst = query.list();
                       
            for (Delito del : lst) {
                Hibernate.initialize(del.getTurno());
                Hibernate.initialize(del.getTipodelito());
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
    public int save(Delito td) {

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

        return td.getIdDelito();
    }

    @Override
    public void update(Delito td) {

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
    public Delito queryById(int id) {
        Delito td = null;
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
           
            td = (Delito) session.load(Delito.class, id);            
            Hibernate.initialize(td.getTurno());
            Hibernate.initialize(td.getTipodelito());
            
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
        Delito td = null;
        Session session = null;
        Transaction tx=null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            td = (Delito) session.load(Delito.class, id);
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
