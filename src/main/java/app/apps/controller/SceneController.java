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
    public String detailsScene(@PathVariable(name = "ids") Integer idscene, Model model) throws Exception {
        HttpSession session = null;
        Film current = null;
        Scene s = null;
        try {
            session = SceneController.session();
            current = (Film) session.getAttribute("current_film");
            s = ss.getById(idscene);
            model.addAttribute("scene", s);
            model.addAttribute("dialogue", ss.getDialogues(s));
            model.addAttribute("status_planning", ss.getStatusPlanning());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return "detail_scene";
    }

    @PostMapping(value = "/film/{idf}/scene/{ids}/planifier")
    public String plannifier(@PathVariable(name = "ids") Integer idscene, @RequestParam(name = "plan") String plan,
            Model model) throws Exception {
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
            ss.plannifier(s, ts);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return detailsScene(idscene, model);
    }

    @PostMapping(value = "/film/{idf}/scene/{ids}/status")
    public String changeStatus(@PathVariable(name = "ids") Integer idscene,
            @RequestParam(name = "status") Integer status, Model model) throws Exception {
        HttpSession session = null;
        Film current = null;

        Scene s = null;
        Timestamp ts = null;
        try {
            session = SceneController.session();
            current = (Film) session.getAttribute("current_film");
            ps.changeStatus(s, status);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return detailsScene(idscene, model);
    }

    @GetMapping(value = "/film/{id}/planifier")
    public String to_planning(Model model) throws Exception {
        HttpSession session = null;
        Film current = null;
        try {
            session = SceneController.session();
            current = (Film) session.getAttribute("current_film");
            model.addAttribute("liste_planning", ps.listToJson(ps.listPlanning(current.getId())));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return "planning";
    }

    @PostMapping(value = "/film/{id}/planifier")
    public String planning(Model model) throws Exception {
        HttpSession session = null;
        Film current = null;
        try {
            session = SceneController.session();
            current = (Film) session.getAttribute("current_film");
            ps.globalPlan(current);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return to_planning(model);
    }

    @GetMapping(value = "/film/{id}/scene/create")
    public String to_create(Model model) {
        HttpSession session = null;
        Film current = null;
        session = SceneController.session();
        current = (Film) session.getAttribute("current_film");
        List<app.apps.model.Character> lc = cs.getCharacterByFilm(current.getId());
        model.addAttribute("plateau", fss.getAllFilmSet());
        model.addAttribute("liste_chara", lc);
        model.addAttribute("liste_character_json", ps.listToJson(lc));
        return "create_scene";
    }

    //titre,description,time_start,time_end,filmset,estimed_time
    //dialogue_personnage[],dialogue_texte[],dialogue_action[]
    @PostMapping(value = "/film/{id}/scene/create")
    public String create(String titre,String description,String time_start,String time_end,Integer filmset,String estimed_time,Integer[] d_perso,String[] d_dialogue,String[] d_action,Model model)throws Exception {
        Scene s = null;
        Dialogue d = null;
        try {
            s = new Scene();
            s.setTitle(titre);
            s.setGlobal_action(description);
            s.setTime_start(Time.valueOf(time_start));
            s.setTime_end(Time.valueOf(time_end));
            s.setEstimated_time(Time.valueOf(estimed_time));
            s.setFilmset((Filmset) hibernate.findById(Filmset.class,filmset));
            s = ss.create(s);
            d = new Dialogue();
            d.setScene_id(s.getId());
            for(int i=0;i<d_perso.length;i++){
                d.setCharacter((app.apps.model.Character) hibernate.findById(app.apps.model.Character.class,d_perso[i]));
                d.setTexte(d_dialogue[i]);
                d.setAction(d_action[i]);
                ds.createDialogue(d);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return to_create(model);
    }
}
