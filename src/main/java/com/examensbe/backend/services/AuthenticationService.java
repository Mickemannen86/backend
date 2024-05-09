package com.examensbe.backend.services;

import com.examensbe.backend.Jwt.JwtAuthenticationRequest;
import com.examensbe.backend.Jwt.JwtAuthenticationResponse;
import com.examensbe.backend.config.AppPasswordConfig;
import com.examensbe.backend.models.user.RegisterRequest;
import com.examensbe.backend.models.user.Roles;
import com.examensbe.backend.models.user.UserEntity;
import com.examensbe.backend.repositories.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AppPasswordConfig appPasswordConfig;

    public AuthenticationService(UserRepository userRepository, AppPasswordConfig appPasswordConfig) {
        this.userRepository = userRepository;
        this.appPasswordConfig = appPasswordConfig;

    }

    public UserEntity register(RegisterRequest request) {

        UserEntity newUser = new UserEntity();

        newUser.setUsername(request.username());
        newUser.setPassword(appPasswordConfig.bCryptPasswordEncoder().encode(request.password()));
        newUser.setRole(Roles.valueOf(request.roles()));   //Roles.ADMIN,// dynamisk lösning
        newUser.setAccountNonExpired(true); //newUser.isAccountNonExpired()
        newUser.setAccountNonLocked(true); // newUser.isAccountNonLocked()
        newUser.setAccountEnabled(true); // newUser.isEnabled()
        newUser.setCredentialsNonExpired(true); // newUser.isCredentialsNonExpired()
        UserEntity savedUser = userRepository.save(newUser);

        return savedUser;
    }

    public JwtAuthenticationResponse authenticate(JwtAuthenticationRequest request) {
        return null;
    }

}

    /* registrera ny användare
        UserEntity userEntity = new UserEntity(
                request.username(),
                appPasswordConfig.bCryptPasswordEncoder().encode(request.password()),
                newUser.getRole(),   //Roles.ADMIN,// dynamisk lösning
                newUser.isAccountNonExpired(),
                newUser.isAccountNonLocked(),
                newUser.isEnabled(),
                newUser.isCredentialsNonExpired()
        );

     */

