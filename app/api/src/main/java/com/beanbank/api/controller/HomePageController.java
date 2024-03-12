package com.beanbank.api.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HomePageController {

    @GetMapping
    public Object Server_announcement() {
        // To confirm if the server is up and running
        Map<String, String> object = new HashMap<>();
        object.put("message", "Welcome to BeanBank API");
        return object;
    }

}
