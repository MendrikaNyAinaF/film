package app.apps.controller;

import app.apps.model.*;
import app.apps.model.Character;
import app.apps.model.reponse.*;
import app.apps.service.*;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class RestPlanningController {
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

    @RequestMapping(value="/film/{idf}/confirmer_planning", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> confirmPlanning(@RequestBody Planning[] planning)throws Exception{
        List<Planning> lp = null;
        Planning p = null;
        try{
            lp = new ArrayList<Planning>();
            for(int i=0;i<planning.length;i++){
                p = new Planning();
                p.setDate_debut(new Timestamp(planning[i].getDate_debut().getTime()));
                p.setDate_fin(new Timestamp(planning[i].getDate_fin().getTime()));
                p.setScene(sceneService.getById(planning[i].getId()));
                System.out.println("    "+p.getDate_debut().toString());
                lp.add(p);
            }
            planningService.insertPlanning(lp);
        }
        catch(Exception ex){
            System.out.println("    Error:"+ex.getMessage());
            return new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
        System.out.println("    Success confirmation");
        return new ResponseEntity("Planning confirmed",HttpStatus.OK);
    }
}
