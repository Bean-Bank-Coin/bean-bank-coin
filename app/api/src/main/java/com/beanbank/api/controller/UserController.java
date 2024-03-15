package com.beanbank.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.beanbank.api.service.UserService;
import com.models.User;

@RestController
@RequestMapping("/api")
public class UserController {
    final UserService userService;
    public static String currentUser = "";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public User createUser(@PathVariable("username") String username) {
        if (!username.equals(currentUser)) {
            return null;
        }

        User foundUser = userService.getUserByUsername(username);

        if (foundUser == null) {
            return userService.createUser(new User(0, username));
        }

        return foundUser;
    }
}
