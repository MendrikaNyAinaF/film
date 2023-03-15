package app.apps.service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
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

import app.apps.model.Film;
import app.apps.model.Filmset;
import app.apps.model.Scene;
import app.apps.model.Planning;
import app.apps.model.StatusPlanning;
import app.apps.model.Settings;
import app.apps.dao.HibernateDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class PlanningService {

    @Autowired
    HibernateDAO hibernate;

    @Autowired
    FilmSetService filmsetService;

    public HibernateDAO getHibernate() {
        return this.hibernate;
    }

    public void setHibernate(HibernateDAO a) {
        this.hibernate = a;
    }

    public void create(Planning p) throws Exception {
        this.hibernate.add(p);
    }

    public Settings getWorkHour() throws Exception {
        Settings s = new Settings();
        s.setName("work hour");
        return (Settings) hibernate.findOneWhere(s, false, false);
    }

    /*
     * public void globalPlan(Film f) throws Exception {
     * SceneService ss = new SceneService();
     * Calendar calendar = Calendar.getInstance();
     * Settings workhour = getWorkHour();
     * double hour = workhour.getValue();
     * int team = f.getNbr_team();
     * int simult = 0;
     * calendar.setTimeInMillis(f.getStart_shooting().getTime());
     * calendar.set(Calendar.HOUR_OF_DAY, 8);
     * Timestamp plan = new Timestamp(calendar.getTimeInMillis());
     * List<Scene> ls = ss.findByFilm(f.getId());
     * Planning p = null;
     * int planned = 0;
     * while (planned != ls.size()) {
     * for (Scene s : ls) {
     * if (checkIfPlanFree(f, s, plan)) {
     * p = new Planning();
     * p.setScene(s);
     * p.setStatus((StatusPlanning) hibernate.findById(StatusPlanning.class, 1));
     * p.setDate(plan);
     * create(p);
     * Time est = s.getEstimated_time();
     * double h = est.toLocalTime().getHour();
     * double m = est.toLocalTime().getMinute() / 60;
     * double se = est.toLocalTime().getSecond() / 3600;
     * double temps = h + m + se;
     * if (temps > hour) {
     * Calendar cal = Calendar.getInstance();
     * cal.setTimeInMillis(plan.getTime());
     * cal.add(Calendar.DAY_OF_YEAR, 1);
     * cal.set(Calendar.HOUR_OF_DAY, 8);
     * if (checkIfPlanFree(f, s, new Timestamp(cal.getTimeInMillis()))) {
     * p.setDate(new Timestamp(cal.getTimeInMillis()));
     * create(p);
     * }
     * }
     * planned++;
     * }
     * }
     * calendar.add(Calendar.DAY_OF_YEAR, 1);
     * plan = new Timestamp(calendar.getTimeInMillis());
     * }
     * }
     */

    public List listPlanning(Integer filmid) throws Exception {
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(
                "SELECT * FROM planning WHERE scene_id IN (SELECT id FROM scene WHERE film_id = " + filmid + ")");
        ArrayList<Planning> rep = new ArrayList();
        List<Object[]> lp = query.list();
        Planning p = null;
        for (Object[] row : lp) {
            p = new Planning();
            p.setId(Integer.parseInt(row[0].toString()));
            p.setScene(hibernate.findById(Scene.class, Integer.parseInt(row[1].toString())));
            p.setDate_debut((Timestamp) row[3]);
            p.setDate_fin((Timestamp) row[4]);
            rep.add(p);
        }
        session.close();
        return rep;
    }

    public String listToJson(List l) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(l);
        return json;
    }

    public void changeStatus(Scene s, Integer status) throws Exception {
        s.setStatus(hibernate.findById(StatusPlanning.class, status));
        hibernate.update(s);
    }

    public boolean checkIfPlanFree(Film f, Scene toAdd, Timestamp t) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(t.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Timestamp before = new Timestamp(calendar.getTimeInMillis());

        calendar.add(Calendar.DAY_OF_YEAR, 1);

        Timestamp after = new Timestamp(calendar.getTimeInMillis());

        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Planning> ls = session.createCriteria(Planning.class)
                .add(Restrictions.and(
                        Restrictions.ge("date", before),
                        Restrictions.lt("date", after),
                        Restrictions.sqlRestriction(
                                "scene_id IN (SELECT id FROM scene WHERE film_id = " + f.getId() + ")")))
                .list();
        session.close();
        if (ls.size() <= 0) {
            return true;
        }
        if (ls.size() >= f.getNbr_team()) {
            return false;
        }
        SceneService ss = new SceneService();
        for (Planning p : ls) {
            if (!(ss.needSameActor(toAdd, p.getScene()))
                    && toAdd.getFilmset().getId() == p.getScene().getFilmset().getId()) {
                return true;
            }
        }
        return true;
    }
