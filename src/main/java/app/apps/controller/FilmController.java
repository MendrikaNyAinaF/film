package app.apps.controller;

import app.apps.model.*;
import app.apps.model.Character;
import app.apps.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class FilmController {
    @Autowired
    FilmService filmService;

    @Autowired
    ActorService actorService;

    @Autowired
    CharaterService charaterService;

    @Autowired
    GenderService genderService;

    @Autowired
    PlanningService planningService;

    @GetMapping(value = "/films/{page}")
    public String getAllFilm(@PathVariable("page") Integer page, HttpServletRequest request) throws Exception {
        Integer limit = 3;

        Integer offset = page * limit;
        List<Film> filmList = new ArrayList<>();
        if (request.getSession().getAttribute("film_motcle") != null) {
            System.out.println("heyheyhey");
            filmList = filmService.search((String) request.getSession().getAttribute("film_motcle"), offset, limit);
        } else {
            filmList = filmService.getFilm(offset, limit);
        }

        int nbPage = filmList.size();
        // nbPage= (int) Math.ceil((double)nbPage/limit);
        Boolean endpage = false;
        if (nbPage == page) {
            endpage = true;
        }
        request.setAttribute("liste_film", filmList);
        request.setAttribute("endPage", endpage);
        request.setAttribute("page", page);
        return "liste_film";
    }

    @PostMapping(value = "/search_film")
    public String searchFilm(HttpServletRequest request, Model m) throws Exception {
        request.getSession().setAttribute("film_motcle", request.getParameter("motcle"));
        return getAllFilm(0, request);
    }

    @GetMapping(value = "/film/{id}/scenes/{page}")
    public String getSceneByFilmId(@PathVariable("id") Integer filmId, @PathVariable("page") Integer page, Model m,
            HttpServletRequest request) {
        Integer limit = 6;
        Integer offset = page * limit;

        return "";
    }

    @PostMapping(value = "/search_scene")
    public String searchScene(HttpServletRequest request, Model m) throws Exception {
        request.getSession().setAttribute("search_scene", request.getParameter(""));
        Film f = (Film) request.getSession().getAttribute("film_id");
        return getSceneByFilmId(f.getId(), 1, m, request);

    }

    @GetMapping(value = "film/{id}/planning")
    public String getPlanningByIdFilm(@PathVariable("id") Integer filmId, Model m, HttpServletRequest request)
            throws Exception {
        Gson gson = new Gson();
        request.setAttribute("liste_planning", gson.toJson(planningService.listPlanning(filmId)));
        return "planning";
    }

    @GetMapping(value = "/film/create")
    public String getFormFilm(HttpServletRequest request) {
        List<Actor> actorList = actorService.getAllActor();
        List<Gender> genderList = genderService.getAllGender();
        request.setAttribute("actor", actorList);
        request.setAttribute("gender", genderList);

        // Conversion en json
        Gson gson = new Gson();
        String jsonActor = gson.toJson(actorList);
        String jsonGender = gson.toJson(genderList);

        request.setAttribute("liste_actor_json", jsonActor);
        request.setAttribute("liste_gender_json", jsonGender);

        return "create_film";
    }

    @PostMapping(value = "/film/create")
    public String createFilm(HttpServletRequest request) throws Exception {
        Film f = new Film();
        f.setTitle(request.getParameter("titre"));
        f.setNbr_team(Integer.valueOf(request.getParameter("team")));
        f.setDescription(request.getParameter("description"));

        String timeString = request.getParameter("duree"); // String representing the time
        DateFormat format = new SimpleDateFormat("HH:mm"); // Creating a date formatter
        try {
            Date date = format.parse(timeString);
            Time time = new Time(date.getTime());
            f.setDuration(time);

            String timeString1 = request.getParameter("tournage_debut"); // String representing the date
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd"); // Creating a date formatter
            Date date1 = format1.parse(timeString1);
            f.setStart_shooting(new java.sql.Date(date1.getTime()));

            // personnage
            String[] perso = request.getParameterValues("personnage_nom");
            String[] description = request.getParameterValues("personnage_description");
            String[] acteurs = request.getParameterValues("personnage_acteur");
            String[] genre = request.getParameterValues("personnage_genre");
            ArrayList<Character> characters = new ArrayList<Character>();
            Character character = null;
            for (int i = 0; i < perso.length; i++) {
                character = new Character();
                character.setName(perso[i]);
                character.setDescription(description[i]);
                character.setActor(new Actor(Integer.valueOf(acteurs[i])));
                character.setGender(new Gender(Integer.valueOf(genre[i])));
                characters.add(character);
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        filmService.createFilm(f);

        return getAllFilm(0, request);
    }

    @GetMapping(value = "/film/{id}")
    public String getFilmDetails(@PathVariable("id") Integer filmId, HttpServletRequest request) throws Exception {
        Film film = filmService.getFilmById(filmId);
        List<Character> characterList = charaterService.getCharacterByFilm(filmId);
        request.setAttribute("film", film);
        request.setAttribute("film_personnage", characterList);

        return "detail_film";
    }

    @GetMapping(value = "/film/{id}/current")
    public String setFilmSession(@PathVariable("id") Integer filmId, HttpServletRequest request, Model m)
            throws Exception {
        request.getSession().setAttribute("current_film", filmService.getFilmById(filmId));
        System.out.println(request.getSession().getAttribute("current_film"));
        System.out.println("ici");
        return getAllFilm(0, request);
    }

}
