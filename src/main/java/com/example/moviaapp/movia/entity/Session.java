package com.example.moviaapp.movia.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Session {
    @Id
    @GeneratedValue
    private int id;

    @OneToOne(cascade = CascadeType.DETACH)
    private Film film;
    @OneToOne(cascade = CascadeType.DETACH)
    private CinemaRoom cinemaRoom;
    @Column
    private LocalDateTime date;

    @Column
    private int price;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Seat> seats;


    @JsonIgnore
    public void setFilm(Film film) {
        this.film = film;
    }

    @JsonSetter("filmId")
    public void setFilm(int filmId) {
        this.film = new Film();
        this.film.setId(filmId);
    }

    @JsonGetter("film")
    public Film getFilm() {
        return film;
    }

    @JsonGetter("cinemaRoom")
    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    @JsonIgnore
    public void setCinemaRoom(CinemaRoom cinemaRoom) {
        this.cinemaRoom = cinemaRoom;
    }

    @JsonSetter("cinemaRoomId")
    public void setCinemaRoom(int cinemaRoomId) {
        this.cinemaRoom = new CinemaRoom();
        this.cinemaRoom.setId(cinemaRoomId);
    }

    @JsonSetter("date")
    public void setDate(Long date) {
        this.date = LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneId.of("Europe/Moscow"));
    }
    @JsonIgnore
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    @JsonGetter("date")
    public Long getDate() {
        return date.atZone(ZoneId.of("Europe/Moscow")).toInstant().toEpochMilli();
    }
}
