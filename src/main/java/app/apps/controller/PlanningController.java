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
import org.springframework.web.bind.annotation.ResponseBody;

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
     * //throws ex; request.setAttribute("erreur", ex.getMessage());
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
            request.setAttribute("liste_scene",sceneService.getUnplannedScene(current.getId()));
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("erreur", ex.getMessage());
        }
        return "form_planning";
    }

    @PostMapping(value = "/film/{id}/planifier")
    public String planning(HttpServletRequest request, HttpSession session,
            @RequestParam(required = false, name = "idscene") Integer[] ids,
            @RequestParam(name = "start_date") String date1, @RequestParam(name = "end_date") String date2) {
        Film current = null;
        Timestamp commencement = null;
        try {
            current = (Film) session.getAttribute("current_film");
            if (current != null) {
                commencement = Timestamp.valueOf(date1.replace("T"," ")+":00");
            }
            // traitement du planning
            List<Planning> lp = planningService.proposerPlanning(ids,Timestamp.valueOf(date1.replace("T"," ")+":00"),Timestamp.valueOf(date2.replace("T"," ")+":00"));
            System.out.println(lp);
            if(lp!=null) System.out.println(lp.size());
            request.setAttribute("nbr_scene",ids.length);
            request.setAttribute("start_date",date1.replace("T"," "));
            request.setAttribute("liste_planning",lp);
            return "proposing_planning";// "redirect:/film/" + current.getId() + "/planning";
        } catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("erreur", ex.getMessage());
            return to_planning(request, session);
        }
    }

    @PostMapping("/film/{idf}/confirmer_planning")
    public String confirmPlanning(HttpServletRequest request, HttpSession session, @RequestParam(name = "id[]") Integer[] ids, @RequestParam(name = "date_debut[]") String[] date1, @RequestParam(name = "date_fin[]") String[] date2)throws Exception{
        List<Planning> lp = null;
        Planning p = null;
        try{
            lp = new ArrayList<Planning>();
            for(int i=0;i<ids.length;i++){
                p = new Planning();
                p.setDate_debut(Timestamp.valueOf(date1[i].replace("T"," ")+":00"));
                p.setDate_fin(Timestamp.valueOf(date2[i].replace("T"," ")+":00"));
                p.setScene(sceneService.getById(ids[i]));
                lp.add(p);
            }
            planningService.insertPlanning(lp);
        }
        catch(Exception ex){
            throw ex;
        }
        return to_planning(request, session);
    }

}
