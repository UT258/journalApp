package com.Utech.journalApp.controller;

import com.Utech.journalApp.Entity.UserEntity;
import com.Utech.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController //it is a controller rest controller means it is a rest api
//main function of rest controller is to return json data by annotating the class with @RestController
// @RequestMapping("/healthcheck") //this is the url for the health check
@RequestMapping("/public") //this is the base url for public endpoints
//this is the public controller which will be used to check the health of the application

public class publicController {
     @Autowired
    private UserService userService;

    @PostMapping
    public void addUser(@RequestBody UserEntity newUser) {

        userService.saveUser(newUser);
    }
}
