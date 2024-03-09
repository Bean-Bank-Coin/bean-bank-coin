package com.beanbank.api.controller;

import com.beanbank.api.model.User;
import com.beanbank.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable(value = "username") String username, @RequestBody User user) {
        return userService.getUserByUsername(username);
    }
}
