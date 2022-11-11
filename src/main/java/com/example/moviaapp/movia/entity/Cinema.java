package com.example.moviaapp.movia.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Cinema {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String city;
    @Column
    private String address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CinemaRoom> cinemaRoomList;

    public void addCinemaRoomList(CinemaRoom cinemaRoom){
        cinemaRoomList.add(cinemaRoom);
    }
    public void removeCinemaRoomList(CinemaRoom cinemaRoom){
        cinemaRoomList.remove(cinemaRoom);
    }
}
