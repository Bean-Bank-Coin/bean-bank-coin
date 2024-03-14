package com.beanbank.api.controller;

import com.beanbank.api.service.UserService;
import com.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ResponseEntity<User> getUserByUsername(@RequestBody User user) {
        User foundUser = userService.findUserByUsername(user.getUsername());

        if (foundUser != null)
            return ResponseEntity.ok(foundUser);

        foundUser = userService.findUserByEmail(user.getEmail());

        if (foundUser != null)
            return ResponseEntity.ok(foundUser);

        return ResponseEntity.notFound().build();
    }
}
