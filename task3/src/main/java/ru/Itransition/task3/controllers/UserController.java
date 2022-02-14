package ru.Itransition.task3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.Itransition.task3.model.User;
import ru.Itransition.task3.repository.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/allUsers")
    public String showUsers(Model model){
        Iterable<User> user = userRepository.findAll();
        model.addAttribute("user", user);
        return "allUsers";
    }
}
