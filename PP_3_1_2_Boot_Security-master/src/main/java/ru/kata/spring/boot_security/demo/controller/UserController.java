package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.security.UserDetails;
import ru.kata.spring.boot_security.demo.services.UserDetailServ;
import ru.kata.spring.boot_security.demo.services.UserService;

/**
 * @author Karina.
 */
@Controller
@RequestMapping()
public class UserController {

    private final UserService userService;

    private final UserDetailServ userDetailServ;


    @Autowired
    public UserController(UserService userService, UserDetailServ userDetailServ) {
        this.userService = userService;
        this.userDetailServ = userDetailServ;
    }



    @GetMapping("/user")
    public String showUserInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        model.addAttribute("user", userDetails.getUser());
        return "users/show";
    }




}
