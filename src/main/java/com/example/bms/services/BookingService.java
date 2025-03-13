package com.example.bms.services;

import com.example.bms.models.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookingService {

    boolean blockSeats(long showId, List<Long> seatIds, long userId);

    Optional<Ticket> bookTicket(long showId, List<Long> seatIds, long userId);

    void clearAllSeatLocks();
}
