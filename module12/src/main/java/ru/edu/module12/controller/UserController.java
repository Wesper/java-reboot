package ru.edu.module12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.module12.entity.UserEntity;
import ru.edu.module12.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        List<UserEntity> users = service.getAllUsers();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("showAllUsers");
        return modelAndView;
    }

}