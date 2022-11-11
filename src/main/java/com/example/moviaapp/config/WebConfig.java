package com.example.moviaapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    private final String INDEX_HTML = "index.html";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        List<String> pageUrl = List.of("/", "/cinema/*", "/new_cinema*", "/new_film*", "/film*", "/cinema/*/session/*", "/cinema/*/session/*/*");
        pageUrl.forEach(s -> registry.addViewController(s).setViewName(INDEX_HTML));
    }

}