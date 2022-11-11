package com.example.moviaapp.auth;

import com.example.moviaapp.auth.storage.TokenStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;


@Slf4j
public class DynamicRememberMeServices extends TokenBasedRememberMeServices {
    private final TokenStorage tokenStorage;
    public final static String PARAM_BASED_KEY = "remember-me-param-based";
    public final static String REMEMBER_ME_KEY = "token";


    public DynamicRememberMeServices(String key, UserDetailsService userDetailsService, TokenStorage tokenStorage) {
        super(key, userDetailsService);
        super.setParameter(PARAM_BASED_KEY);
        super.setCookieName(REMEMBER_ME_KEY);
        this.tokenStorage = tokenStorage;
    }

    @Override
    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {
        if ("on".equalsIgnoreCase(request.getParameter(PARAM_BASED_KEY))) {
            log.debug("param based request");
            return super.rememberMeRequested(request, parameter);
        }
        log.debug("always remember me");
        return true;
    }

    @Override
    public void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        super.onLoginSuccess(request, response, successfulAuthentication);
        var token = Arrays.stream(response.getHeaders("Set-Cookie").toString().split(";"))
                .filter(s -> s.contains("token"))
                .findFirst().orElse("")
                .replaceAll("\\[", "");
        tokenStorage.addToken(token, successfulAuthentication);
    }
}