/* 
    public List<Filmset> getOpenFilmset(Integer idf,Timestamp t) throws Exception {
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(Filmset.class);
        cr.add(Restrictions.and(
            Restrictions.sqlRestriction("this_.id in (select filmset_id from scene where id not in (select scene_id from planning) and film_id = "+idf+")"),
            Restrictions.sqlRestriction("this_.id in (select filmset_id from filmset_planning where weekday = date_part('isodow','"+t.toString()+"'::timestamp) and timestart <= '"+t.toString()+"'::time and timeend >= '"+t.toString()+"'::time")
            )
        );
        cr.addOrder(Order.asc("id"));
        List<Filmset> ls = cr.list();
        session.close();
        return ls;
    }
*/
    public List<Planning> proposerPlanning(Integer[] ls, Timestamp debut_tournage) throws Exception {
        Settings workhour = getWorkHour();
        double hour = workhour.getValue();
        double worked = 0;
        ArrayList<Planning> lp = new ArrayList<Planning>();
        Calendar cal = Calendar.getInstance();
        Timestamp shooting = debut_tournage;
        cal.setTime(shooting);
        Planning p = null;
        List<Filmset> lf = filmsetService.neededFilmsets(ls);
        Scene s = null;
        StatusPlanning sp = (StatusPlanning) hibernate.getById(StatusPlanning.class,1);
        int i;
        Time est;
        double h;
        double m;
        double se;
        double estWork;
        Timestamp start = null;
        Timestamp end = null;
        System.out.println(ls.length);
        for(Filmset f : lf ){
            for(i=0;i<ls.length;i++){
                s = (Scene) hibernate.findById(Scene.class,ls[i]);
                //System.out.println(s);
                if(f.getId()!=s.getFilmset().getId()) continue;
                est = s.getEstimated_time();
                System.out.println(est.toString());
                h = (double) est.toLocalTime().getHour();
                m = ((double) est.toLocalTime().getMinute())/60;
                se = ((double) est.toLocalTime().getSecond())/3600;
                estWork = h+m+se;
                if(worked+estWork>8){
                    cal.add(Calendar.DAY_OF_YEAR,1);
                    cal.set(Calendar.HOUR_OF_DAY,8);
                    cal.set(Calendar.MINUTE,0);
                    cal.set(Calendar.SECOND,0);
                    shooting.setTime(cal.getTimeInMillis());
                }
                start = new Timestamp(shooting.getTime());
                end = new Timestamp(shooting.getTime()+(long) (estWork*3600000));
                p = new Planning();
                p.setScene(s);
                p.setDate_debut(start);
                p.setDate_fin(end);
                lp.add(p);
                shooting.setTime(end.getTime()+1200000);
            }
            cal.add(Calendar.DAY_OF_YEAR,1);
            cal.set(Calendar.HOUR_OF_DAY,8);
            cal.set(Calendar.MINUTE,0);
            cal.set(Calendar.SECOND,0);
            shooting.setTime(cal.getTimeInMillis());
        }
        return (List<Planning>) lp;
    }

    public boolean isThereSuperposistion(Timestamp d, Timestamp f) throws Exception {
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(Planning.class)
                .add(Restrictions.and(
                        Restrictions.ge("date_fin", d),
                        Restrictions.le("date_debut", f)));
        List<Scene> ls = cr.list();
        session.close();
        if (ls.size() > 0)
            return true;
        return false;
    }
    public void insertPlanning(List<Planning> lp)throws Exception{
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        try{
            sessionFactory = this.hibernate.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            for (Planning pa : lp) {
                if (isThereSuperposistion(pa.getDate_debut(), pa.getDate_fin())) {
                    throw new Exception("Planning invalid: superposition de jour de tournage");
                }
                session.save(pa);
            }
            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
            throw ex;
        }
        finally{
            if(session!=null) session.close();
        }
    }
}
