package com.example.moviaapp.movia.service.impl;

import com.example.moviaapp.movia.entity.Film;
import com.example.moviaapp.movia.repository.FilmRepository;
import com.example.moviaapp.movia.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;

    @Override
    public ResponseEntity<List<Film>> findAll() {
        return ResponseEntity.ok(filmRepository.findAll());
    }

    @Override
    public ResponseEntity<Film> findById(int id) {
        return ResponseEntity.ok(
                filmRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found film by id:" + id))
        );
    }

    @Override
    public ResponseEntity<Void> deleteById(int id) {
        try {
            filmRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

    }

    @Override
    public ResponseEntity<Film> save(int id, Film film) {
        try {
            Film saveFilm = filmRepository.save(film);
            return ResponseEntity.ok(saveFilm);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
