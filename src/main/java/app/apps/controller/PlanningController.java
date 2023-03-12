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
import javax.servlet.http.HttpSession;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PlanningController {
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

    /*
     * @GetMapping(value = "/film/{id}/planning")
     * public String theplanning(HttpServletRequest request, HttpSession session)
     * throws Exception {
     * Film current = null;
     * try {
     * current = (Film) session.getAttribute("current_film");
     * request.setAttribute("liste_planning",
     * planningService.listToJson(planningService.listPlanning(current.getId())));
     * } catch (Exception ex) {
     * ex.printStackTrace();
     * throw ex;
     * }
     * return "planning";
     * }
     */

    // todo
    @GetMapping(value = "/film/{id}/planifier")
    public String to_planning(HttpServletRequest request, HttpSession session) {
        Film current = null;
        try {
            current = (Film) session.getAttribute("current_film");
            // liste des scenes non planifiées atao dans "liste_scene"
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("erreur", ex.getMessage());
        }
        return "form_planning";
    }

    @PostMapping(value = "/film/{id}/planifier")
    public String planning(HttpServletRequest request, HttpSession session,
            @RequestParam(required = false, name = "idscene") Integer[] ids,
            @RequestParam(name = "start_date") String date) {
        Film current = null;
        java.sql.Date commencement = null;
        try {
            current = (Film) session.getAttribute("current_film");
            if (current != null) {
                commencement = java.sql.Date.valueOf(date);
            }
            // traitement du planning
            return "proposing_planning";// "redirect:/film/" + current.getId() + "/planning";
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("erreur", ex.getMessage());
            return to_planning(request, session);
        }
    }

}
