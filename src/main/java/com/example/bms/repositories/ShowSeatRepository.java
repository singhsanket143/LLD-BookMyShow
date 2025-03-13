package com.example.bms.repositories;

import com.example.bms.models.ShowSeat;
import com.example.bms.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findAllByShowId(long showId);

    List<ShowSeat> findAllByShowIdAndSeatIdIn(Long showId, List<Long> seatIds);

    @Modifying
    @Query("UPDATE ShowSeat s SET s.ticket = :ticket, s.status = 1 WHERE s.id IN :ids")
    int bookShowSeatsBulk(@Param("ids") List<Long> ids, @Param("ticket") Ticket ticket);
}
