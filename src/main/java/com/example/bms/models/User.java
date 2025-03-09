package com.example.bms.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class User extends BaseModel{

        private String name;

        private String email;

        @OneToMany(mappedBy = "user")
        private List<Ticket> tickets;
}
