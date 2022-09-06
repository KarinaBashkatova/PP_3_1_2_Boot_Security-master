package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserDetailServ;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

/**
 * @author Karina Bashkatova.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final UserDetailServ userDetailServ;

    private final RoleServiceImpl roleServiceImpl;

    @Autowired
    public AdminController(UserService userService, UserDetailServ userDetailServ, RoleServiceImpl roleServiceImpl) {
        this.userService = userService;
        this.userDetailServ = userDetailServ;
        this.roleServiceImpl = roleServiceImpl;
    }


    @GetMapping()
    public String allUsers(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userDetailServ.loadUserByUsername(name).getUser();
        model.addAttribute("currentUser", user);
        model.addAttribute("users", userService.showAllUsers());
        model.addAttribute("roles", roleServiceImpl.getRoleList());

        return "users/all"  ;
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute ("user") User user, Model model) {
        model.addAttribute("roles", roleServiceImpl.getRoleList());

        return "users/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/newError";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("{id}/edit")
    @Secured("ADMIN")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.showUser(id));
        return "users/edit";
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "users/editError";
        }

        userService.update(id, user);
        return  "redirect:/admin";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("users/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "users/show";
    }

}