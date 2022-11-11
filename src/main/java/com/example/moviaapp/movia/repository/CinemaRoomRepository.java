package com.example.moviaapp.movia.repository;


import com.example.moviaapp.movia.entity.CinemaRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRoomRepository extends CrudRepository<CinemaRoom,Integer> {
    List<CinemaRoom> findAll();
}
