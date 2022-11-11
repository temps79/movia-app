package com.example.moviaapp.movia.service;

import com.example.moviaapp.movia.entity.Session;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface SessionService extends GenericCrudService<Session> {
    ResponseEntity<List<Session>> findByCinemaRoomId(int cinemaRoomId);
    ResponseEntity<List<Session>> findAllByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
