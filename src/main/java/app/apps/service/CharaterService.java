package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharaterService {
    @Autowired
    HibernateDAO hibernateDAO;

    public List<Character> getCharacterByFilm(int idFilm) {
        Character c = new Character();
        return hibernateDAO.getByIdFilm(c, idFilm);
    }

    public void create(Character character) throws Exception {
        hibernateDAO.add(character);
    }
}
