package app.apps.service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

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
    public Settings getWorkHour(){
        Settings s = new Settings();
        s.setName("work hour");
        return (Settings) hibernate.findOneWhere(s,false,false);
    }
    public void globalPlan(Film f){
        Settings workhour = getWorkHour();
        double hour = workhour.getValue();
        int team = f.getTeam();
        Timestamp begin = f.getStart_shooting();
    }
    public List listPlanning(Integer filmid){
        SessionFactory sessionFactory = this.hibernate.getSessionFactory();
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery("SELECT * FROM planning WHERE scene_id IN (SELECT id FROM scene WHERE film_id = "+filmid+")");
        ArrayList<Planning> rep = new ArrayList(); 
        List<Object[]> lp = query.list();
        Planning p = null;
        for(Object[] row: lp){
            p = new Planning();
            p.setId(Integer.parseInt(row[0].toString()));
            p.setScene_id(Integer.parseInt(row[1].toString()));
            p.setStatus(Integer.parseInt(row[2].toString()));
            p.setDate((Timestamp) row[3]);
            rep.add(p);
        }
        session.close();
        return rep;
    }
    public void changeStatus(Integer idscene,Integer status){
        SceneService ss = new SceneService();
        Scene s = ss.getById(idscene);
        s.setStatus(status);
        hibernate.update(s);
    }
    public void changeStatus(Scene s,Integer status){
        s.setStatus(status);
        hibernate.update(s);
    }
}