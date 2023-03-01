package app.apps.service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
import app.apps.model.Scene;
import app.apps.model.Planning;
import app.apps.model.StatusPlanning;
import app.apps.model.Settings;
import app.apps.dao.HibernateDAO;

import org.springframework.beans.factory.annotation.Autowired;

public class PlanningService {

    @Autowired
    HibernateDAO hibernate;

    public HibernateDAO getHibernate(){
        return this.hibernate;
    }
    public void setHibernate(HibernateDAO a){
        this.hibernate=a;
    }

    public void create(Planning p)throws Exception{
        this.hibernate.add(p);
    }
    public Settings getWorkHour()throws Exception{
        Settings s = new Settings();
        s.setName("work hour");
        return (Settings) hibernate.findOneWhere(s,false,false);
    }
    public void globalPlan(Film f)throws Exception{
        SceneService ss = new SceneService();
        Calendar calendar = Calendar.getInstance();
        Settings workhour = getWorkHour();
        double hour = workhour.getValue();
        int team = f.getNbr_team();
        int simult = 0;
        calendar.setTimeInMillis(f.getStart_shooting().getTime());
        calendar.set(Calendar.HOUR_OF_DAY,8);
        Timestamp plan = new Timestamp(calendar.getTimeInMillis());
        List<Scene> ls = ss.findByFilm(f.getId());
        Planning p = null;
        int planned = 0;
        while(planned!=ls.size()){
            for(Scene s:ls){
                if(checkIfPlanFree(f,s,plan)){
                    p = new Planning();
                    p.setScene(s);
                    p.setStatus((StatusPlanning) hibernate.findById(StatusPlanning.class,1));
                    p.setDate(plan);
                    create(p);
                    Time est = s.getEstimated_time();
                    double h = est.toLocalTime().getHour();
                    double m = est.toLocalTime().getMinute()/60;
                    double se = est.toLocalTime().getSecond()/3600;
                    double temps = h+m+se;
                    if(temps>hour){
                        Calendar cal = Calendar.getInstance();
                        cal.setTimeInMillis(plan.getTime());
                        cal.add(Calendar.DAY_OF_YEAR,1);
                        cal.set(Calendar.HOUR_OF_DAY,8);
                        if(checkIfPlanFree(f,s,new Timestamp(cal.getTimeInMillis()))){
                            p.setDate(new Timestamp(cal.getTimeInMillis()));
                            create(p);
                        }
                    }
                    planned++;
                }
            }
            calendar.add(Calendar.DAY_OF_YEAR,1);
            plan = new Timestamp(calendar.getTimeInMillis());
        }
    }
    public List listPlanning(Integer filmid)throws Exception{
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery("SELECT * FROM planning WHERE scene_id IN (SELECT id FROM scene WHERE film_id = "+filmid+")");
        ArrayList<Planning> rep = new ArrayList(); 
        List<Object[]> lp = query.list();
        Planning p = null;
        for(Object[] row: lp){
            p = new Planning();
            p.setId(Integer.parseInt(row[0].toString()));
            p.setScene(hibernate.findById(Scene.class,Integer.parseInt(row[1].toString())));
            p.setStatus(hibernate.findById(StatusPlanning.class,Integer.parseInt(row[2].toString())));
            p.setDate((Timestamp) row[3]);
            rep.add(p);
        }
        session.close();
        return rep;
    }
    public void changeStatus(Integer idPlanning,Integer status)throws Exception{
        Planning p = (Planning) this.hibernate.findById(Planning.class,idPlanning);
        p.setStatus(hibernate.findById(StatusPlanning.class,status));
        hibernate.update(p);
    }
    public void changeStatus(Planning p,Integer status)throws Exception{
        p.setStatus(hibernate.findById(StatusPlanning.class,status));
        hibernate.update(p);
    }
    public boolean checkIfPlanFree(Film f,Scene toAdd,Timestamp t)throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(t.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Timestamp before = new Timestamp(calendar.getTimeInMillis());

        calendar.add(Calendar.DAY_OF_YEAR,1);

        Timestamp after = new Timestamp(calendar.getTimeInMillis());

        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Planning> ls = session.createCriteria(Planning.class)
            .add(Restrictions.and(
                Restrictions.ge("date",before),
                Restrictions.lt("date",after),
                Restrictions.sqlRestriction("scene_id IN (SELECT id FROM scene WHERE film_id = "+f.getId()+")")
            ))
            .list();
        session.close();
        if(ls.size()<=0){
            return true;
        }
        if(ls.size()>=f.getNbr_team()){
            return false;
        }
        SceneService ss = new SceneService();
        for(Planning p: ls){
            if(!(ss.needSameActor(toAdd,p.getScene())) && toAdd.getFilmset().getId()==p.getScene().getFilmset().getId()){
                return true;
            }
        }
        return true;
    }
}
