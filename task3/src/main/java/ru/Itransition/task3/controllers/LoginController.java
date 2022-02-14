package ru.Itransition.task3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.Itransition.task3.service.UserRegistration;
import ru.Itransition.task3.service.UserService;
import javax.validation.Valid;

@Controller
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginUser( Model model) {
        model.addAttribute("title", "Enter");
        return "login";
    }

    @GetMapping("/register")
    public String registrationUserGet(Model model){
        UserRegistration userRegistration = new UserRegistration();
        model.addAttribute("user", userRegistration);
        return "register";
    }

    @PostMapping("/register")
    public String registrationUserPost(
            @Valid UserRegistration userRegistration, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "register";
        }

        if (!userRegistration.getPassword().equals(userRegistration.getRepeatPassword())){
            bindingResult.rejectValue("password","", "passwords not equals");
            return "register";
        }

        userService.register(userRegistration);
        return "redirect:/login";
    }
}
