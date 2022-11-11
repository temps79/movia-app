package com.example.moviaapp.movia.service.impl;

import com.example.moviaapp.movia.entity.Film;
import com.example.moviaapp.movia.entity.Seat;
import com.example.moviaapp.movia.repository.SeatRepository;
import com.example.moviaapp.movia.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;

    @Override
    public ResponseEntity<List<Seat>> findAll() {
        return ResponseEntity.ok(seatRepository.findAll());
    }

    @Override
    public ResponseEntity<Seat> findById(int id) {
        return ResponseEntity.ok(
                seatRepository.findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found film by id:" + id))
        );
    }

    @Override
    public ResponseEntity<Void> deleteById(int id) {
        try {
            seatRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<Seat> save(int id, Seat entity) {
        try {
            Seat saveSeat = seatRepository.save(entity);
            return ResponseEntity.ok(saveSeat);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public Set<Seat> createSeats(int row, int column) {
        Set<Seat> seats = new TreeSet<>(Comparator.comparingInt(Seat::getRow).thenComparingInt(Seat::getColumn));
        for (int i = 1; i < row + 1; i++) {
            for (int j = 1; j < column + 1; j++) {
                Seat seat = new Seat(-1, i, j, null);
                seats.add(seat);
            }
        }
        return seats.stream().map(seat -> save(-1, seat).getBody())
                .collect(Collectors.toCollection(TreeSet::new));
    }
}
