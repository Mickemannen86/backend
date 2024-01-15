package com.examensbe.backend.models;


import java.util.UUID;


public class User {

    // instans variablar
    private Long id;

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    private String location;

    // Constructor
    public User(Long id) {

        this.id = id;

    }
    public User() {}

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() { return firstname; }

    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }

    public void setLastname(String lastname) { this.lastname = lastname; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }
}
