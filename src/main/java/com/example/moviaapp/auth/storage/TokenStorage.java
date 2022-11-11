package com.example.moviaapp.auth.storage;

import org.springframework.security.core.Authentication;

public interface TokenStorage {
    void addToken(String token, Authentication authentication);

    void removeToken(String token);
}
