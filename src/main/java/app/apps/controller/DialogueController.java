package app.apps.controller;

import app.apps.model.Character;
import app.apps.model.Dialogue;
import app.apps.model.Scene;
import app.apps.service.CharaterService;
import app.apps.service.DialogueService;
import app.apps.service.SceneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
public class DialogueController {
    @Autowired
    SceneService sceneService;
    @Autowired
    DialogueService dialogueService;
    @Autowired
    CharaterService characterService;

    Gson gson = new Gson();

    @GetMapping(value = "/film/{idfilm}/scene/{idscene}/dialogue/update")
    public String updateDialogueForm(HttpServletRequest req, @PathVariable("idfilm") int idfilm,
            @PathVariable("idscene") int idscene) {
        try {
            Scene s = sceneService.getById(idscene);
            req.setAttribute("scene", s);
            req.setAttribute("dialogue", sceneService.getDialogues(s));
            List<Character> chara = characterService.getCharacterByFilm(idfilm);
            req.setAttribute("liste_chara", chara);
            req.setAttribute("liste_character_json", gson.toJson(chara));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erreur", e);
        }
        return "form_dialogue";
    }

    @PostMapping(value = "/film/{idfilm}/scene/{idscene}/dialogue/update")
    public String updateDialogue(@RequestParam(name = "iddialogue") String[] id,
            @RequestParam(name = "dialogue_personnage", required = false) Integer[] character_id,
            @RequestParam(name = "dialogue_texte", required = false) String[] texte,
            @RequestParam(name = "dialogue_action", required = false) String[] action, HttpServletRequest req,
            @PathVariable("idfilm") int idfilm, @PathVariable("idscene") int idscene) {
        List<Dialogue> update = new ArrayList<>();
        try {
            System.out.println("dialogue " + req.getParameter("test"));
            if (id != null) {
                System.out.println("size" + character_id.length);
                for (int i = 0; i < id.length; i++) {
                    System.out.println("dialogue " + id);
                    Dialogue d = new Dialogue();
                    if (!id[i].equals("0"))
                        d.setId(Integer.valueOf(id[i]));
                    d.setScene_id(idscene);
                    d.setCharacter(new Character(character_id[i]));
                    d.setTexte(texte[i]);
                    d.setAction(action[i]);
                    update.add(d);
                }
                System.out.println("update " + update.size());
                dialogueService.updateDialogue(update);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("erreur", e.getMessage());
        }

        return updateDialogueForm(req, idfilm, idscene);
    }
}
