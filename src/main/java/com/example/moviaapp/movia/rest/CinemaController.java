package com.example.moviaapp.movia.rest;

import com.example.moviaapp.movia.entity.Cinema;
import com.example.moviaapp.movia.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cinema")
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Cinema>> getCinemaList() {
        return cinemaService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Cinema> getCinema(@PathVariable("id") int id) {
        return cinemaService.findById(id);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Cinema> save(@PathVariable("id") int id, @RequestBody Cinema cinema) {
        return cinemaService.save(id, cinema);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Cinema> update(@PathVariable("id") int id, @RequestBody Cinema cinema) {
        return cinemaService.save(id, cinema);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        return cinemaService.deleteById(id);

    }
}
