package com.example.bms.services;

import com.example.bms.models.*;
import com.example.bms.repositories.ShowRepository;
import com.example.bms.repositories.ShowSeatRepository;
import com.example.bms.repositories.TicketRepository;
import com.example.bms.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RedisBookingService implements BookingService{

    private final CacheService cacheService;
    private final ShowSeatRepository showSeatRepository;
    private final TicketRepository ticketRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;

    public RedisBookingService(
            RedisService cacheService,
            ShowSeatRepository showSeatRepository,
            TicketRepository ticketRepository,
            ShowRepository showRepository,
            UserRepository userRepository

    ) {
        this.cacheService = cacheService;
        this.showSeatRepository = showSeatRepository;
        this.ticketRepository = ticketRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;

    }

    @Override
    public boolean blockSeats(long showId, List<Long> seatIds, long userId) {

        System.out.println("Printing cache after logic");
        cacheService.getAllKeysAndValues();

        // 1. We will first of all check if the seats are available or not
        // a. Check if the seats are not booked already

        List<ShowSeat> showSeats = showSeatRepository.findAllByShowIdAndSeatIdIn(showId, seatIds);

        System.out.println("Printing showSeats");
        showSeats.forEach(showSeat -> {
            System.out.println(showSeat.getId() + " " + showSeat.getStatus());
        });

        for(ShowSeat seat: showSeats) {
            if(seat.getStatus().equals(ShowSeatStatus.BOOKED)) {
                return false;
            }
        }

        // b. Check if the seats are not locked already
        for(ShowSeat seat: showSeats) {
            String status = (String) cacheService.get("seatId-"+seat.getId()+"-userId-"+userId);

            // if status is not null and the user who has locked the seat is the same user then continue

            if(status != null) {
                return false;
            }
        }


        // 2. If all the seats are available then we will block the seats in redis - seatId - userId
        for(ShowSeat seat: showSeats) {
            cacheService.set("seatId-"+seat.getId()+"-userId-"+userId, "LOCKED");
        }

        System.out.println("Printing cache after logic");
        cacheService.getAllKeysAndValues();

        return true;
    }

    @Override
    @Transactional
    public Optional<Ticket> bookTicket(long showId, List<Long> showSeatIds, long userId) {
        // 1. In redis check if the user has lock for all the seats that they are trying to book

        for(Long seatId: showSeatIds) {
            String status = (String) cacheService.get("seatId-"+seatId+"-userId-"+userId);
            System.out.println("status: "+status + " seatId: "+seatId + " userId: "+userId);
            if(status == null) {
                return Optional.empty();
            }
        }

        System.out.println("All seats available");

        User user = userRepository.findById(userId).get();
        Show show = showRepository.findById(showId).get();

        // Create a new ticket

        // Go to all the rows of show_Seats and update the status to booked and update ticket id in one query

        Ticket t = createTicketAndBookSeat(show, user, showSeatIds);


        System.out.println("ticket created");
        return Optional.of(t);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Ticket createTicketAndBookSeat(Show show, User user, List<Long> seatIds) {

        Ticket ticket = new Ticket();
//        ticket.setAmount(100L);
        ticket.setUser(user);
        ticket.setShow(show);
        ticket.setStatus(TicketStatus.BOOKED);

        ticket = ticketRepository.save(ticket);

        showSeatRepository.bookShowSeatsBulk(seatIds, ticket);

        return ticket;

    }

    @Override
    public void clearAllSeatLocks() {
        cacheService.deleteAll();
    }
}
