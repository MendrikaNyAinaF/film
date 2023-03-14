package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.V_planning;
import app.apps.model.V_top_planning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VPlanningService {
    @Autowired
    HibernateDAO hibernateDAO;

    public List<? extends V_planning> getPlanning(Integer id) throws Exception {
        String req="select id,scene_id,status,date_debut,date_fin,title from v_planning where filmset_id="+id+" and status != 3";
        return hibernateDAO.findBySql(new V_planning().getClass(),req,0, 400);
    }
}
