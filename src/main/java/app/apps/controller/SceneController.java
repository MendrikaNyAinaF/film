package app.apps.controller;

import java.sql.Timestamp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import app.apps.service.SceneService;
import app.apps.service.PlanningService;
import app.apps.model.Scene;
import app.apps.model.Planning;
import app.apps.model.StatusPlanning;
import app.apps.model.Film;
import app.apps.model.Gender;
import app.apps.model.Actor;
import app.apps.model.Dialogue;
import app.apps.model.StatusPlanning;
import app.apps.dao.HibernateDAO;

@Controller
public class SceneController {

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

    @GetMapping(value = "/film/{idf}/scene/{ids}")
    public String detailsScene(@PathVariable(name = "ids") Integer idscene,Model model)throws Exception {
        HttpSession session = null;
        Film current = null;
        SceneService ss = null;
        Scene s = null;
        try{
            session = SceneController.session();
            current = (Film) session.getAttribute("current_film");
            ss = new SceneService();
            s = ss.getById(idscene);
            model.addAttribute("scene",s);
            model.addAttribute("dialogue",ss.getDialogues(s));
            model.addAttribute("statusPlanning",ss.getStatusPlanning());
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return "detail_scene";
    }

    @PostMapping(value = "/film/{idf}/scene/{ids}/planifier")
    public String plannifier(@PathVariable(name = "ids") Integer idscene,@RequestParam(name = "plan") String plan,Model model)throws Exception {
        HttpSession session = null;
        Film current = null;
        SceneService ss = null;
        Scene s = null;
        Timestamp ts = null;
        try{
            session = SceneController.session();
            current = (Film) session.getAttribute("current_film");
            ss = new SceneService();
            s = ss.getById(idscene);
            if(plan!=null && !(plan.equals(""))){
                ts = Timestamp.valueOf(plan.replace("T"," ")+":00");
            }
            ss.plannifier(s,ts);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return detailsScene(idscene,model);
    }

    @PostMapping(value = "/film/{idf}/scene/{ids}/planifier")
    public String changeStatus(@PathVariable(name = "ids") Integer idscene,@RequestParam(name = "status") Integer status,Model model)throws Exception {
        HttpSession session = null;
        Film current = null;
        PlanningService ps = null;
        Scene s = null;
        Timestamp ts = null;
        try{
            session = SceneController.session();
            current = (Film) session.getAttribute("current_film");
            ps = new PlanningService();
            ps.changeStatus(s,status);
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return detailsScene(idscene,model);
    }

    @GetMapping(value = "/film/{id}/planifier")
    public String to_planning(Model model)throws Exception{
        HttpSession session = null;
        Film current = null;
        PlanningService ps = null;
        try{
            session = SceneController.session();
            current = (Film) session.getAttribute("current_film");
            model.addAttribute("liste_planning",ps.listToJson(ps.listPlanning(current.getId())));
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return "planning";
    }

    @GetMapping(value = "/film/{id}/scene/create")
    public String to_create(Model model){
        model.addAttribute("plateau",null);
        model.addAttribute("liste_chara",null);
        model.addAttribute("liste_character_json",null);
        return "create_scene";
    }
    
    @PostMapping(value = "/film/{id}/scene/create")
    public String create(Model model){
        SceneService ss = null;
        try{
            ss = new SceneService();
            //ss.create();
        }
        catch(Exception ex){
            ex.printStackTrace();
            throw ex;
        }
        return to_create(model);
    }
}
