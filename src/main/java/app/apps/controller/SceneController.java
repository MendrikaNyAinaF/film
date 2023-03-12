package app.apps.controller;

import java.util.List;
import java.sql.Time;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import app.apps.service.SceneService;
import app.apps.service.PlanningService;
import app.apps.service.FilmSetService;
import app.apps.service.CharaterService;
import app.apps.service.DialogueService;
import app.apps.model.Scene;
import app.apps.model.Scene_status;
import app.apps.model.Planning;
import app.apps.model.StatusPlanning;
import app.apps.model.Film;
import app.apps.model.Gender;
import app.apps.model.Actor;
import app.apps.model.Dialogue;
import app.apps.model.Character;
import app.apps.model.Filmset;
import app.apps.model.StatusPlanning;
import app.apps.dao.HibernateDAO;

@Controller
public class SceneController {
    @Autowired
    HibernateDAO hibernate;

    @Autowired
    SceneService ss;

    @Autowired
    PlanningService ps;

    @Autowired
    FilmSetService fss;

    @Autowired
    CharaterService cs;

    @Autowired
    DialogueService ds;

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    @GetMapping(value = "/film/{idf}/scene/{ids}")
    public String detailsScene(@PathVariable(name = "ids") Integer idscene, HttpServletRequest request)
            throws Exception {
        HttpSession session = null;
        Film current = null;
        Scene s = null;
        Scene_status sst = null;
        try {
            session = SceneController.session();
            current = (Film) session.getAttribute("current_film");
            s = ss.getById(idscene);
            sst = ss.getByIdWStatus(idscene);
            request.setAttribute("scene", sst);
            request.setAttribute("dialogue", ss.getDialogues(s));
            request.setAttribute("status_planning", ss.getStatusPlanning());
        } catch (Exception ex) {
            ex.printStackTrace();
            // throws ex;
            request.setAttribute("erreur", ex.getMessage());
        }
        return "detail_scene";
    }

    @PostMapping(value = "/film/{idf}/scene/{ids}/planifier")
    public String plannifier(@PathVariable(name = "ids") Integer idscene, @RequestParam(name = "plan") String plan,
            HttpServletRequest request) throws Exception {
        HttpSession session = null;
        Film current = null;
        Scene s = null;
        Timestamp ts = null;
        try {
            session = SceneController.session();
            current = (Film) session.getAttribute("current_film");
            s = ss.getById(idscene);
            if (plan != null && !(plan.equals(""))) {
                ts = Timestamp.valueOf(plan.replace("T", " ") + ":00");
            }
            // ss.plannifier(s, ts);
        } catch (Exception ex) {
            ex.printStackTrace();
            // throws ex;
            request.setAttribute("erreur", ex.getMessage());
        }
        return detailsScene(idscene, request);
    }

    @PostMapping(value = "/film/{idf}/scene/{ids}/status")
    public String changeStatus(@PathVariable(name = "ids") Integer idscene,
            @RequestParam(name = "status") Integer status, HttpServletRequest request) throws Exception {
        HttpSession session = null;
        Film current = null;
        Scene s = null;
        Timestamp ts = null;
        try {
            session = SceneController.session();
            current = (Film) session.getAttribute("current_film");
            s = ss.getById(idscene);
            System.out.println("status : " + s);
            ps.changeStatus(s, status);
        } catch (Exception ex) {
            ex.printStackTrace();
            // throws ex;
            request.setAttribute("erreur", ex.getMessage());
        }
        return detailsScene(idscene, request);
    }

    @GetMapping(value = "/film/{id}/scene/create")
    public String to_create(HttpServletRequest request) {
        HttpSession session = null;
        Film current = null;
        session = SceneController.session();
        current = (Film) session.getAttribute("current_film");
        List<app.apps.model.Character> lc = cs.getCharacterByFilm(current.getId());
        request.setAttribute("plateau", fss.getAllFilmSet());
        request.setAttribute("liste_chara", lc);
        request.setAttribute("liste_character_json", ps.listToJson(lc));
        return "create_scene";
    }

    // titre,description,time_start,time_end,filmset,estimed_time
    // dialogue_personnage[],dialogue_texte[],dialogue_action[]
    @PostMapping(value = "/film/{id}/scene/create")
    public String create(@RequestParam(name = "titre") String titre,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "time_start") String time_start, @RequestParam(name = "time_end") String time_end,
            @RequestParam(name = "filmset") Integer filmset, @RequestParam(name = "estimed_time") String estimed_time,
            @RequestParam(name = "dialogue_personnage") Integer[] d_perso,
            @RequestParam(name = "dialogue_texte") String[] d_dialogue,
            @RequestParam(name = "dialogue_action") String[] d_action, HttpServletRequest request,
            @PathVariable(name = "id") Integer filmid) throws Exception {
        Scene s = null;
        Dialogue d = null;
        try {
            s = new Scene();
            s.setTitle(titre);
            s.setGlobal_action(description);
            s.setTime_start(Time.valueOf(time_start));
            s.setTime_end(Time.valueOf(time_end));
            s.setEstimated_time(Time.valueOf(estimed_time));
            s.setFilmset((Filmset) hibernate.findById(Filmset.class, filmset));
            s.setFilm_id(filmid);
            s = ss.create(s);
            d = new Dialogue();
            d.setScene_id(s.getId());

            for (int i = 0; i < d_perso.length; i++) {
                d.setCharacter(
                        (app.apps.model.Character) hibernate.findById(app.apps.model.Character.class, d_perso[i]));
                d.setTexte(d_dialogue[i]);
                d.setAction(d_action[i]);
                ds.createDialogue(d);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            // throws ex;
            request.setAttribute("erreur", ex.getMessage());
        }
        return to_create(request);
    }

    @GetMapping(value = "/film/{id}/scene/{idscene}/update")
    public String to_update(@PathVariable Integer idscene, HttpServletRequest request, HttpSession session) {
        Scene_status sst = null;
        try {
            request.setAttribute("plateau", fss.getAllFilmSet());
            // s = ss.getById(idscene);
            sst = ss.getByIdWStatus(idscene);
            request.setAttribute("scene", sst);
            request.setAttribute("status_planning", ss.getStatusPlanning());

        } catch (Exception ex) {
            ex.printStackTrace();
            // throws ex;
            request.setAttribute("erreur", ex.getMessage());
        }
        return "update_scene";
    }

    @PostMapping(value = "/film/{id}/scene/{idscene}/update")
    public String update(@RequestParam(name = "titre") String titre,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "time_start") String time_start, @RequestParam(name = "time_end") String time_end,
            @RequestParam(name = "filmset") Integer filmset, @RequestParam(name = "estimed_time") String estimed_time,
            @RequestParam(name = "prefered_shooting_start") String date_start,
            HttpServletRequest req, HttpSession session, @PathVariable Integer idscene) {
        Scene s = null;
        try {
            s = new Scene();
            // modification de la scene
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("erreur", ex.getMessage());
        }
        return to_update(idscene, req, session);
    }
}
