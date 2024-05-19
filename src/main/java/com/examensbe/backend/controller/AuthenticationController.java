package com.examensbe.backend.controller;


import com.examensbe.backend.Jwt.JwtAuthenticationResponse;
import com.examensbe.backend.Jwt.JwtTokenGenerator;
import com.examensbe.backend.exceptions.UserAlreadyExistException;
import com.examensbe.backend.models.user.*;
import com.examensbe.backend.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final JwtTokenGenerator jwtTokenGenerator;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService service;

    public UserAlreadyExistException userAlreadyExistsException;


    @Autowired
    public AuthenticationController(JwtTokenGenerator jwtTokenGenerator, AuthenticationManager authenticationManager, AuthenticationService service) {
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.authenticationManager = authenticationManager;
        this.service = service;
    }

    @PostMapping("/debug") // localhost:8080/auth/login
    public ResponseEntity<LoginRequest> debugEndpoint(@RequestBody String requestBody) {
        // Print the received request body to the console for debugging
        System.out.println("Received request body: " + requestBody);

        // Return a simple response acknowledging the receipt of the request
        return ResponseEntity.ok().body(new LoginRequest("USERNAME", "PASSWORD"));
    }

    @PostMapping("/login") // localhost:8080/auth/login
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Authenticate the user based on the credentials provided in the LoginRequest
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );

        // When successful login
        System.out.println(
                "-- Success login! --" +
                        "\nUsername: " + authentication.getName() +
                        "\nAuthenticated: " + authentication.isAuthenticated() +
                        "\nAuthorities: " + authentication.getAuthorities() +
                        "\nCredentials: " + authentication.getCredentials() +
                        "\nDetails: " + authentication.getDetails() +
                        "\nAuthentication: " + authentication + "\n"
        );

        // Generate JWT token using the authenticated Authentication object
        String token = jwtTokenGenerator.generateToken(authentication, loginRequest.username());

        // Return the token in the response
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @PostMapping("/register") // localhost:8080/auth/register
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        //DTO ResReq siginRequest aka ResReQ response = new ResReq();´outUsers = UserEntity
        try {

        UserEntity newUser = service.register(request);

        System.out.println("Ny användare: " + newUser.getUsername() +
                        "\n" + newUser.getPassword() +
                        "\n" + newUser.getRole() +
                        "\n" + newUser.isAccountNonExpired() +
                        "\n" + newUser.isCredentialsNonExpired() +
                        "\n" + newUser.isAccountNonLocked() +
                        "\n" + newUser.isEnabled() +
                        "\n" + newUser.getAuthorities()
                );

        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>("Användaren finns redan", HttpStatus.CONFLICT);
        }
    }
}
