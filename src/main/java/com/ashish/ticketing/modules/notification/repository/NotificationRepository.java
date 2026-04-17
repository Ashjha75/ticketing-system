package com.ashish.ticketing.modules.notification.repository;

import com.ashish.ticketing.modules.notification.entity.NotificationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationLog, Long> {
}
