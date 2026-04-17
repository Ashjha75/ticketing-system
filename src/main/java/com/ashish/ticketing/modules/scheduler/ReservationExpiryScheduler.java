package com.ashish.ticketing.modules.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationExpiryScheduler {

    @Scheduled(fixedRateString = "${scheduler.reservation.expiry.rate:60000}")
    public void processExpiredReservations() {
        // TODO: Implement reservation expiration logic
    }
}
