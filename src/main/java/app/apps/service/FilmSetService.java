package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Actor;
import app.apps.model.Filmset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmSetService {
    @Autowired
    HibernateDAO hibernateDAO;

    public List<Filmset> getAllFilmSet(){
        return hibernateDAO.getAll1(new Filmset());
    }
    public Filmset getFilmsetById(int id) throws Exception {
        return  hibernateDAO.getById(new Filmset(),id);
    }
}
