package com.example.moviaapp.auth.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

public interface AuthService {
    ResponseEntity<Integer> getCurrentAccessLevel(User user);
}
