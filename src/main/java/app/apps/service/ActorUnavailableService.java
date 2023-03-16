package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Actor_unavailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorUnavailableService {
    @Autowired
    HibernateDAO hibernateDAO;

    public void insertUnavailable(Actor_unavailable actor_unavailable){
        hibernateDAO.save1(actor_unavailable);
    }
    public List<Actor_unavailable> dateUnavailableActor(Integer idActor){
        return hibernateDAO.getByIdActor(new Actor_unavailable(),idActor);
    }
}
