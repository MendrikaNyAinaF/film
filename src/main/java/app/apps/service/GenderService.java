package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Actor;
import app.apps.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderService {
    @Autowired
    HibernateDAO hibernateDAO;

    public List<Gender> getAllGender(){
        return hibernateDAO.getAll1(new Gender());
    }
}
