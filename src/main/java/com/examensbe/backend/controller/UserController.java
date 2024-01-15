package com.examensbe.backend.controller;

import com.examensbe.backend.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/* Skapa API */

@RestController
//@RequestMapping("/api/v1/test1")
public class UserController {

    @GetMapping("/")
    public ResponseEntity<String> helloMikey() {



        return ResponseEntity.ok("Hello Mikey, API connection succed");
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = null;
        user = userService.getUserById(id);
        return userService.getAllUsers();
    }

    @PostMapping("/users/{id}")
    public ResponseEntity<User> createUserById(@PathVariable("id") Long id) {
        User user = null;
        user = userService.save();
        return user;
    }



}
