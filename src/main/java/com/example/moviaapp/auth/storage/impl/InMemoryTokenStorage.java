package com.example.moviaapp.auth.storage.impl;

import com.example.moviaapp.auth.storage.TokenStorage;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryTokenStorage implements TokenStorage {
    private final Map<String,Authentication> storage=new HashMap<>();
    @Override
    public void addToken(String token, Authentication authentication) {
        storage.put(token,authentication);
    }

    @Override
    public void removeToken(String token) {
        storage.remove(token);
    }
}
