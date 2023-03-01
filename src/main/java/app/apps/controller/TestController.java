package app.apps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
     @GetMapping(value = "/test")
     public String test() {
          return "detail_film";
     }
}
