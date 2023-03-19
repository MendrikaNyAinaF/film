package app.apps.controller;

import app.apps.model.Actor;
import app.apps.model.Actor_unavailable;
import app.apps.model.Scene;
import app.apps.service.ActorService;
import app.apps.service.ActorUnavailableService;
import app.apps.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.util.List;

@Controller
public class ActorController {
    @Autowired
    ActorService actorService;

    @Autowired
    ActorUnavailableService actorUnavailableService;

    @Autowired
    SceneService sceneService;

    Gson gson = new Gson();

    @GetMapping(value = "/actors")
    public String actorList(HttpServletRequest request) {
        try {
            List<Actor> allActor = actorService.getAllActor();
            request.setAttribute("liste_actor", allActor);
        } catch (Exception e) {
        }
        return "liste_actor";
    }

    @GetMapping(value = "/actor/{id}/planning")
    public String disponibilite(HttpServletRequest request, @PathVariable(name = "id") Integer actorId) {
        try {
            Actor actor = actorService.getActorById(actorId);
            request.setAttribute("actor", actor);

            List<Actor_unavailable> nonDisponibilite = actorUnavailableService.dateUnavailableActor(actorId);
            request.setAttribute("liste_actor_unavailable", gson.toJson(nonDisponibilite));

            List<Scene> actorScene = (List<Scene>) sceneService.findByActorId(actorId);
            request.setAttribute("liste_scene", gson.toJson(actorScene));
        } catch (Exception e) {
        }
        return "planning_actor";
    }

    @PostMapping("/filmset/{id}/indisponible")
    public String actorIndisponible(@RequestParam Date date_debut, @RequestParam Date date_fin,
            @RequestParam String observation, @PathVariable(name = "id") Integer id,
            RedirectAttributes redirectAttributes) {
        try {
            // enregister l'indisponibilite du plateau

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("erreur", ex.getMessage());

        }
        return "redirect:/actor/" + id + "/planning";
    }
}
