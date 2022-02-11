package ru.Itransition.task3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/allUsers")
    public String showUsers(Model model){
        model.addAttribute("name", "user");
        return "allUsers";
    }
}
