package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Actor;
import app.apps.model.Filmset;
import app.apps.model.Filmset_unavailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;

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
    public List<Filmset_unavailable> isOpen(Filmset f,Date t) throws Exception{
        SessionFactory sessionFactory = this.hibernateDAO.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(Filmset_unavailable.class);
        cr.add(Restrictions.and(
            /* Restrictions.sqlRestriction("this_.id in (select filmset_id from scene where id not in (select scene_id from planning) and film_id = "+f.getId()+")"), */
            Restrictions.le("date_debut",t),
            Restrictions.ge("date_fin",t),
            Restrictions.eq("filmset_id",f.getId())
            )
        );
        List<Filmset_unavailable> ls = cr.list();
        session.close();
        return ls;
    }
    public List<Filmset> neededFilmsets(Integer[] idscenes)throws Exception{
        SessionFactory sessionFactory = this.hibernateDAO.getSessionFactory();
        Session session = sessionFactory.openSession();
        String in = "(";
        boolean all = false;
        for (int i = 0; i < idscenes.length; i++) {
            if (idscenes[i] >= 0) {
                in = in + idscenes[i].toString();
                if (i < idscenes.length - 1) {
                    in = in + ",";
                }
            } else {
                all = true;
                break;
            }
        }
        in = in + ")";
        Criteria cr = session.createCriteria(Filmset.class);
        cr.add(Restrictions.and(Restrictions.sqlRestriction("this_.id IN (select filmset_id from scene where id in "+ in + ")")));
        cr.addOrder(Order.asc("id"));
        List<Filmset> lf = cr.list();
        return lf;
    }
}
