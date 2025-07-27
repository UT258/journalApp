package com.Utech.journalApp.controller;

import com.Utech.journalApp.Entity.UserEntity;
import com.Utech.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import java.security.Security;
import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity) {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = auth.getName(); // Get the currently authenticated user's name
        UserEntity userindb = userService.findByName(currentUserName);



        if (userindb!=null)
        {

            userindb.setPassword(userEntity.getPassword());

        } else {
            return ResponseEntity.notFound().build();
        }

        userService.saveUser(userindb); //it will be updated
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
