package com.example.moviaapp.movia.repository;


import com.example.moviaapp.movia.entity.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<Session,Integer> {
    List<Session> findAll();
    List<Session> findAllByCinemaRoomIdOrderByDateAsc(int cinemaRoomId);
    List<Session> findAllByDateBetween(LocalDateTime todayString, LocalDateTime sevenDaysBeforeString);
}