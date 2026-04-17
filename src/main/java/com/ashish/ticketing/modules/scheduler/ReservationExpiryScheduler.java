package com.ashish.ticketing.modules.scheduler;

import com.ashish.ticketing.modules.booking.entity.Booking;
import com.ashish.ticketing.modules.booking.enums.BookingStatus;
import com.ashish.ticketing.modules.booking.repository.BookingRepository;
import com.ashish.ticketing.modules.booking.service.BookingLifecycleService;
import com.ashish.ticketing.modules.inventory.service.InventoryService;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ReservationExpiryScheduler {

  private static final Logger logger = LoggerFactory.getLogger(ReservationExpiryScheduler.class);

  private final BookingRepository bookingRepository;
  private final InventoryService inventoryService;
  private final BookingLifecycleService bookingLifecycleService;

  @Value("${scheduler.reservation.expiry-time-ms:900000}")
  private long expiryTimeMs;

  @Value("${scheduler.reservation.batch-size:50}")
  private int batchSize;

  public ReservationExpiryScheduler(
      BookingRepository bookingRepository,
      InventoryService inventoryService,
      BookingLifecycleService bookingLifecycleService
  ) {
    this.bookingRepository = bookingRepository;
    this.inventoryService = inventoryService;
    this.bookingLifecycleService = bookingLifecycleService;
  }

  @Scheduled(fixedDelayString = "${scheduler.reservation.fixed-delay:60000}")
  @Transactional
  public void expireBookings() {
    Instant cutoff = Instant.now().minusMillis(Math.max(expiryTimeMs, 60000L));
    Page<Booking> page = bookingRepository.findByStatusAndCreatedAtBefore(
      BookingStatus.PENDING,
      cutoff,
      PageRequest.of(0, Math.max(batchSize, 1))
    );

    for (Booking booking : page.getContent()) {
      try {
        inventoryService.releaseTickets(booking.getEventId(), booking.getQuantity());
        bookingLifecycleService.expireBooking(booking);
        bookingRepository.save(booking);
      } catch (Exception ex) {
        logger.error("Failed to expire bookingId={}", booking.getId(), ex);
      }
    }
    }
}
