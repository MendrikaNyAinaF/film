package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Actor;
import app.apps.model.Filmset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Example;
import org.hibernate.query.Query;

@Service
public class FilmSetService {
    @Autowired
    HibernateDAO hibernateDAO;

    public List<Filmset> getAllFilmSet(){
        return hibernateDAO.getAll1(new Filmset());
    }
    public Filmset getFilmsetById(int id) throws Exception {
        return  hibernateDAO.getById(new Filmset(),id);
    }
    public boolean isOpen(Filmset f,Timestamp t) throws Exception{
        SessionFactory sessionFactory = this.hibernateDAO.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(Filmset.class);
        cr.add(Restrictions.and(
            Restrictions.sqlRestriction("this_.id in (select filmset_id from scene where id not in (select scene_id from planning) and film_id = "+f.getId()+")"),
            Restrictions.sqlRestriction("this_.id in (select filmset_id from filmset_planning where weekday = date_part('isodow','"+t.toString()+"'::timestamp) and timestart <= '"+t.toString()+"'::time and timeend >= '"+t.toString()+"'::time"),
            Restrictions.eq("id",f.getId())
            )
        );
        cr.addOrder(Order.asc("id"));
        List<Filmset> ls = cr.list();
        session.close();
        if(ls.size()>0) return true;
        return false;
    }
}
