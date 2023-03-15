package app.apps.controller;

import javax.servlet.http.HttpServletRequest;

import app.apps.model.*;
import app.apps.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
               List<Filmset> allfilmset=filmSetService.getAllFilmSet();
               req.setAttribute("liste_filmset", allfilmset);

               // passer les statistiques des plateaux en json
               Gson gson = new Gson();
               List<V_top_planning> v_top_plannings= (List<V_top_planning>) vTopPlanningService.getTopPlanning();
               String planningJson=gson.toJson(v_top_plannings);
               req.setAttribute("filmset_json",planningJson);


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
               Filmset filmset=filmSetService.getFilmsetById(id);
               req.setAttribute("plateau", filmset);

               // passer la disponibilite du plateau
               List<Filmset_unavailable> filmset_plannings= filmSetPlanningService.getByFilmsetId(id);
               Gson gson = new Gson();
               req.setAttribute("liste_filmsetplanning",gson.toJson(filmset_plannings));

               // passer le planning des plateaux
               List<V_planning> allPlanning= (List<V_planning>) vPlanningService.getPlanning(id);
               req.setAttribute("liste_scene",gson.toJson(allPlanning));


          } catch (Exception ex) {
               ex.printStackTrace();
               req.setAttribute("erreur", ex.getMessage());
          }
          return "planning_filmset";
     }
}
