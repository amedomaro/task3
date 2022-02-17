package ru.Itransition.task3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.Itransition.task3.model.Status;
import ru.Itransition.task3.model.User;
import ru.Itransition.task3.repository.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {

    private User user;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/allUsers")
    public String showUsers(Model model){
        Iterable<User> user = userRepository.findAll();
        model.addAttribute("user", user);
        return "allUsers";
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] names = request.getParameterValues("names");
        List list = Arrays.asList(names);
        request.setAttribute("names", list);
        RequestDispatcher rd = request.getRequestDispatcher("employee.jsp");
        rd.forward(request, response);
        for (String str : names){
            System.out.println(str);
        }
    }

//    @GetMapping("/goga")
//    public String test(@RequestParam("srch-term") String str, Model model){
//        System.out.println(str);
//        return "redirect:/allUser";
//    }

    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id){
        user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return "redirect:/allUsers";
    }

    @GetMapping("/users/block")
    public String userBlockedAll(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            user.setStatus(Status.BLOCKED);
            userRepository.save(user);
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        new SecurityContextLogoutHandler().logout(request, response, auth);
        return "redirect:/login?logout";
    }

    @RequestMapping(value = "/users/blocked/{id}", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request,
                             HttpServletResponse response,
                             @PathVariable(value = "id") Long id) {
        user = userRepository.findById(id).orElseThrow();
        user.setStatus(Status.BLOCKED);
        userRepository.save(user);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (user.getUsername().equals(auth.getName())) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            return "redirect:/login?logout";
        }
        return "redirect:/allUsers";
    }


    @GetMapping("/users/blockUnblock/{id}")
    public String blockUnblock(@PathVariable(value = "id") Long id){
        user = userRepository.findById(id).orElseThrow();
        user.setStatus(user.getStatus().equals(Status.BLOCKED)? Status.ACTIVE : Status.BLOCKED);
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
