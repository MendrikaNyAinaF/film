package app.apps.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import app.apps.model.*;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;

import app.apps.service.Utilitaire;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.query.Query;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.query.Query;

//@Component("hibernateImp")
public class HibernateDAO implements InterfaceDAO {
    private SessionFactory sessionFactory;

    public HibernateDAO() {
        // this.sessionFactory = Utilities.getCurrentSessionFromConfig();
    }

    public void setSessionFactory(SessionFactory se) {
        this.sessionFactory = se;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Override
    public void add(Object o) throws Exception {
        Transaction trans = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            trans = session.beginTransaction();
            session.save(o);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void update(Object o) {
        Transaction trans = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            trans = session.beginTransaction();
            session.update(o);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Object o) {
        Transaction trans = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            trans = session.beginTransaction();
            session.delete(o);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public <T> T findById(Class<T> o, Serializable id) throws Exception {
        Session session = sessionFactory.openSession();
        T entity = (T) session.get(o, id);
        session.close();
        return entity;
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public <T> T findOneBySql(Class<T> o, String sql) throws Exception {
        Session session = sessionFactory.openSession();
        Query query = setQuery(session, o, sql, 0, 0, true);
        T results = (T) query.getSingleResult();
        session.close();
        return results;
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public <T> T findOneWhere(T entity, boolean and, boolean like) throws Exception {
        Session session = sessionFactory.openSession();
        Query query = setQuery(session, entity, 0, 0, null, false, true, and, like);
        T results = (T) query.getSingleResult();
        session.close();
        return results;
    }

    @Deprecated
    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> findBySql(Class<T> o, String sql, int offset, int size)
            throws Exception {
        Session session = sessionFactory.openSession();
        Query query = setQuery(session, o, sql, offset, size, false);
        List<T> results = query.getResultList();
        session.close();
        return results;
    }

    @Override
    public <T> List<T> findAll(Class<T> classe, int offset, int size, String order, boolean asc) throws Exception {
        Session session = sessionFactory.openSession();
        Query query = setQuery(session, classe, offset, size, order, asc, false);
        List<T> results = query.getResultList();
        session.close();
        return results;
    }

    @SuppressWarnings("unchecked")

    @Override
    public <T> List<T> findWhere(T entity, int offset, int size, String order, boolean asc, boolean and, boolean like)
            throws Exception {
        Session session = sessionFactory.openSession();
        Query query = setQuery(session, entity, offset, size, order, asc, false, and, like);
        List<T> results = query.getResultList();
        session.close();
        return results;
    }

    private <T> Query setQuery(Session session, Class<T> classe, int offset, int size, String order, boolean orderASC,
            boolean oneResult) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(classe);
        Root<T> root = criteriaQuery.from(classe);
        Query<T> query = session.createQuery(criteriaQuery);
        if (oneResult) {
            return query;
        }
        if (size > 0) {
            query.setFirstResult(offset)
                    .setMaxResults(size);
        }
        return query;
    }

    private <T> Query setQuery(Session session, T entity, int offset, int size, String order, boolean orderASC,
            boolean oneResult, boolean and, boolean like) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery((Class<T>) entity.getClass());
        Root<T> root = criteriaQuery.from((Class<T>) entity.getClass());

        List<Predicate> predicates = new ArrayList<Predicate>();
        Path<String> namePath = null;
        for (Field field : Utilitaire.getAllFields(entity.getClass())) {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                if (value != null) {
                    if (value instanceof String) {
                        namePath = root.get(field.getName());
                        if (like) {
                            predicates.add(
                                    builder.like(builder.lower(namePath), "%" + ((String) value).toLowerCase() + "%"));
                        } else {
                            predicates.add(builder.equal(root.get(field.getName()), value));
                        }
                    } else {
                        predicates.add(builder.equal(root.get(field.getName()), value));
                    }
                }
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (predicates.size() != 0) {
            if (and) {
                criteriaQuery.where((predicates.toArray(new Predicate[predicates.size()])));
            } else {
                criteriaQuery.where(builder.or(predicates.toArray(new Predicate[predicates.size()])));
            }
        }
        if (order != null && !order.isEmpty()) {
            if (orderASC) {
                criteriaQuery.orderBy(builder.asc(root.get(order)));
            } else {
                criteriaQuery.orderBy(builder.desc(root.get(order)));
            }
        }
        Query<T> query = session.createQuery(criteriaQuery);
        if (oneResult) {
            return query;
        }
        if (size > 0) {
            query.setFirstResult(offset)
                    .setMaxResults(size);
        }
        return query;
    }

    private <T> Query setQuery(Session session, Class<T> classe, String sql, int offset, int size,
            boolean oneResult) {
        Query<T> query = session.createNativeQuery(sql, classe);
        if (oneResult) {
            return query;
        }
        query.setFirstResult(offset);
        query.setMaxResults(size);
        return query;
    }

    public void save1(Object o) {
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(o);
        tx.commit();
        System.out.println("saved");
        session.close();
    }

    public <T> List<T> getAll1(Object o) {
        Session session = this.sessionFactory.openSession();
        List<T> list = session.createQuery("from " + o.getClass().getName()).list();
        session.close();
        return list;
    }

    public <T> List<T> getByIdFilm(Object o, Integer idFilm) {
        Session session = this.sessionFactory.openSession();
        List<T> result = null;
        try {
            Criteria criteria = session.createCriteria(o.getClass());
            criteria.add(Restrictions.eq("film_id", idFilm));
            result = criteria
                    .list();

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return result;
    }

    public <T> List<T> getByIdScene(Object o, Integer idScene) {
        Session session = this.sessionFactory.openSession();
        List<T> result = null;
        try {
            Criteria criteria = session.createCriteria(o.getClass());
            criteria.add(Restrictions.eq("scene_id", idScene));
            result = criteria
                    .list();

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return result;
    }

    public <T> List<T> getByPagination1(Object o, Integer offset, Integer limit) {
        Session session = this.sessionFactory.openSession();
        List<T> result = null;
        try {
            result = session.createCriteria(o.getClass())
                    .setFirstResult(offset) // offset
                    .setMaxResults(limit) // limit
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public <T> T getById(Object o, Integer id) throws Exception {
        Session session = null;
        Object p = null;
        try {
            session = this.sessionFactory.openSession();
            p = session.load(o.getClass(), id);
            System.out.println("Loaded successfully, details=" + p);
        } catch (Exception e) {

        } finally {
            session.close();
        }
        return (T) p;
    }
    public <T> List<T> getByIdFilmSet(Object o, Integer idFilmSet){
        Session session = this.sessionFactory.openSession();
        List<T> result = null;
        Filmset f=new Filmset();
        f.setId(idFilmSet);
        try {
            Criteria criteria = session.createCriteria(o.getClass());
            criteria.add(Restrictions.eq("filmset_id", f));
            result = criteria
                    .list();

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return result;
    }
    public <T> List<T> getByIdActor(Object o, Integer idActor){
        Session session = this.sessionFactory.openSession();
        List<T> result = null;

        Actor actor=new Actor();
        actor.setId(idActor);
        try {
            Criteria criteria = session.createCriteria(o.getClass());
            criteria.add(Restrictions.eq("actor_id", actor));
            result = criteria
                    .list();

        } catch (Exception e) {

        } finally {
            session.close();
        }
        return result;
    }


}
