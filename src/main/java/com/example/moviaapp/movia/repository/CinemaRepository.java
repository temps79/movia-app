package com.example.moviaapp.movia.repository;

import com.example.moviaapp.movia.entity.Cinema;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends CrudRepository<Cinema,Integer> {
    List<Cinema> findAll();
}
