package com.example.moviaapp.movia.rest;


import com.example.moviaapp.movia.entity.Cinema;
import com.example.moviaapp.movia.entity.Film;
import com.example.moviaapp.movia.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/film")
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Film>> getCinemaList() {
        return filmService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Film> getCinema(@PathVariable("id") int id) {
        return filmService.findById(id);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Film> save(@PathVariable("id") int id, @RequestBody Film film) {
        return filmService.save(id, film);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Film> update(@PathVariable("id") int id, @RequestBody Film film) {
        return filmService.save(id, film);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        return filmService.deleteById(id);

    }
}
