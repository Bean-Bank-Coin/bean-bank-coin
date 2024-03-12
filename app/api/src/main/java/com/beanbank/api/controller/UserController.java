package com.beanbank.api.controller;

import com.beanbank.api.service.UserService;
import com.models.User;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User> getUserByUsername(@PathVariable(value = "username") String username) {
        User foundUser = userService.findUserByUsername(username);

        if (foundUser == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(foundUser);
    }
}
