package com.Utech.journalApp.controller;

import com.Utech.journalApp.Entity.UserEntity;
import com.Utech.journalApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }
    @PostMapping
    public void addUser(@RequestBody UserEntity newUser) {
        userService.saveUser(newUser);
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserEntity userEntity) {
        UserEntity userindb = userService.findById(userEntity.getId());
        if (userindb!=null)
        {
            userindb.setName(userEntity.getName());

            userindb.setPassword(userEntity.getPassword());

        } else {
            return ResponseEntity.notFound().build();
        }

        userService.saveUser(userEntity); //it will be updated
        return ResponseEntity.ok().build();
    }
}
