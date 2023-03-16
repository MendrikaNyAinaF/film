package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {
    @Autowired
    HibernateDAO hibernateDAO;

    public void saveHoliday(Holiday holiday){
        hibernateDAO.save1(holiday);
    }
}
