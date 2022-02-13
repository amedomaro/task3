package ru.Itransition.task3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

}
