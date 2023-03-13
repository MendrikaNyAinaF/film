package app.apps.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import app.apps.service.ActorService;
import app.apps.service.CharaterService;
import app.apps.service.FilmService;
import app.apps.service.GenderService;
import app.apps.service.PlanningService;
import app.apps.service.SceneService;
import app.apps.service.StatusPlanningService;

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
               req.setAttribute("",planningJson);


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
               req.setAttribute("liste_filmset", filmset);

               // passer la disponibilite du plateau
               List<Filmset_planning> filmset_plannings= filmSetPlanningService.getByFilmsetId(id);
               req.setAttribute("liste_filmsetplanning",filmset_plannings);

               // passer le planning des plateaux
               List<Scene> allScene=sceneService.getSceneByFilmSetId(id);
               req.setAttribute("liste_scene",allScene);


          } catch (Exception ex) {
               ex.printStackTrace();
               req.setAttribute("erreur", ex.getMessage());
          }
          return "planning_filmset";
     }
}
