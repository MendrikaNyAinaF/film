package app.apps.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Author;

@Service
public class AuthorService {
     @Autowired
     HibernateDAO hibernateDAO;

     public List<Author> getAll() throws Exception {
          return hibernateDAO.findAll(Author.class, 0, 0, null, false);
     }
}
