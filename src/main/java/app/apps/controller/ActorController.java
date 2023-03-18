package app.apps.controller;

import app.apps.model.Actor;
import app.apps.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ActorController {
    @Autowired
    ActorService actorService;

    @GetMapping
    public String actorList(HttpServletRequest request){
        try {
            List<Actor> allActor=actorService.getAllActor();
            request.setAttribute("liste_actor",allActor);
        }
        catch (Exception e){}
        return "";
    }
}
