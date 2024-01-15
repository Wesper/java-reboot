package com.example.module11.controller;

import com.example.module11.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.module11.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @RequestMapping(path = "/get", method = RequestMethod.GET)
    public ModelAndView getAllUsers() {
        ModelAndView modelAndView = new ModelAndView();
        List<UserEntity> users = service.getAllUsers();
        modelAndView.addObject("users", users);
        modelAndView.setViewName("showAllUsers");
        return modelAndView;
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public ModelAndView getAddUsersForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addUserForm");
        return modelAndView;
    }

    @RequestMapping(path = "/edit/{userId}", method = RequestMethod.GET)
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

    @RequestMapping(path = "/delete/{userId}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable("userId") String userId) {
        service.deleteById(Long.valueOf(userId));
        return "redirect:../get";
    }

    @RequestMapping(path = "/edit", method = RequestMethod.POST)
    public String editUser(@RequestParam HashMap<String, String> req) {
        long id = Long.parseLong(req.get("id"));
        String name = req.get("name");
        int age = Integer.parseInt(req.get("age"));
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setName(name);
        user.setAge(age);
        service.updateById(user);
        return "redirect:get";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addUser(@RequestParam HashMap<String, String> req) {
        String name = req.get("name");
        int age = Integer.parseInt(req.get("age"));
        UserEntity user = new UserEntity();
        user.setName(name);
        user.setAge(age);
        service.createUser(user);
        return "redirect:get";
    }
}
