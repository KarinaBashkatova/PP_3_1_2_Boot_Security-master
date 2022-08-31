package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.services.UserDetailServ;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;

/**
 * @author Karina Bashkatova.
 */
@Controller
@RequestMapping()
public class AdminController {

    private final UserService userService;

    private final UserDetailServ userDetailServ;

    @Autowired
    public AdminController(UserService userService, UserDetailServ userDetailServ) {
        this.userService = userService;
        this.userDetailServ = userDetailServ;
    }


    @GetMapping("/admin")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.showAllUsers());
        return "users/all"  ;
    }

//    @GetMapping("/{id}")
 //   public String show(@PathVariable("id") int id, Model model) {
 //       model.addAttribute("user", userService.showUser(id));
//        return "users/show";
 //   }

    @GetMapping("admin/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "users/new";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

            return "users/newError";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("admin/{id}/edit")
    @Secured("ADMIN")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.showUser(id));
        return "users/edit";
    }

    @PatchMapping("admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "users/editError";
        }

        userService.update(id, user);
        return  "redirect:/admin/users/{id}";
    }

    @DeleteMapping("admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/users/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "users/show";
    }

}