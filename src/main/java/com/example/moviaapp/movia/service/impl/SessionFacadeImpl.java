package com.example.moviaapp.movia.service.impl;

import com.example.moviaapp.movia.entity.*;
import com.example.moviaapp.movia.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionFacadeImpl implements SessionFacade {
    private final SessionService sessionService;
    private final FilmService filmService;
    private final CinemaRoomService cinemaRoomService;
    private final SeatService seatService;

    @Override
    public ResponseEntity<Session> save(Session session) {
        CinemaRoom cinemaRoom = cinemaRoomService.findById(session.getCinemaRoom().getId()).getBody();
        Film film = filmService.findById(session.getFilm().getId()).getBody();
        if (cinemaRoom != null && film != null) {
            session.setCinemaRoom(cinemaRoom);
            session.setFilm(film);
            if (session.getSeats() == null || session.getSeats().size() == 0) {
                Set<Seat> seats = seatService.createSeats(cinemaRoom.getCountRow(), cinemaRoom.getCountColumn());
                session.setSeats(seats);
            }
            ZoneId zoneId = ZoneId.of("Europe/Moscow");
            Instant nowDate = Instant.ofEpochMilli(session.getDate());
            Instant endDate = Instant.ofEpochMilli(session.getDate() + TimeUnit.MINUTES.toMillis(session.getFilm().getTime()));
            List<Session> sessions = sessionService.findByCinemaRoomId(cinemaRoom.getId()).getBody();
            sessions = sessions
                    .stream().filter(
                            session1 -> (session1.getDate() < nowDate.toEpochMilli() && (session1.getDate() + TimeUnit.MINUTES.toMillis(session1.getFilm().getTime())) > nowDate.toEpochMilli()) ||
                                    (session1.getDate() > nowDate.toEpochMilli() &&
                                            session1.getDate() < endDate.toEpochMilli() &&
                                            (session1.getDate() + TimeUnit.MINUTES.toMillis(session1.getFilm().getTime())) > endDate.toEpochMilli()
                                    ) ||
                                    (session1.getDate() < nowDate.toEpochMilli() && (session1.getDate() + TimeUnit.MINUTES.toMillis(session1.getFilm().getTime())) > endDate.toEpochMilli()) ||
                                    (session1.getDate() > nowDate.toEpochMilli() && (session1.getDate() + TimeUnit.MINUTES.toMillis(session1.getFilm().getTime())) < endDate.toEpochMilli())
                    ).collect(Collectors.toList());
            if (sessions.size() == 0) {
                Session saveSession = sessionService.save(-1, session).getBody();
                return ResponseEntity.ok(saveSession);
            } else {
                return ResponseEntity.internalServerError().build();
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Void> delete(int id) {
        return null;
    }

    @Override
    public ResponseEntity<Session> updateSeat(int id, Seat seat, User user) {
        if (seat.getUserName() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"The place is already booked");
        }
        Session session = sessionService.findById(id).getBody();
        seat.setUserName(user.getUsername());
        session.setSeats(session.getSeats().stream().map(seat1 -> {
            if (seat1.getColumn() == seat.getColumn() && seat1.getRow() == seat.getRow() && seat1.getUserName() == null) {
                return seat;
            }
            return seat1;
        }).collect(Collectors.toSet()));
        session = sessionService.save(id, session).getBody();
        return ResponseEntity.ok(session);
    }
}
