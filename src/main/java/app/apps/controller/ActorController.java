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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ActorController {
    @Autowired
    ActorService actorService;

    @Autowired
    ActorUnavailableService actorUnavailableService;

    @Autowired
    SceneService sceneService;

    @GetMapping(value = "/actor/list")
    public String actorList(HttpServletRequest request){
        try {
            List<Actor> allActor=actorService.getAllActor();
            request.setAttribute("liste_actor",allActor);
        }
        catch (Exception e){}
        return "";
    }

    @GetMapping(value = "/actor/{id}/diponibilite")
    public String disponibilite(HttpServletRequest request, @PathVariable(name = "id") Integer actorId){
       try {
           List<Actor_unavailable> nonDisponibilite=actorUnavailableService.dateUnavailableActor(actorId);
           request.setAttribute("liste_unavailable",nonDisponibilite);

           List<Scene> actorScene= (List<Scene>) sceneService.findByActorId(actorId);
           request.setAttribute("actor_scene",actorScene);
       }
       catch (Exception e){}
        return "";
    }
}
