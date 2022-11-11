package com.example.moviaapp.movia.service.impl;

import com.example.moviaapp.movia.entity.CinemaRoom;
import com.example.moviaapp.movia.entity.Seat;
import com.example.moviaapp.movia.repository.CinemaRoomRepository;
import com.example.moviaapp.movia.service.CinemaRoomService;
import com.example.moviaapp.movia.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CinemaRoomServiceImpl implements CinemaRoomService {
    private final CinemaRoomRepository cinemaRoomRepository;


    /**
     * Поиск всех кинозалов
     *
     * @return список кинозалов
     */
    @Override
    public ResponseEntity<List<CinemaRoom>> findAll() {
        return ResponseEntity.ok(cinemaRoomRepository.findAll());
    }

    /**
     * Поиск кинозала по ИД
     *
     * @param id уникальный номер кинозала
     * @return 200:зал
     */
    @Override
    public ResponseEntity<CinemaRoom> findById(int id) {
        return ResponseEntity.ok(
                cinemaRoomRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found cinema room by id:" + id))
        );
    }

    /**
     * Удаление кинозала по ИД
     *
     * @param id уникальный номер кинозала
     * @return 200:ОК
     */
    @Override
    public ResponseEntity<Void> deleteById(int id) {
        try {
            cinemaRoomRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Сохранение/обновление кинозала
     *
     * @param id         ИД
     * @param cinemaRoom кинозал
     * @return Сохраненный/Обновленный кинозал
     */
    @Override
    public ResponseEntity<CinemaRoom> save(int id, CinemaRoom cinemaRoom) {
        try {
            CinemaRoom saveCinemaRoom = cinemaRoomRepository.save(cinemaRoom);
            return ResponseEntity.ok(saveCinemaRoom);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }

}
