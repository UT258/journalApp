package com.Utech.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController //it is a controller rest controller means it is a rest api
//main function of rest controller is to return json data by annotating the class with @RestController
// @RequestMapping("/healthcheck") //this is the url for the health check
public class healthcheck {
    @GetMapping("/health-check") //this is the url for the health check
    public String healthCheck() {
        return "OK";
    }
}
