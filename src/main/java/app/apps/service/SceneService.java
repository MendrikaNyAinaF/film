package app.apps.service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Calendar;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.hibernate.SQLQuery;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Example;
import org.hibernate.query.Query;

import app.apps.model.Scene;
import app.apps.model.Planning;
import app.apps.model.StatusPlanning;
import app.apps.model.Film;
import app.apps.model.Gender;
import app.apps.model.Actor;
import app.apps.model.Dialogue;
import app.apps.model.StatusPlanning;
import app.apps.dao.HibernateDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneService {

    @Autowired
    HibernateDAO hibernate;

    private int pagination = 6;

    public HibernateDAO getHibernate() {
        return this.hibernate;
    }

    public void setHibernate(HibernateDAO a) {
        this.hibernate = a;
    }

    public List<Scene> listScenes(Integer idfilm, String recherche, Integer status, Integer[] idactors,
            int page) {
        if (recherche == null)
            recherche = "";
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(Scene.class)
                .add(Restrictions.or(
                        Restrictions.like("title", "%" + recherche + "%"),
                        Restrictions.like("global_action", "%" + recherche + "%")));
        if (status != null && status >= 0) {
            cr.add(Restrictions.and(Restrictions.eq("status",status)));
        }
        if (idactors.length > 0) {
            String in = "(";
            boolean all = false;
            for (int i = 0; i < idactors.length; i++) {
                if (idactors[i] >= 0) {
                    in = in + idactors[i].toString();
                    if (i < idactors.length - 1) {
                        in = in + ",";
                    }
                } else {
                    all = true;
                    break;
                }
            }
            in = in + ")";
            if (!all)
                cr.add(Restrictions.and(Restrictions.sqlRestriction(
                        "this_.id IN (select scene_id from dialogue where character_id in (select id from character where actor_id in "
                                + in + "))")));
        }
        cr.add(Restrictions.and(Restrictions.eq("film_id", idfilm)));
        cr.setFirstResult((page) * pagination).setMaxResults(pagination);
        List<Scene> ls = cr.list();
        session.close();
        return ls;
    }

    /* public Scene getById(Integer id) throws Exception {
        return (Scene) this.hibernate.findById(Scene.class, id);
    } */

    /*
     * public void plannifier(Scene s, Timestamp date)throws Exception{
     * PlanningService ps = new PlanningService();
     * Film f = hibernate.findById(Film.class,s.getFilm_id());
     * Planning p = new Planning();
     * if(date==null){
     * Calendar cal = Calendar.getInstance();
     * cal.setTimeInMillis(System.currentTimeMillis());
     * cal.set(Calendar.HOUR_OF_DAY,8);
     * Timestamp t = new Timestamp(cal.getTimeInMillis());
     * while(!(ps.checkIfPlanFree(f,s,t))){
     * cal.add(Calendar.DAY_OF_YEAR,1);
     * t = new Timestamp(cal.getTimeInMillis());
     * }
     * p.setScene(s);
     * p.setStatus((StatusPlanning) hibernate.findById(StatusPlanning.class,1));
     * p.setDate(t);
     * }
     * else{
     * if(!(ps.checkIfPlanFree(f,s,date))) throw new
     * Exception("Planning decoseillé, date deja chargée, acteur deja sollicité, ou changement de plateau necessaire"
     * );
     * p.setScene(s);
     * p.setStatus((StatusPlanning) hibernate.findById(StatusPlanning.class,1));
     * p.setDate(date);
     * }
     * ps.create(p);
     * }
     */
    public Scene create(Scene s) throws Exception {
        this.hibernate.add(s);
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        Scene last = (Scene) session.createCriteria(Scene.class)
                .addOrder(Order.desc("id"))
                .setMaxResults(1)
                .uniqueResult();
        session.close();
        return last;
    }

    public List<Scene> findByFilm(Integer idfilm) {
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Scene> ls = session.createCriteria(Scene.class)
                .add(Restrictions.and(Restrictions.eq("film_id", idfilm)))
                .list();
        session.close();
        return ls;
    }

    public Integer countElements(Integer idfilm, String recherche, Integer status, Integer[] idactors) {
        if (recherche == null)
            recherche = "";
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(Scene.class)
                .add(Restrictions.like("title", "%" + recherche + "%"))
                .add(Restrictions.like("global_action", "%" + recherche + "%"));
        if (status != null && status >= 0) {
            cr.add(Restrictions.and(Restrictions.eq("status",status)));
        }
        if (idactors.length > 0) {
            String in = "(";
            boolean all = false;
            for (int i = 0; i < idactors.length; i++) {
                if (idactors[i] >= 0) {
                    in = in + idactors[i].toString();
                    if (i < idactors.length - 1) {
                        in = in + ",";
                    }
                } else {
                    all = true;
                    break;
                }
            }
            in = in + ")";
            if (!all)
                cr.add(Restrictions.and(Restrictions.sqlRestriction(
                        "this_.id IN (select scene_id from dialogue where character_id in (select id from character where actor_id in "
                                + in + "))")));
        }
        cr.add(Restrictions.and(Restrictions.eq("film_id", idfilm)));
        cr.setProjection(Projections.rowCount());
        Integer res = ((Number) cr.uniqueResult()).intValue();
        session.close();
        return res;
    }

    public int countElements(List<Scene> ls) {
        return ls.size();
    }

    public List<Actor> getActor(Scene s) throws Exception {
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(
                "SELECT * from actor where id IN (SELECT actor_id from character WHERE id IN (SELECT character_id FROM dialogue WHERE scene_id="
                        + s.getId() + "))");
        ArrayList<Actor> rep = new ArrayList();
        List<Object[]> lp = query.list();
        Actor a = null;
        for (Object[] row : lp) {
            a = new Actor();
            a.setId(Integer.parseInt(row[0].toString()));
            a.setName(row[1].toString());
            a.setBirthdate((java.sql.Date) row[2]);
            a.setContact(row[3].toString());
            a.setGender(hibernate.findById(Gender.class, Integer.parseInt(row[4].toString())));
            rep.add(a);
        }
        session.close();
        return rep;
    }

    public List<Dialogue> getDialogues(Scene s) throws Exception {
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Dialogue> ls = session.createCriteria(Dialogue.class)
                .add(Restrictions.and(Restrictions.eq("scene_id", s.getId())))
                .list();
        session.close();
        return ls;
    }

    public List<StatusPlanning> getStatusPlanning() throws Exception {
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<StatusPlanning> ls = session.createCriteria(StatusPlanning.class)
            .add(Restrictions.and(Restrictions.gt("id",0)))
            .list();
        session.close();
        return ls;
    }

    public boolean needSameActor(Scene a, Scene b) throws Exception {
        List<Actor> la = getActor(a);
        List<Actor> lb = getActor(b);
        for (Actor au : la) {
            for (Actor ab : lb) {
                if (au.getId() == ab.getId()) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Scene> getAllScene() {
        return hibernate.getAll1(new Scene());
    }
    public List<Scene> getUnplannedScene(Integer idf){
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(Scene.class);
        cr.add(Restrictions.and(Restrictions.eq("status", 3)));
        cr.add(Restrictions.and(Restrictions.eq("film_id",idf)));
        cr.addOrder(Order.asc("id"));
        cr.addOrder(Order.asc("preferred_shooting_time"));
        List<Scene> ls = cr.list();
        session.close();
        return ls;
    }

    public List<Scene> getSceneByFilmSetId(Integer id){
        return hibernate.getByIdFilmSet(new Scene(),id);
    }
    public Scene getById(Integer id) throws Exception {
        return hibernate.getById(new Scene(),id);
    }

    public  void updateScene(Scene e){
        hibernate.update(e);
    }
}
