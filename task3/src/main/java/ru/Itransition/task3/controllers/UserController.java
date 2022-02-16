package ru.Itransition.task3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.Itransition.task3.model.Status;
import ru.Itransition.task3.model.User;
import ru.Itransition.task3.repository.UserRepository;

import java.util.List;

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

    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id){
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/allUsers";
    }

    @GetMapping("/users/block/{id}")
    public String blockUser(@PathVariable(value = "id") Long id){
        User user = userRepository.findById(id).orElseThrow();
        user.setStatus(Status.BLOCKED);
        userRepository.save(user);
//        if(user.getStatus().equals(Status.BLOCKED)){
//            return "redirect:/login";
//        }
        return "redirect:/allUsers";
    }

    @GetMapping("/users/allBlocked")
    public String userBlockedAll(){
        List<User> users =userRepository.findAll();
        for (User user : users){
           user.setStatus(Status.BLOCKED);
            userRepository.save(user);
        }
        return "redirect:/allUsers";
    }

    @GetMapping("/users/allUnblocked")
    public String userUnBlockedAll(){
        List<User> users =userRepository.findAll();
        for (User user : users){
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
        }
        return "redirect:/allUsers";
    }
}
