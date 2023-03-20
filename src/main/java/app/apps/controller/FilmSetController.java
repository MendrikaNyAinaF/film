package app.apps.controller;

import javax.servlet.http.HttpServletRequest;

import app.apps.model.*;
import app.apps.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.List;

@Controller
public class FilmSetController {
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

     @Autowired
     FilmSetService filmSetService;

     @Autowired
     FilmSetPlanningService filmSetPlanningService;

     @Autowired
     VTopPlanningService vTopPlanningService;

     @Autowired
     VPlanningService vPlanningService;

     @Autowired
     FilmsetUnavailableService fus;

     @GetMapping(value = "/filmsets")
     public String filmsets(HttpServletRequest req) {
          try {
               // passer la liste des plateaux liste_filmset
               List<Filmset> allfilmset = filmSetService.getAllFilmSet();
               req.setAttribute("liste_filmset", allfilmset);

               // passer les statistiques des plateaux en json
               Gson gson = new Gson();
               List<V_top_planning> v_top_plannings = (List<V_top_planning>) vTopPlanningService.getTopPlanning();
               String planningJson = gson.toJson(v_top_plannings);
               req.setAttribute("filmset_json", planningJson);

          } catch (Exception ex) {
               ex.printStackTrace();
               req.setAttribute("erreur", ex.getMessage());
          }
          return "chart_filmset";
     }

     @GetMapping(value = "/filmset/{id}/planning")
     public String filmsetPlanning(HttpServletRequest req, @PathVariable(name = "id") Integer id) {
          try {
               // passer le plateau
               Filmset filmset = filmSetService.getFilmsetById(id);
               req.setAttribute("plateau", filmset);

               // passer la disponibilite du plateau
               List<Filmset_unavailable> filmset_plannings = filmSetPlanningService.getByFilmsetId(id);
               Gson gson = new Gson();
               System.out.println("filmset_plannings: " + filmset_plannings.size());
               req.setAttribute("liste_filmset_unavailable", gson.toJson(filmset_plannings));

               // passer le planning des plateaux
               List<V_planning> allPlanning = (List<V_planning>) vPlanningService.getPlanning(id);
               req.setAttribute("liste_scene", gson.toJson(allPlanning));

          } catch (Exception ex) {
               ex.printStackTrace();
               req.setAttribute("erreur", ex.getMessage());
          }
          return "planning_filmset";
     }

     @PostMapping(value = "/filmset/{id}/indisponible")
     public String insertFilmsetUnavailable(@PathVariable(name = "id") Integer filmsetId,
               @RequestParam(name = "date_debut") String date_debut, @RequestParam(name = "date_fin") String date_fin,
               @RequestParam(name = "observation") String observation, RedirectAttributes redirectAttributes) {
          Filmset_unavailable filmset_unavailable = new Filmset_unavailable();
          filmset_unavailable.setFilmset_id(filmsetId);
          try {
               filmset_unavailable.setDate_debut(java.sql.Date.valueOf(date_debut));
               filmset_unavailable.setDate_fin(java.sql.Date.valueOf(date_fin));
               filmset_unavailable.setObservation(observation);
               fus.saveUnavailableFilmSet(filmset_unavailable);
          } catch (Exception e) {
               e.printStackTrace();
               redirectAttributes.addFlashAttribute("erreur", e.getMessage());
          }

          return "redirect:/filmset/" + filmsetId + "/planning";
     }
}
