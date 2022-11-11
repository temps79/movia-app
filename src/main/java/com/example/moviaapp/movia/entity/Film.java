package com.example.moviaapp.movia.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table
public class Film {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    @JsonProperty("isNew")
    private boolean isNew;

    @Column(length = 1500)
    private String description;

    @Column(length = 5000)
    private String image;

    @Column
    private int time;

}
