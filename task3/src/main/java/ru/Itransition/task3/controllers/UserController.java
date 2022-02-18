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
import ru.Itransition.task3.service.UserAuthService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class UserController {

    private User user;
    private UserAuthService userAuthService;
    private HttpServletRequest httpServletRequest;
    private HttpServletResponse httpServletResponse;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserController(UserAuthService userAuthService, HttpServletRequest httpServletRequest,
                          HttpServletResponse httpServletResponse, UserRepository userRepository) {
        this.userAuthService = userAuthService;
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
        this.userRepository = userRepository;
    }

    @GetMapping("/allUsers")
    public String showUsers(Model model) {
        Iterable<User> user = userRepository.findAll();
        model.addAttribute("user", user);
        return "allUsers";
    }

    @RequestMapping(value = "/target", method = RequestMethod.GET)
    public String userTarget(HttpServletRequest request) {
        String[] checkBox = request.getParameterValues("checkBoxUser");
        String[] delete = request.getParameterValues("delete");
        String[] block = request.getParameterValues("block");
        String[] unlock = request.getParameterValues("unlock");
        if (checkBox == null) return "redirect:/allUsers";
        if (delete != null) deleteUser(checkBox);
        if (block != null) blockUser(checkBox);
        if (unlock != null) unlockUser(checkBox);
        return "redirect:/allUsers";
    }

    private String deleteUser(String[] checkBox) {
        for (String id : checkBox) {
            user = userRepository.findById(Long.parseLong(id)).orElseThrow();
            userRepository.delete(user);
        }
        return "redirect:/allUsers";
    }

    private String blockUser(String[] checkBox) {
        boolean isFlag = false;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        for (String id : checkBox) {
            user = userRepository.findById(Long.parseLong(id)).orElseThrow();
            user.setStatus(Status.BLOCKED);
            userRepository.save(user);
            if (user.getUsername().equals(auth.getName())) isFlag = true;
        }
        if (isFlag){
            new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
            return "redirect:/login";
        }
        return "redirect:/allUsers";
    }

    private String unlockUser(String[] checkBox) {
        for (String id : checkBox) {
            user = userRepository.findById(Long.parseLong(id)).orElseThrow();
            user.setStatus(Status.ACTIVE);
            userRepository.save(user);
        }
        return "redirect:/allUsers";
    }

//    @RequestMapping(value = "/block", method = RequestMethod.GET)
//    public String userIsActive(HttpServletRequest request,
//                               HttpServletResponse response) {
//
//        String[] check = request.getParameterValues("isChecked");
//        if (check == null) {
//            return "redirect:/allUsers";
//        } else {
//            for (int i = 0; i < check.length; i++) {
//                Long id = Long.parseLong(check[i]);
//                user = userRepository.getById(id);
//                user.setStatus(Status.BLOCKED);
//                userRepository.save(user);
//            }
//        }
//        return "redirect:/allUsers";
//    }
//
//    @GetMapping("/users/delete/{id}")
//    public String delete(@PathVariable(value = "id") Long id) {
//        user = userRepository.findById(id).orElseThrow();
//        userRepository.delete(user);
//        return "redirect:/allUsers";
//    }
//
//    @RequestMapping(value = "/users/blocked/{id}", method = RequestMethod.GET)
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response,
//                             @PathVariable(value = "id") Long id) {
//        user = userRepository.findById(id).orElseThrow();
//        user.setStatus(Status.BLOCKED);
//        userRepository.save(user);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (user.getUsername().equals(auth.getName())) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//            return "redirect:/login?logout";
//        }
//        return "redirect:/allUsers";
//    }
//
//    @GetMapping("/users/blockUnblock/{id}")
//    public String blockUnblock(@PathVariable(value = "id") Long id) {
//        user = userRepository.findById(id).orElseThrow();
//        user.setStatus(user.getStatus().equals(Status.BLOCKED) ? Status.ACTIVE : Status.BLOCKED);
//        userRepository.save(user);
//        return "redirect:/allUsers";
//    }
}