package com.ashish.ticketing.modules.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationRetryScheduler {

    @Scheduled(fixedRateString = "${scheduler.notification.retry.rate:300000}")
    public void retryFailedNotifications() {
        // TODO: Implement notification retry logic
    }
}
