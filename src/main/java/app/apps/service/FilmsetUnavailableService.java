package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Filmset_unavailable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmsetUnavailableService {
    @Autowired
    HibernateDAO hibernateDAO;

    public void saveUnavailableFilmSet(Filmset_unavailable filmset_unavailable){
        hibernateDAO.save1(filmset_unavailable);
    }
    public List<Filmset_unavailable> getByFilmsetId(Integer filmsetId){
        return hibernateDAO.getByIdFilmSet(new Filmset_unavailable(),filmsetId);
    }
}
