package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Dialogue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DialogueService {
    @Autowired
    HibernateDAO hibernateDAO;

    public void createDialogue(Dialogue d){
        hibernateDAO.save1(d);
    }

    public List<Dialogue> getDialogueBySceneId(int idScene){
        Dialogue dialogue=new Dialogue();
        return hibernateDAO.getByIdScene(dialogue,idScene);
    }
}
