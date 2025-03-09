package com.example.bms.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Auditorium extends BaseModel{

    private String name;

    private int capacity;

    @ManyToOne
    private Theatre theatre;

    @OneToMany(mappedBy = "auditorium")
    private List<Seat> seats;


    @OneToMany(mappedBy = "auditorium")
    private List<Show> shows;


}
