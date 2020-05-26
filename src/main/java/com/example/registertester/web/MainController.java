package com.example.registertester.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*Enkel Controller, som bara kopplar ihop URL med webbsidor. Detta hade ocks� g�tt att l�sa med en
 * klass som implementerar WebMvcConfigurer och har @Configuration, som MvcConfig-klassen i m�ndagens projekt.*/
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
