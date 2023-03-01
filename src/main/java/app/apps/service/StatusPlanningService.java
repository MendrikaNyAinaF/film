package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.StatusPlanning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusPlanningService {
    @Autowired
    HibernateDAO hibernateDAO;

    public List<StatusPlanning> getAllStatusPlanning(){
        return hibernateDAO.getAll1(new StatusPlanning());
    }
}
