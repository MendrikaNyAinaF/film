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
import java.util.GregorianCalendar;

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

import app.apps.model.*;
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

    @Autowired
    SceneService sceneService;

    @Autowired
    ActorUnavailableService actorUnavailableService;

    @Autowired
    FilmsetUnavailableService filmsetUnavailableService;

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
            p.setDate_debut((Timestamp) row[2]);
            p.setDate_fin((Timestamp) row[3]);
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
     * public List<Filmset> getOpenFilmset(Integer idf,Timestamp t) throws Exception
     * {
     * SessionFactory sessionFactory = this.hibernate.getSessionFactory();
     * Session session = sessionFactory.openSession();
     * Criteria cr = session.createCriteria(Filmset.class);
     * cr.add(Restrictions.and(
     * Restrictions.
     * sqlRestriction("this_.id in (select filmset_id from scene where id not in (select scene_id from planning) and film_id = "
     * +idf+")"),
     * Restrictions.
     * sqlRestriction("this_.id in (select filmset_id from filmset_planning where weekday = date_part('isodow','"
     * +t.toString()+"'::timestamp) and timestart <= '"+t.toString()
     * +"'::time and timeend >= '"+t.toString()+"'::time")
     * )
     * );
     * cr.addOrder(Order.asc("id"));
     * List<Filmset> ls = cr.list();
     * session.close();
     * return ls;
     * }
     */
    public List<Holiday> getHoliday(Timestamp t) throws Exception {
        Date d = new Date(t.getTime());
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria cr = session.createCriteria(Holiday.class);
        cr.add(Restrictions.and(Restrictions.eq("date", d)));
        List<Holiday> ls = cr.list();
        session.close();
        return ls;
    }

    public boolean isWeekend(Timestamp t) throws Exception {
        int j;
        Calendar c = Calendar.getInstance();
        c.setTime(t);
        j = c.get(Calendar.DAY_OF_WEEK);
        if (j == Calendar.SATURDAY || j == Calendar.SUNDAY)
            return true;
        return false;
    }

    public List<Planning> proposerPlanning(Integer[] ls, Timestamp debut_tournage, Timestamp fin_tournage)
            throws Exception {
        Settings workhour = getWorkHour();
        double hour = workhour.getValue();
        double worked = 0;
        int onwork = 0;
        ArrayList<Planning> lp = new ArrayList<Planning>();
        Calendar cal = Calendar.getInstance();
        Timestamp shooting = new Timestamp(debut_tournage.getTime());
        cal.setTime(shooting);
        Calendar c_noon = Calendar.getInstance();
        Timestamp noon = null;
        Timestamp fourteen = null;
        Planning p = null;
        List<Filmset> lf = filmsetService.neededFilmsets(ls);
        List<Scene> lis = sceneService.getSceneIn(ls);
        Integer[] lstatus = new Integer[ls.length];
        int i = 0;
        for (Scene sce : lis) {
            lstatus[i] = sce.getStatus().getId();
            i++;
        }
        // Scene s = null;
        StatusPlanning sp = (StatusPlanning) hibernate.getById(StatusPlanning.class, 4);
        Time est;
        double h;
        double m;
        double se;
        double estWork;
        Timestamp start = null;
        Timestamp end = null;
        // System.out.println(ls.length);
        double tomillis;
        Scene s = null;
        while (shooting.before(fin_tournage)) {
            /*
             * System.out.println("Shooting : " + shooting.toString());
             * System.out.println("is week-end: " +(isWeekend(shooting)));
             * System.out.println("Holidays: "+(getHoliday(shooting).size()>0));
             */
            if (!isWeekend(shooting) && !(getHoliday(shooting).size() > 0)) {
                for (Filmset f : lf) {
                    if (filmsetService.isOpen(f, new Date(shooting.getTime())).size() > 0)
                        continue;
                    onwork = onwork + 1;
                    for (i = 0; i < ls.length; i++) {
                        s = (Scene) lis.get(i);
                        if (lstatus[i] > 3) {
                            System.out.println("=>  Scene deja pris");
                            continue;
                        }
                        System.out.println("=>  Acteur indisp.: "
                                + sceneService.getActorUnavailable(s, new Date(shooting.getTime())).size());
                        if (sceneService.getActorUnavailable(s, new Date(shooting.getTime())).size() > 0) {
                            System.out.println("=>  Acteur(s) indisponible");
                            continue;
                        }
                        System.out.println("=>      Plateau: " + f.getName());
                        System.out.println("=>      Plateau de scene: " + s.getFilmset().getName());
                        System.out.println("=>      Nombre de plateau: " + onwork);
                        if (!(f.getId().equals(s.getFilmset().getId()))) {
                            System.out.println("=>  Changement de plateau");
                            if( onwork >= 2 ){
                                cal.add(Calendar.DAY_OF_YEAR, 1);
                                cal.set(Calendar.HOUR_OF_DAY, 8);
                                cal.set(Calendar.MINUTE, 0);
                                cal.set(Calendar.SECOND, 0);
                                shooting.setTime(cal.getTimeInMillis());
                                worked = 0;
                                onwork = 0;
                            }
                            break;
                        }
                        est = s.getEstimated_time();
                        // System.out.println(est.toString());
                        h = (double) est.toLocalTime().getHour();
                        m = ((double) est.toLocalTime().getMinute()) / 60;
                        se = ((double) est.toLocalTime().getSecond()) / 3600;
                        estWork = h + m + se;
                        if (worked + estWork > 8) {
                            cal.add(Calendar.DAY_OF_YEAR, 1);
                            cal.set(Calendar.HOUR_OF_DAY, 8);
                            cal.set(Calendar.MINUTE, 0);
                            cal.set(Calendar.SECOND, 0);
                            shooting.setTime(cal.getTimeInMillis());
                            worked = 0;
                            onwork = 0;
                            break;
                        }
                        start = new Timestamp(shooting.getTime());
                        tomillis = estWork * 3600000;
                        end = new Timestamp(shooting.getTime() + (long) tomillis);
                        c_noon.setTime(start);
                        c_noon.set(Calendar.HOUR_OF_DAY, 13);
                        c_noon.set(Calendar.MINUTE, 0);
                        c_noon.set(Calendar.SECOND, 0);
                        noon = new Timestamp(c_noon.getTimeInMillis());
                        fourteen = new Timestamp(c_noon.getTimeInMillis() + (3600000));
                        if(end.compareTo(noon)>0 && start.compareTo(fourteen)<=0){
                            start.setTime(noon.getTime() + (3600000));
                            end.setTime(start.getTime() + (long) tomillis);
                        }
                        p = new Planning();
                        p.setScene(s);
                        p.setDate_debut(start);
                        p.setDate_fin(end);
                        lp.add(p);
                        System.out.println("=>    " + lp.size());
                        lstatus[i] = 4;
                        worked = worked + estWork;
                        shooting.setTime(end.getTime() + 1200000);
                    }
                }
            }
            cal.add(Calendar.DAY_OF_YEAR, 1);
            cal.set(Calendar.HOUR_OF_DAY, 8);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            shooting.setTime(cal.getTimeInMillis());
            worked = 0;
            onwork = 0;
        }
        return (List<Planning>) lp;
    }

    public List<DatePlanning> generateDatePlanning(List<Planning> plannings){
        DatePlanning jour_planning = null;
        List<DatePlanning> list_jour_planning = null;
        Filmset plateau = null;
        List<Filmset> list_plateau = null;
        Date tournage = null;
        List<Planning> list_planning = null;
        try{
            list_jour_planning = new ArrayList<DatePlanning>();
            list_plateau = new ArrayList<Filmset>();
            list_planning = new ArrayList<Planning>();
            jour_planning = new DatePlanning();
            for(Planning p : plannings){
                if(plateau == null){
                    plateau = p.getScene().getFilmset();
                }
                if(tournage == null){
                    tournage = new Date(p.getDate_debut().getTime());
                }
                if(plateau.getId()!=p.getScene().getFilmset().getId()){
                    plateau.setList_planning((List<Planning>) list_planning);
                    list_planning = new ArrayList<Planning>();
                    list_plateau.add(plateau);
                    plateau = p.getScene().getFilmset();
                }
                if(tournage.compareTo(new Date(p.getDate_debut().getTime()))!=0){
                    jour_planning.setJour_tournage(tournage);
                    jour_planning.setList_plateau(list_plateau);
                    list_jour_planning.add(jour_planning);
                    tournage = new Date(p.getDate_debut().getTime());
                }
                list_planning.add(p);
            }
            plateau.setList_planning((List<Planning>) list_planning);
            list_plateau.add(plateau);
            jour_planning.setJour_tournage(tournage);
            jour_planning.setList_plateau(list_plateau);
            list_jour_planning.add(jour_planning);
        }
        catch(Exception e){
            throw e;
        }
        return list_jour_planning;
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
    public int countFilmsetOnADay(Timestamp d) throws Exception{
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        Date t = new Date(d.getTime());
        String query = "SELECT count(distinct scene.filmset_id) FROM planning join scene on planning.scene_id=scene.id where planning.date_debut::date<='"+t.toString()+"' and planning.date_fin::date>='"+t.toString()+"'";
        SQLQuery sqlquery = session.createSQLQuery(query);
        Integer res = 0;
        res = ((Number) sqlquery.uniqueResult()).intValue();
        session.close();
        return res;
    }

    public void insertPlanning(List<Planning> lp) throws Exception {
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        Scene s = null;
        StatusPlanning sp = null;
        Filmset current = null;
        Date a_start = null;
        Date a_end = null;
        Date f_start = null;
        Date f_end = null;
        Filmset_unavailable fu = null;
        try {
            sp = hibernate.findById(StatusPlanning.class, 4);
            sessionFactory = this.hibernate.getSessionFactory();
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            for (Planning pa : lp) {
                if (isThereSuperposistion(pa.getDate_debut(), pa.getDate_fin())) {
                    throw new Exception("Planning invalid : superposition de jour de tournage");
                }
                session.save(pa);
                s = pa.getScene();
                s = sceneService.getById(s.getId());
                s.setStatus(sp);
                session.update(s);
                a_start = new Date(pa.getDate_debut().getTime());
                a_end = new Date(pa.getDate_fin().getTime());
                actorUnavailableService.insertUnavailableFromScene(s,a_start,a_end,"Pour tournage",session);
                if(current == null){
                    current = s.getFilmset();
                    f_start = new Date(pa.getDate_debut().getTime());
                    f_end = new Date(pa.getDate_fin().getTime());
                }
                else{
                    if(current.getId().equals(s.getFilmset().getId())){
                        f_end = new Date(pa.getDate_fin().getTime());
                    }
                    else{
                        fu = new Filmset_unavailable();
                        fu.setDate_debut(f_start);
                        fu.setDate_fin(f_end);
                        fu.setObservation("Tournage");
                        fu.setFilmset_id(current.getId());
                        filmsetUnavailableService.saveUnavailableFilmSet(fu,session);
                        current = s.getFilmset();
                        f_start = new Date(pa.getDate_debut().getTime());
                        f_end = new Date(pa.getDate_fin().getTime());
                    }
                }
            }
            fu = new Filmset_unavailable();
            fu.setDate_debut(f_start);
            fu.setDate_fin(f_end);
            fu.setObservation("Tournage");
            fu.setFilmset_id(current.getId());
            filmsetUnavailableService.saveUnavailableFilmSet(fu,session);

            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();
            ex.printStackTrace();
            throw ex;
        } finally {
            if (session != null)
                session.close();
        }
    }

    public List<Planning> findByActorId(Integer actorId) throws Exception {
        String query = "select * from planning where scene_id in(SELECT scene_id from v_scene_actor WHERE actor_id="
                + actorId + ")";
        System.out.println(query);
        return hibernate.findBySql(Planning.class, query, 0, 0);
    }
}
