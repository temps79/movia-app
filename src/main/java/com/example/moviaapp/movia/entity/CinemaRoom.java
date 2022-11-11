package com.example.moviaapp.movia.entity;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.StringJoiner;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class CinemaRoom {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private int countRow;
    @Column
    private int countColumn;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    private Cinema cinema;

    @JsonIgnore
    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }
    @JsonSetter("cinemaId")
    public void setCinema(int cinemaId) {
        this.cinema = new Cinema();
        this.cinema.setId(cinemaId);
    }
    @JsonIgnore
    public Cinema getCinema() {
        return cinema;
    }
    @JsonGetter("cinemaId")
    public int getCinemaAsInt() {
        return cinema.getId();
    }
}
