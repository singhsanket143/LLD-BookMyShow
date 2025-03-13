package com.example.bms.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Ticket extends BaseModel{

    private int amount;

    @ManyToOne
    private User user;

    @ManyToOne
    private Show show;

    @OneToMany(mappedBy = "ticket")
    private List<ShowSeat> showSeat;

    @Enumerated(EnumType.ORDINAL)
    private TicketStatus status;
}
