package com.example.bms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BlockSeatsRequestDto {
    private long showId;
    private long userId;
    private List<Long> seatId;

}
