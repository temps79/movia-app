package com.example.moviaapp.movia.service.impl;

import com.example.moviaapp.movia.entity.Film;
import com.example.moviaapp.movia.entity.Seat;
import com.example.moviaapp.movia.entity.Session;
import com.example.moviaapp.movia.repository.SessionRepository;
import com.example.moviaapp.movia.service.SeatService;
import com.example.moviaapp.movia.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;


    @Override
    public ResponseEntity<List<Session>> findAll() {
        return ResponseEntity.ok(sessionRepository.findAll());
    }

    @Override
    public ResponseEntity<Session> findById(int id) {
        return ResponseEntity.ok(
                sessionRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found session by id:" + id))
        );
    }

    @Override
    public ResponseEntity<Void> deleteById(int id) {
        try {
            sessionRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Session> save(int id, Session session) {
        try {
            Session saveSession = sessionRepository.save(session);
            return ResponseEntity.ok(saveSession);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<List<Session>> findByCinemaRoomId(int cinemaRoomId) {
        return ResponseEntity.ok(sessionRepository.findAllByCinemaRoomIdOrderByDateAsc(cinemaRoomId));
    }

    @Override
    public ResponseEntity<List<Session>> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<Session> sessions=sessionRepository.findAllByDateBetween(startDate,endDate);
        return ResponseEntity.ok(sessions);
    }
}
