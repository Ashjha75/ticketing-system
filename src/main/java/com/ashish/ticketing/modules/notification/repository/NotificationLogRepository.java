package com.ashish.ticketing.modules.notification.repository;

import com.ashish.ticketing.modules.notification.entity.NotificationLog;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {

    List<NotificationLog> findByStatus(String status);

    List<NotificationLog> findByUserId(Long userId);
}

