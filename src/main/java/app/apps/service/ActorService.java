package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {
    @Autowired
    HibernateDAO hibernateDAO;

    public List<Actor> getAllActor() {
        return hibernateDAO.getAll1(new Actor());
    }

    public Actor getActorById(Integer id) throws Exception {
        return hibernateDAO.getById(new Actor(), id);
    }
}
