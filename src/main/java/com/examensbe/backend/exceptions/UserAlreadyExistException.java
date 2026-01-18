package com.examensbe.backend.exceptions;

import org.springframework.stereotype.Component;

@Component
public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
    }
}