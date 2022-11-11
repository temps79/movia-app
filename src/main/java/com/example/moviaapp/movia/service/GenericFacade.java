package com.example.moviaapp.movia.service;

import org.springframework.http.ResponseEntity;

public interface GenericFacade<T> {
    ResponseEntity<T> save(T t);

    ResponseEntity<Void> delete(int id);
}
