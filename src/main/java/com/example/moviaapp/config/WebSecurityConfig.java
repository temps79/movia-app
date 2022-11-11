package com.example.moviaapp.config;

import com.example.moviaapp.auth.DynamicRememberMeServices;
import com.example.moviaapp.auth.storage.TokenStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final TokenStorage tokenStorage;

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable().headers().frameOptions().disable()
                .disable()
                .formLogin()
                .and()
                .rememberMe()
                .rememberMeServices(new DynamicRememberMeServices("remember-me-param-based", userDetailsService(), tokenStorage))
                .tokenValiditySeconds(60 * 10)
                .and()
                .build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        PasswordEncoder bCryptPasswordEncoder = getEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password(bCryptPasswordEncoder.encode("123"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("user2")
                .password(bCryptPasswordEncoder.encode("123"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password(bCryptPasswordEncoder.encode("123"))
                .roles("USER", "ADMIN")
                .build());
        return manager;
    }
}
