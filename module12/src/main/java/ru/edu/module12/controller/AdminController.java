package ru.edu.module12.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.edu.module12.entity.UserEntity;
import ru.edu.module12.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService service;

    @GetMapping(path = "/users")
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        List<UserEntity> users = service.getAllUsers();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("showAllUsersAdmin");
        return modelAndView;
    }

    @GetMapping(path = "/add")
    public ModelAndView getAddUsersForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addUserForm");
        return modelAndView;
    }

    @GetMapping(path = "/edit/{userId}")
    public ModelAndView getEditUsersForm(@PathVariable("userId") String userId) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<UserEntity> user = service.getUserById(Long.valueOf(userId));
        if (user.isPresent()) {
            modelAndView.addObject("id", userId);
            modelAndView.addObject("name", user.get().getName());
            modelAndView.addObject("age", user.get().getAge());
        } else {
            modelAndView.addObject("id", "unknown user");
            modelAndView.addObject("name", "unknown user");
            modelAndView.addObject("age", "unknown user");
        }
        modelAndView.setViewName("editUserForm");
        return modelAndView;
    }

    @GetMapping(path = "/delete/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        service.deleteById(Long.valueOf(userId));
        return "redirect:../users";
    }

    @PostMapping(path = "/edit")
    public String editUser(@RequestParam HashMap<String, String> req) {
        long id = Long.parseLong(req.get("id"));
        String name = req.get("name");
        int age = Integer.parseInt(req.get("age"));
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setName(name);
        user.setAge(age);
        service.updateById(user);
        return "redirect:users";
    }

    @PostMapping(path = "/add")
    public String addUser(@RequestParam HashMap<String, String> req) {
        String name = req.get("name");
        int age = Integer.parseInt(req.get("age"));
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setAge(age);
        service.createUser(user);
        return "redirect:users";
    }
}