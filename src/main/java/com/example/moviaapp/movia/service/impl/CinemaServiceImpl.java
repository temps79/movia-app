package com.example.moviaapp.movia.service.impl;

import com.example.moviaapp.movia.entity.Cinema;
import com.example.moviaapp.movia.repository.CinemaRepository;
import com.example.moviaapp.movia.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;


    @Override
    public ResponseEntity<List<Cinema>> findAll() {
        return ResponseEntity.ok(cinemaRepository.findAll());
    }

    @Override
    public ResponseEntity<Cinema> findById(int id) {
        return ResponseEntity.ok(
                cinemaRepository.findById(id)
                        .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found cinema by id:"+id))
        );
    }

    @Override
    public ResponseEntity<Void> deleteById(int id) {
        try{
            cinemaRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }

    }

    @Override
    public ResponseEntity<Cinema> save(int id, Cinema cinema) {
        try{
            Cinema saveCinema=cinemaRepository.save(cinema);
            return ResponseEntity.ok(saveCinema);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
