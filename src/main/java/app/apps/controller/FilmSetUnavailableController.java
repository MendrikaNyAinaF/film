package app.apps.controller;

import app.apps.model.Filmset_unavailable;
import app.apps.service.FilmsetUnavailableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FilmSetUnavailableController {
    @Autowired
    FilmsetUnavailableService fus;

    @PostMapping(value = "/filmsetunavailable/insert/{id}")
    public String insertFilmsetUnavailable(@PathVariable(name = "id") Integer filmsetId,@RequestParam(name = "date_debut") String date_debut,@RequestParam(name = "date_fin") String date_fin,@RequestParam(name = "observation") String observation){
        Filmset_unavailable filmset_unavailable=new Filmset_unavailable();
        filmset_unavailable.setFilmset_id(filmsetId);
        try{
            filmset_unavailable.setDate_debut(java.sql.Date.valueOf(date_debut));
            filmset_unavailable.setDate_fin(java.sql.Date.valueOf(date_fin));
            filmset_unavailable.setObservation(observation);
            fus.saveUnavailableFilmSet(filmset_unavailable);
        }
        catch (Exception e){

        }

        return "";
    }
}
