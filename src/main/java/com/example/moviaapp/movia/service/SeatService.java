package com.example.moviaapp.movia.service;

import com.example.moviaapp.movia.entity.Seat;

import java.util.Set;

public interface SeatService extends GenericCrudService<Seat> {
    Set<Seat> createSeats(int row, int column);
}
