package app.apps.controller;

import app.apps.model.Actor;
import app.apps.model.Actor_unavailable;
import app.apps.model.Film;
import app.apps.model.Planning;
import app.apps.model.Scene;
import app.apps.service.ActorService;
import app.apps.service.ActorUnavailableService;
import app.apps.service.FilmService;
import app.apps.service.PlanningService;
import app.apps.service.SceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
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

    @Autowired
    ActorUnavailableService aus;

    @Autowired
    PlanningService planningService;

    @Autowired
    FilmService filmService;

    @GetMapping(value = "/actors")
    public String actorList(HttpServletRequest request) {
        try {
            List<Film> film = filmService.allFilm();
            List<Actor> allActor = actorService.getAllActor();
            request.setAttribute("liste_actor", allActor);
            request.setAttribute("liste_film", film);
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

            List<Planning> actorScene = planningService.findByActorId(actorId);
            request.setAttribute("liste_scene", gson.toJson(actorScene));
        } catch (Exception e) {
        }
        return "planning_actor";
    }

    @PostMapping(value = "/actor/{id}/indisponible")
    public String insertActorUnavailable(@PathVariable(name = "id") Integer actorId,
            @RequestParam(name = "date_debut") String date_debut, @RequestParam(name = "date_fin") String date_fin,
            @RequestParam(name = "observation") String observation, RedirectAttributes redirectAttributes) {
        Actor_unavailable actor_unavailable = new Actor_unavailable();
        actor_unavailable.setActor_id(actorId);
        try {
            actor_unavailable.setDate_debut(java.sql.Date.valueOf(date_debut));
            actor_unavailable.setDate_fin(java.sql.Date.valueOf(date_fin));
            actor_unavailable.setObservation(observation);
            aus.insertUnavailable(actor_unavailable);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erreur", e.getMessage());
        }
        return "redirect:/actor/" + actorId + "/planning";
    }

    @GetMapping("/actor/{id}/pdf")
    public void pdfDialogue(@PathVariable(name = "id") Integer actorId, ServletOutputStream out,
            HttpServletResponse response, @RequestParam(name = "filmid") Integer filmid) throws IOException {
        Document document = null;
        PdfWriter writer = null;
        try {
            document = new Document();
            writer = PdfWriter.getInstance(document, out);
            document.open();
            Actor ac = actorService.getActorById(actorId);
            List<Scene> scene = sceneService.findActorScene(actorId, filmid);
            actorService.pdfActor(ac, scene, document);
        } catch (Exception e) {
            response.setContentType("text/html;charset=UTF-8");
            out.println(e.getMessage());
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }
}
