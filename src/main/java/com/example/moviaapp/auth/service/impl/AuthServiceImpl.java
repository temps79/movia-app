package com.example.moviaapp.auth.service.impl;

import com.example.moviaapp.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Override
    public ResponseEntity<Integer> getCurrentAccessLevel(User user) {
        int level = 0;
        if (user != null) {
            List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
            if (roles.contains("ROLE_ADMIN")) {
                level = 100;
            } else if(roles.contains("ROLE_USER")) {
                level = 10;
            }

        }
        return ResponseEntity.ok(level);
    }
}
