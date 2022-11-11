package com.example.moviaapp.movia.service.impl;

import com.example.moviaapp.movia.entity.Cinema;
import com.example.moviaapp.movia.entity.CinemaRoom;
import com.example.moviaapp.movia.service.CinemaRoomFacade;
import com.example.moviaapp.movia.service.CinemaRoomService;
import com.example.moviaapp.movia.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CinemaRoomFacadeImpl implements CinemaRoomFacade {
    private final CinemaService cinemaService;
    private final CinemaRoomService cinemaRoomService;

    @Override
    @Transactional
    public ResponseEntity<CinemaRoom> save(CinemaRoom cinemaRoom) {
        CinemaRoom saveCinemaRoom = cinemaRoomService.save(-1, cinemaRoom).getBody();
        Cinema cinema = cinemaService.findById(cinemaRoom.getCinema().getId()).getBody();
        if (cinema != null && saveCinemaRoom != null) {
            saveCinemaRoom.setCinema(cinema);
            cinema.addCinemaRoomList(saveCinemaRoom);
            cinemaService.save(cinema.getId(), cinema);
            return ResponseEntity.ok(saveCinemaRoom);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Void> delete(int id) {
        CinemaRoom cinemaRoom = cinemaRoomService.findById(id).getBody();
        if (cinemaRoom != null) {
            Cinema cinema = cinemaService.findById(cinemaRoom.getCinema().getId()).getBody();
            if(cinema!=null) {
                cinema.removeCinemaRoomList(cinemaRoom);
                cinemaService.save(cinema.getId(), cinema);
                cinemaRoomService.deleteById(id);
                return ResponseEntity.ok().build();
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
