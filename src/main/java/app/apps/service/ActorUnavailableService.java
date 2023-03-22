package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Actor_unavailable;
import app.apps.model.Actor;
import app.apps.model.Scene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Session;

import java.util.List;
import java.sql.Date;

@Service
public class ActorUnavailableService {
    @Autowired
    HibernateDAO hibernateDAO;

    @Autowired
    SceneService sceneService;

    public void insertUnavailable(Actor_unavailable actor_unavailable) {
        hibernateDAO.save1(actor_unavailable);
    }

    public void insertUnavailableFromScene(Scene s,Date start,Date end,String comment,Session session)throws Exception{
        List<Actor> la = null;
        Actor_unavailable au = null;
        try{
            la= sceneService.getActor(s);
            for(Actor a : la){
                au = new Actor_unavailable();
                au.setDate_debut(start);
                au.setDate_fin(end);
                au.setObservation(comment);
                au.setActor_id(a.getId());
                session.save(au);
            }
        }
        catch(Exception ex){
            throw ex;
        }
    }

    public List<Actor_unavailable> dateUnavailableActor(Integer idActor) throws Exception {
        // return hibernateDAO.getByIdActor(new Actor_unavailable(),idActor);
        Actor_unavailable f = new Actor_unavailable();
        f.setActor_id(idActor);
        return hibernateDAO.findWhere(f, 0, 0, null, true, true, false);
    }
}
