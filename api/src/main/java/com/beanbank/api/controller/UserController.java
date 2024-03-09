package com.beanbank.api.controller;

import com.beanbank.api.model.User;
import com.beanbank.api.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    final
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public User getUserByUsername(@PathVariable(value = "username") String username) {
        return userService.getUserByUsername(username);
    }
}
