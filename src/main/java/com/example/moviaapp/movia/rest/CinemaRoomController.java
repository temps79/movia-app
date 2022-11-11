package com.example.moviaapp.movia.rest;


import com.example.moviaapp.movia.entity.CinemaRoom;
import com.example.moviaapp.movia.service.CinemaRoomFacade;
import com.example.moviaapp.movia.service.CinemaRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:63342", maxAge = 3600)
@RestController
@RequestMapping("api/v1/cinema_room")
@RequiredArgsConstructor
public class CinemaRoomController {
    private final CinemaRoomService cinemaRoomService;
    private final CinemaRoomFacade cinemaRoomFacade;

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<CinemaRoom>> getCinemaList() {
        return cinemaRoomService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<CinemaRoom> getCinema(@PathVariable("id") int id) {
        return cinemaRoomService.findById(id);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CinemaRoom> save(@PathVariable("id") int id, @RequestBody CinemaRoom cinemaRoom) {
        return cinemaRoomFacade.save(cinemaRoom);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<CinemaRoom> update(@PathVariable("id") int id, @RequestBody CinemaRoom cinemaRoom) {
        return cinemaRoomService.save(id, cinemaRoom);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        return cinemaRoomFacade.delete(id);

    }
}
