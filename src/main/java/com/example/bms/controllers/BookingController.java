package com.example.bms.controllers;


import com.example.bms.dto.BlockSeatsRequestDto;
import com.example.bms.dto.BookSeatRequestDto;
import com.example.bms.models.Ticket;
import com.example.bms.services.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/block")
    public boolean blockSeats(@RequestBody BlockSeatsRequestDto blockSeatsRequestDto) {
        return bookingService.blockSeats(blockSeatsRequestDto.getShowId(), blockSeatsRequestDto.getSeatId(), blockSeatsRequestDto.getUserId());
    }

    @DeleteMapping
    public void clearAllSeatLocks() {
        bookingService.clearAllSeatLocks();
    }

    @PostMapping("/confirm")
    public Optional<Ticket> confirmBooking(@RequestBody BookSeatRequestDto bookSeatRequestDto) {
        return bookingService.bookTicket(
                bookSeatRequestDto.getShowId(),
                bookSeatRequestDto.getSeatId(),
                bookSeatRequestDto.getUserId()
        );
    }
}
