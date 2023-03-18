package app.apps.controller;

import app.apps.model.Character;
import app.apps.model.Dialogue;
import app.apps.service.DialogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DialogueController {
    @Autowired
    DialogueService dialogueService;

    @GetMapping(value = "/dialogue/modifier")
    public String updateDialogueForm(){
            return "";
    }

    @PostMapping(value = "/dialogue/modifer")
    public String updateDialogue(@RequestParam(name = "id") String[] id,
                                 @RequestParam(name = "scene_id") String[] scene_id,
                                 @RequestParam(name = "character_id") String[] character_id,
                                 @RequestParam(name = "texte") String[] texte,
                                 @RequestParam(name = "action") String[] action){
        List<Dialogue> update=new ArrayList<>();
        for(int i=0;i<id.length;i++){
            Dialogue d=new Dialogue();
            d.setId(Integer.valueOf(id[i]));
            d.setScene_id(Integer.valueOf(scene_id[i]));
           d.setCharacter(new Character(Integer.valueOf(character_id[i])));
           d.setTexte(texte[i]);
           d.setAction(action[i]);
           update.add(d);
        }
        dialogueService.updateDialogue(update);
        return "";
    }
}
