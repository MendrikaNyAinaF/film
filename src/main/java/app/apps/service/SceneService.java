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

    public HibernateDAO getHibernate(){
        return this.hibernate;
    }
    public void setHibernate(HibernateDAO a){
        this.hibernate=a;
    }

    public List<Scene> listScenes(Integer idfilm,String recherche, int page){
        if(recherche==null) recherche="";
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Scene> ls = session.createCriteria(Scene.class)
            .add(Restrictions.like("title","%"+recherche+"%"))
            .add(Restrictions.like("global_action","%"+recherche+"%"))
            .add(Restrictions.and(Restrictions.eq("film_id",idfilm)))
            .setFirstResult((page-1)*pagination)
            .setMaxResults(pagination)
            .list();
        session.close();
        return ls;
    }
    public Scene getById(Serializable id)throws Exception{
        return (Scene) this.hibernate.findById(Scene.class,id);
    }
    public void plannifier(Scene s, Timestamp date)throws Exception{
        PlanningService ps = new PlanningService();
        Film f = hibernate.findById(Film.class,s.getFilm_id());
        Planning p = new Planning();
        if(date==null){
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(Calendar.HOUR_OF_DAY,8);
            Timestamp t = new Timestamp(cal.getTimeInMillis());
            while(!(ps.checkIfPlanFree(f,s,t))){
                cal.add(Calendar.DAY_OF_YEAR,1);
                t = new Timestamp(cal.getTimeInMillis());
            }
            p.setScene(s);
            p.setStatus((StatusPlanning) hibernate.findById(StatusPlanning.class,1));
            p.setDate(t);
        }
        else{
            if(!(ps.checkIfPlanFree(f,s,date))) throw new Exception("Planning decoseillé, date deja chargée, acteur deja sollicité, ou changement de plateau necessaire");
            p.setScene(s);
            p.setStatus((StatusPlanning) hibernate.findById(StatusPlanning.class,1));
            p.setDate(date);
        }
        ps.create(p);
    }
    public void create(Scene s)throws Exception{
        this.hibernate.add(s);
    }
    public List<Scene> findByFilm(Integer idfilm){
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Scene> ls = session.createCriteria(Scene.class)
            .add(Restrictions.and(Restrictions.eq("film_id",idfilm)))
            .list();
        session.close();
        return ls;
    }
    public int countElements(Integer idfilm,String recherche){
        if(recherche==null) recherche="";
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Scene> ls = session.createCriteria(Scene.class)
            .add(Restrictions.like("title","%"+recherche+"%"))
            .add(Restrictions.like("global_action","%"+recherche+"%"))
            .add(Restrictions.and(Restrictions.eq("film_id",idfilm)))
            .list();
        session.close();
        return ls.size();
    }
    public int countElements(List<Scene> ls){
        return ls.size();
    }
    public List<Actor> getActor(Scene s)throws Exception{
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery("SELECT * from actor where id IN (SELECT actor_id from character WHERE id IN (SELECT character_id FROM dialogue WHERE scene_id="+s.getId()+"))");
        ArrayList<Actor> rep = new ArrayList(); 
        List<Object[]> lp = query.list();
        Actor a = null;
        for(Object[] row: lp){
            a = new Actor();
            a.setId(Integer.parseInt(row[0].toString()));
            a.setName(row[1].toString());
            a.setBirthdate((java.sql.Date) row[2]);
            a.setContact(row[3].toString());
            a.setGender(hibernate.findById(Gender.class,Integer.parseInt(row[4].toString())));
            rep.add(a);
        }
        session.close();
        return rep;
    }
    public List<Dialogue> getDialogues(Scene s)throws Exception{
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Dialogue> ls = session.createCriteria(Dialogue.class)
            .add(Restrictions.and(Restrictions.eq("scene_id",s.getId())))
            .list();
        session.close();
        return ls;
    }

    public List<StatusPlanning> getStatusPlanning()throws Exception{
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<StatusPlanning> ls = session.createCriteria(StatusPlanning.class).list();
        session.close();
        return ls;
    }
    public boolean needSameActor(Scene a,Scene b)throws Exception{
        List<Actor> la = getActor(a);
        List<Actor> lb = getActor(b);
        for(Actor au: la){
            for(Actor ab: lb){
                if(au.getId()==ab.getId()){
                    return true;
                }
            }
        }
        return false;
    }
    public List<Scene> getAllScene(){
        return  hibernate.getAll1(new Scene());
    }
}
