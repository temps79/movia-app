package com.example.moviaapp.movia.repository;

import com.example.moviaapp.movia.entity.Cinema;
import com.example.moviaapp.movia.entity.Film;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends CrudRepository<Film,Integer> {
    List<Film> findAll();
}
