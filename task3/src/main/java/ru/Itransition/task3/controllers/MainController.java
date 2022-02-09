package ru.Itransition.task3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("title", "The main page");
        return "homePage";
    }

}
