package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.V_top_planning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VTopPlanningService {
    @Autowired
    HibernateDAO hibernateDAO;
    public List<? extends V_top_planning> getTopPlanning() throws Exception {
        return hibernateDAO.findBySql(new V_top_planning().getClass(),"select id,name,type_id,x,y,count(id) as uses from v_top_planning group by id,type_id,name,x,y",0, 400);
    }
}
