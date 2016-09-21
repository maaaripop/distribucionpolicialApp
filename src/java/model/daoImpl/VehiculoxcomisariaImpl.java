/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.daoImpl;

import java.util.List;
import model.dao.IVehiculoxcomisaria;
import model.pojo.Comisaria;
import model.pojo.Vehiculoxcomisaria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

/**
 *
 * @author Mariella
 */
public class VehiculoxcomisariaImpl implements IVehiculoxcomisaria {

    private static VehiculoxcomisariaImpl instance = null;

    protected VehiculoxcomisariaImpl() {

    }

    public static VehiculoxcomisariaImpl getInstance() {
        if (instance == null) {
            instance = new VehiculoxcomisariaImpl();
        }
        return instance;
    }

    @Override
    public List<Vehiculoxcomisaria> getAll() {
        List<Vehiculoxcomisaria> lst = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from Vehiculoxcomisaria";
            Query query = session.createQuery(hql);
            lst = query.list();
            for (Vehiculoxcomisaria del : lst) {
                Hibernate.initialize(del.getVehiculo());
                Hibernate.initialize(del.getComisaria());
                Hibernate.initialize(del.getVehiculo().getTipovehiculo());
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
    public int save(Vehiculoxcomisaria d) {
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

        return d.getIdvehiculoXcomisaria();
    }
    
    public List<Vehiculoxcomisaria> queryByIdComisaria(int id) {
        List<Vehiculoxcomisaria> lst = null;
        Session session = null;
        
        try {
            session = HibernateUtil.getSessionFactory().openSession();
           
            Query query = session.createQuery("from Vehiculoxcomisaria where idComisaria = :code ");
            query.setParameter("code", id); 
            lst = query.list();            
            for (Vehiculoxcomisaria del : lst) {
                Hibernate.initialize(del.getVehiculo());
                Hibernate.initialize(del.getComisaria());
                Hibernate.initialize(del.getVehiculo().getTipovehiculo());
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
