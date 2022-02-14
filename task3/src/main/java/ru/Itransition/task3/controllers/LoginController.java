package ru.Itransition.task3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.Itransition.task3.service.UserRegistrationRepr;
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
        UserRegistrationRepr userRegistrationRepr = new UserRegistrationRepr();
        model.addAttribute("user", userRegistrationRepr);
        return "register";
    }

    @PostMapping("/register")
    public String registrationUserPost(
            @Valid UserRegistrationRepr userRegistrationRepr, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "register";
        }

        if (!userRegistrationRepr.getPassword().equals(userRegistrationRepr.getRepeatPassword())){
            bindingResult.rejectValue("password","", "passwords not equals");
            return "register";
        }

        userService.create(userRegistrationRepr);
        return "redirect:/login";
    }
}
