package app.apps.controller;

import app.apps.model.Actor_unavailable;
import app.apps.service.ActorUnavailableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ActorUnavalaibleController {
    @Autowired
    ActorUnavailableService aus;

    @PostMapping(value = "/actorunavailable/insert/{id}")
    public String insertActorUnavailable(@PathVariable(name = "id") Integer actorId, @RequestParam(name = "date_debut") String date_debut, @RequestParam(name = "date_fin") String date_fin, @RequestParam(name = "observation") String observation){
        Actor_unavailable actor_unavailable=new Actor_unavailable();
        actor_unavailable.setActor_id(actorId);
        try{
            actor_unavailable.setDate_debut(java.sql.Date.valueOf(date_debut));
            actor_unavailable.setDate_fin(java.sql.Date.valueOf(date_fin));
            actor_unavailable.setObservation(observation);
            aus.insertUnavailable(actor_unavailable);
        }
        catch (Exception e){}

        return "";
    }
}
