package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {
    @Autowired
    HibernateDAO hibernateDAO;

    public void createFilm(Film film){
        hibernateDAO.save1(film);
    }

    public List<Film> getFilm(Integer offset,Integer limit){
        return hibernateDAO.getByPagination1(new Film(),offset,limit);
    }
    public List<Film> search(String keyWord,Integer offset,Integer limit) throws Exception {
        Film f=new Film();
        f.setDescription(keyWord);
        f.setTitle(keyWord);
        return hibernateDAO.findWhere(f,offset,limit,null,false,false,true);
    }
    public List<Film> allFilm(){
        return hibernateDAO.getAll1(new Film());
    }
    public int pageNumber(List<Film> t,Integer limit){
        int nbPage= t.size();
        nbPage= (int) Math.ceil((double)nbPage/limit);
        return  nbPage;
    }
    public int searchPageNumber(String keyWord,Integer offset,Integer limit) throws Exception {
        List<Film> filmList=this.search(keyWord,offset,limit);
        int nbPage= filmList.size();
        nbPage= (int) Math.ceil((double)nbPage/limit);
        return  nbPage;
    }
    public Film getFilmById(Integer idFilm) throws Exception {
        return hibernateDAO.getById(new Film(),idFilm);
    }

    public Integer maxFilm() throws Exception {
        Integer rep=0;
        Film f=new Film();
        List<Film> fi= hibernateDAO.findWhere(f,0,1,"id",false,false,false);
        rep=fi.get(0).getId();
        return rep;

    }
 
    
}
