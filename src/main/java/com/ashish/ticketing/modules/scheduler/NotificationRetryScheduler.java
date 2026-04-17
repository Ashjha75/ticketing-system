package com.ashish.ticketing.modules.scheduler;

import com.ashish.ticketing.modules.notification.entity.NotificationLog;
import com.ashish.ticketing.modules.notification.repository.NotificationLogRepository;
import com.ashish.ticketing.modules.notification.service.NotificationService;
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
public class NotificationRetryScheduler {

  private static final Logger logger = LoggerFactory.getLogger(NotificationRetryScheduler.class);
  private static final String STATUS_FAILED = "FAILED";
  private static final String STATUS_SUCCESS = "SUCCESS";

  private final NotificationLogRepository notificationLogRepository;
  private final NotificationService notificationService;

  @Value("${scheduler.notification.max-retry:3}")
  private int maxRetry;

  @Value("${scheduler.notification.batch-size:20}")
  private int batchSize;

  public NotificationRetryScheduler(
      NotificationLogRepository notificationLogRepository,
      NotificationService notificationService
  ) {
    this.notificationLogRepository = notificationLogRepository;
    this.notificationService = notificationService;
  }

  @Scheduled(fixedDelayString = "${scheduler.notification.fixed-delay:120000}")
  @Transactional
    public void retryFailedNotifications() {
    Page<NotificationLog> page = notificationLogRepository.findByStatusAndRetryCountLessThanOrderByCreatedAtAsc(
      STATUS_FAILED,
      Math.max(maxRetry, 1),
      PageRequest.of(0, Math.max(batchSize, 1))
    );

    for (NotificationLog log : page.getContent()) {
      if (!STATUS_FAILED.equalsIgnoreCase(log.getStatus())) {
        continue;
      }

      try {
        boolean success = notificationService.retryFailedNotification(log);
        if (success) {
          log.setStatus(STATUS_SUCCESS);
          log.setSentAt(Instant.now());
          log.setMessage("Notification sent successfully on retry");
        } else {
          log.setRetryCount(log.getRetryCount() + 1);
          log.setMessage("Retry attempt failed");
        }
        notificationLogRepository.save(log);
      } catch (Exception ex) {
        logger.error("Failed to process notification retry for logId={}", log.getId(), ex);
      }
    }
    }
}
