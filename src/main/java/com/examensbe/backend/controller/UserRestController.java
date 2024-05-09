package com.examensbe.backend.controller;


import com.examensbe.backend.config.AppPasswordConfig;
import com.examensbe.backend.models.user.Roles;
import com.examensbe.backend.models.user.UserEntity;
import com.examensbe.backend.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private final AppPasswordConfig appPasswordConfig;
    private final UserRepository userRepository;

    @Autowired
    public UserRestController(AppPasswordConfig appPasswordConfig, UserRepository userRepository) {
        this.appPasswordConfig = appPasswordConfig;
        this.userRepository = userRepository;
    }

    @PostMapping("/user") // http://localhost:8080/api/user funkar om allt i body fylls i!
    public ResponseEntity<UserEntity> createNewUser(@RequestBody UserEntity newUser) {

        UserEntity userEntity = new UserEntity(
                newUser.getUsername(),
                appPasswordConfig.bCryptPasswordEncoder().encode(newUser.getPassword()),
                newUser.getRole(),   //Roles.ADMIN,// dynamisk lösning
                newUser.isAccountNonExpired(),
                newUser.isAccountNonLocked(),
                newUser.isEnabled(),
                newUser.isCredentialsNonExpired()
        );

        return new ResponseEntity<>(userRepository.save(userEntity), HttpStatus.CREATED);
    }

}

