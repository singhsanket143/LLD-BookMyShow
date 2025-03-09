package com.example.bms.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Movie extends BaseModel {

    private String name;
    private String poster;

    @OneToMany(mappedBy = "movie")
    private List<Show> shows;
}
