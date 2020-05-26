package com.example.registertester.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*Enkel Controller, som bara kopplar ihop URL med webbsidor. Detta hade också gått att lösa med en
 * klass som implementerar WebMvcConfigurer och har @Configuration, som MvcConfig-klassen i måndagens projekt.*/
@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}
