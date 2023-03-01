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
import app.apps.dao.HibernateDAO;

import org.springframework.beans.factory.annotation.Autowired;

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

    public List listScenes(Integer idfilm,String recherche, int page){
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
    public void plannifier(){}
    public void create(Scene s)throws Exception{
        this.hibernate.add(s);
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
        return ls.size();
    }
    public int countElements(List<Scene> ls){
        return ls.size();
    }
}
