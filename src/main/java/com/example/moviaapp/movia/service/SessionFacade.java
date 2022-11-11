package com.example.moviaapp.movia.service;

import com.example.moviaapp.movia.entity.Seat;
import com.example.moviaapp.movia.entity.Session;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;

public interface SessionFacade extends GenericFacade<Session> {
    ResponseEntity<Session> updateSeat(int id, Seat seat, User user);
}
