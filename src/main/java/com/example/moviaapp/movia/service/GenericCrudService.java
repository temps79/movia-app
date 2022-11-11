package com.example.moviaapp.movia.service;

import com.example.moviaapp.movia.entity.Cinema;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenericCrudService<T> {
    ResponseEntity<List<T>> findAll();
    ResponseEntity<T> findById(int id);

    ResponseEntity<Void> deleteById(int id);
    ResponseEntity<T> save(int id,T entity);
}
