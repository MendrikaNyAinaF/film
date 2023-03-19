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

     @PostMapping("/filmset/{id}/indisponible")
     public String filmsetIndisponible(@RequestParam Date date_debut, @RequestParam Date date_fin,
               @RequestParam String observation, @PathVariable(name = "id") Integer id,
               RedirectAttributes redirectAttributes) {
          try {
               // enregister l'indisponibilite du plateau

          } catch (Exception ex) {
               redirectAttributes.addFlashAttribute("erreur", ex.getMessage());

          }
          return "redirect:/filmset/" + id + "/planning";
     }
}
