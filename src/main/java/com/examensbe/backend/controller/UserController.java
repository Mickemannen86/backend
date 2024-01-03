package com.examensbe.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/* Skapa API */

@RestController
//@RequestMapping("/api/v1/test1")
public class UserController {

    @GetMapping()
    public ResponseEntity<String> helloMikey() {
        return ResponseEntity.ok("Hello Mikey, API connection succed");


    }



}
