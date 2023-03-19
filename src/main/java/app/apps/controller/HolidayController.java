package app.apps.controller;

import app.apps.model.Holiday;
import app.apps.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HolidayController {
    @Autowired
    HolidayService holidayService;

    @GetMapping(value = "/ferie")
    public String holidayForm(HttpServletRequest request) {
        return "form_ferie";
    }

    @PostMapping(value = "/ferie")
    public String insertHoliday(HttpServletRequest request, @RequestParam(name = "name") String name,
            @RequestParam(name = "date") String date) {
        Holiday holiday = new Holiday();
        holiday.setName(name);
        try {
            holiday.setDate(java.sql.Date.valueOf(date));
            holidayService.saveHoliday(holiday);
        } catch (Exception e) {
            request.setAttribute("erreur", "Data non valida");
        }
        return holidayForm(request);
    }
}
