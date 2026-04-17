package com.ashish.ticketing.modules.booking.repository;

import com.ashish.ticketing.modules.booking.entity.Booking;
import com.ashish.ticketing.modules.booking.enums.BookingStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    interface EventBookingAggregation {
        Long getEventId();
        Long getBookingCount();
        BigDecimal getTotalRevenue();
    }

    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    List<Booking> findByUserId(Long userId);

    Optional<Booking> findByBookingNumber(String bookingNumber);

    long countByStatus(BookingStatus status);

    long countByCreatedAtGreaterThanEqual(Instant start);

    Page<Booking> findByStatusAndCreatedAtBefore(BookingStatus status, Instant createdAt, Pageable pageable);

    @Query("select coalesce(sum(b.amount), 0) from Booking b where b.status = :status")
    BigDecimal sumAmountByStatus(@Param("status") BookingStatus status);

    @Query("""
        select coalesce(sum(b.amount), 0)
        from Booking b
        where b.status = :status and b.createdAt >= :start
        """)
    BigDecimal sumAmountByStatusSince(@Param("status") BookingStatus status, @Param("start") Instant start);

    @Query("""
        select b.eventId as eventId,
               count(b) as bookingCount,
               coalesce(sum(b.amount), 0) as totalRevenue
        from Booking b
        where b.status = :status
        group by b.eventId
        """)
    List<EventBookingAggregation> aggregateByEventForStatus(@Param("status") BookingStatus status);

    long countByEventIdAndStatus(Long eventId, BookingStatus status);

    @Query("select coalesce(sum(b.amount), 0) from Booking b where b.eventId = :eventId and b.status = :status")
    BigDecimal sumAmountByEventIdAndStatus(@Param("eventId") Long eventId, @Param("status") BookingStatus status);
}
