package com.example.moviaapp.movia.repository;

import com.example.moviaapp.movia.entity.Seat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends CrudRepository<Seat, Integer> {
    List<Seat> findAll();
}
