package com.example.moviaapp.movia.rest;

import com.example.moviaapp.movia.entity.Seat;
import com.example.moviaapp.movia.entity.Session;
import com.example.moviaapp.movia.service.SessionFacade;
import com.example.moviaapp.movia.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/session")
@RequiredArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final SessionFacade sessionFacade;

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Session>> getCinemaList() {
        return sessionService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Session> getCinema(@PathVariable("id") int id) {
        return sessionService.findById(id);
    }


    @GetMapping("/cinema_room_id/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Session>> getByCinemaRoomId(@PathVariable("id") int id) {
        return sessionService.findByCinemaRoomId(id);
    }

    @PostMapping("/seat/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Session> updateSeat(@PathVariable("id") int id, @RequestBody Seat seat, @AuthenticationPrincipal User user) {
        return sessionFacade.updateSeat(id,seat,user);

    }

    @PostMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Session> save(@PathVariable("id") int id, @RequestBody Session session) {
        return sessionFacade.save(session);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Session> update(@PathVariable("id") int id, @RequestBody Session session) {
        return sessionService.save(id, session);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        return sessionService.deleteById(id);

    }
}
