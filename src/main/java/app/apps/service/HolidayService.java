package app.apps.service;

import app.apps.dao.HibernateDAO;
import app.apps.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayService {
    @Autowired
    HibernateDAO hibernateDAO;

    public void saveHoliday(Holiday holiday){
        hibernateDAO.save1(holiday);
    }

    public List<Holiday> listHoliday(){
        return hibernateDAO.getAll1(new Holiday());
    }
}
