package com.ashish.ticketing.modules.booking.repository;

import com.ashish.ticketing.modules.booking.entity.Booking;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    List<Booking> findByUserId(Long userId);

    Optional<Booking> findByBookingNumber(String bookingNumber);
}
