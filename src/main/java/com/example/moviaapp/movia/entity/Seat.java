package com.example.moviaapp.movia.entity;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Comparator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Seat implements Comparable<Seat> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int column;
    @Column(name = "\"ROW\"")
    private int row;
    @Column
    private String userName;

    @Override
    public int compareTo(Seat o) {
        return Comparator.comparingInt(Seat::getRow)
                .thenComparingInt(Seat::getColumn)
                .compare(this, o);
    }
}
