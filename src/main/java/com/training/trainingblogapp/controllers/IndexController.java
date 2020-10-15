package com.training.trainingblogapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"","/"})
public class IndexController {

    @GetMapping({"index", "index.html"})
    public String showIndexPage() {
        return "index";
    }

    @GetMapping({"register", "register.html"})
    public String showRegisterPage() {
        return "register";
    }

    @GetMapping({"about", "about.html"})
    public String showAboutPage() {
        return "about";
    }

}
