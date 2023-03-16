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
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    @Autowired
    StatusPlanningService statusplanningService;

    @Autowired
    SceneService sceneService;

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
    public String getSceneByFilmId(@PathVariable("id") Integer filmId, @PathVariable("page") Integer page,
            HttpServletRequest request) {
        Integer limit = 6;
        Integer offset = page * limit;
        List<Scene> listScene = new ArrayList<>();
        String mc = "";
        Integer status = null;
        Integer[] actors = new Integer[0];
        if (request.getSession().getAttribute("scene_motcle") != null)
            mc = (String) request.getSession().getAttribute("scene_motcle");
        if (request.getSession().getAttribute("scene_status") != null)
            status = (Integer) request.getSession().getAttribute("scene_status");
        if (request.getSession().getAttribute("scene_actors") != null)
            actors = (Integer[]) request.getSession().getAttribute("scene_actors");

        listScene = sceneService.listScenes(filmId, mc, status, actors, page);

        List<Scene> allF = sceneService.getAllScene();
        int nbPage;
        if(request.getSession().getAttribute("nbrScene")==null){
            nbPage = allF.size();
        }
        else{
            nbPage = (int) request.getSession().getAttribute("nbrScene");
        }
        nbPage = (int) Math.ceil((double) nbPage / limit);
        Boolean endpage = false;
        if (nbPage == page+1) {
            endpage = true;
        }
        request.setAttribute("status", statusplanningService.getAllStatusPlanning());
        request.setAttribute("character", actorService.getAllActor());
        request.setAttribute("liste_scene", listScene);
        request.setAttribute("endPage", endpage);
        request.setAttribute("page", page);

        return "liste_scene";
    }

    @PostMapping(value = "/search_scene")
    public String searchScene(@RequestParam(name = "status") Integer status,
            @RequestParam(name = "actors", required = false) Integer[] actors, HttpServletRequest request)
            throws Exception {
        request.getSession().setAttribute("scene_motcle", request.getParameter("motcle"));
        request.getSession().setAttribute("scene_status", status);
        if (actors != null)
            request.getSession().setAttribute("scene_actors", actors);
        else {
            request.getSession().setAttribute("scene_actors", null);
        }
        Film f = (Film) request.getSession().getAttribute("current_film");
        if(actors == null) actors = new Integer[0];
        Integer countScenesWithResearch = sceneService.countElements(f.getId(),request.getParameter("motcle"),status,actors);
        request.getSession().setAttribute("nbrScene",countScenesWithResearch);
        return getSceneByFilmId(f.getId(), 0, request);
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
    public String createFilm(HttpServletRequest request, @RequestParam(name = "titre") String title,
            @RequestParam(name = "description") String desc, @RequestParam(name = "personnage_nom") String[] perso,
            @RequestParam(name = "personnage_description") String[] description,
            @RequestParam(name = "personnage_genre") Integer[] genre,
            @RequestParam(name = "personnage_acteur") Integer[] acteurs,
            @RequestParam(name = "image") String image, @RequestParam(name = "duree") String timeString)
            throws Exception {
        // System.out.println(image);
        Film f = new Film();
        f.setTitle(title);
        f.setNbr_team(1);
        f.setDescription(desc);
        f.setVisuel(image);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime localTime = LocalTime.parse(timeString, formatter);
            Time time = Time.valueOf(localTime);
            f.setDuration(time);

            // personnage
            ArrayList<Character> characters = new ArrayList<Character>();
            Character character = null;
            for (int i = 0; i < perso.length; i++) {
                character = new Character();
                character.setName(perso[i]);
                character.setDescription(description[i]);
                character.setActor(new Actor(acteurs[i]));
                character.setGender(new Gender(genre[i]));
                characters.add(character);
            }

            // creer le film
            filmService.createFilm(f);
            for (Character c : characters) {
                c.setFilm_id(f.getId());
                charaterService.create(c);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("erreur", e.getMessage());
        }

        return getAllFilm(0, request);
    }

    @GetMapping(value = "/film/{id}")
    public String getFilmDetails(@PathVariable("id") Integer filmId, HttpServletRequest request) throws Exception {
        Film film = filmService.getFilmById(filmId);
        List<Character> characterList = charaterService.getCharacterByFilm(filmId);
        request.setAttribute("film_detail", film);
        request.setAttribute("character_list", characterList);

        return "detail_film";
    }

    @GetMapping(value = "/film/{id}/current")
    public String setFilmSession(@PathVariable("id") Integer filmId, HttpServletRequest request, Model m)
            throws Exception {
        request.getSession().setAttribute("current_film", filmService.getFilmById(filmId));

        return getAllFilm(0, request);
    }

}
