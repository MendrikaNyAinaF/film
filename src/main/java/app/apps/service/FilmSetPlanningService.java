package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Filmset_planning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmSetPlanningService {
    @Autowired
    HibernateDAO hibernateDAO;

    public List<Filmset_planning> getByFilmsetId(Integer id){
        return hibernateDAO.getByIdFilmSet(new Filmset_planning(),id);
    }
}
