package com.ashish.ticketing.modules.notification.service;

import com.ashish.ticketing.modules.booking.entity.Booking;
import com.ashish.ticketing.modules.booking.repository.BookingRepository;
import com.ashish.ticketing.modules.notification.dto.response.NotificationResponse;
import com.ashish.ticketing.modules.notification.entity.NotificationLog;
import com.ashish.ticketing.modules.notification.repository.NotificationLogRepository;
import com.ashish.ticketing.modules.user.entity.User;
import com.ashish.ticketing.modules.user.repository.UserRepository;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);
	private static final String TYPE_EMAIL = "EMAIL";
	private static final String STATUS_SUCCESS = "SUCCESS";
	private static final String STATUS_FAILED = "FAILED";

	private final EmailService emailService;
	private final TemplateService templateService;
	private final NotificationLogRepository notificationLogRepository;
	private final UserRepository userRepository;
	private final BookingRepository bookingRepository;

	public NotificationService(
			EmailService emailService,
			TemplateService templateService,
			NotificationLogRepository notificationLogRepository,
			UserRepository userRepository,
			BookingRepository bookingRepository
	) {
		this.emailService = emailService;
		this.templateService = templateService;
		this.notificationLogRepository = notificationLogRepository;
		this.userRepository = userRepository;
		this.bookingRepository = bookingRepository;
	}

	public NotificationResponse sendBookingConfirmation(User user, Booking booking) {
		String subject = "Booking Confirmation - " + booking.getBookingNumber();
		String body = templateService.buildBookingConfirmation(user, booking);

		try {
			emailService.sendEmail(user.getEmail(), subject, body);
			saveLog(user.getId(), booking.getId(), STATUS_SUCCESS, "Booking confirmation email sent", Instant.now());
			return new NotificationResponse(STATUS_SUCCESS, "Booking confirmation notification sent");
		} catch (Exception ex) {
			logger.error("Failed to send booking confirmation for bookingNumber={}", booking.getBookingNumber(), ex);
			saveLog(user.getId(), booking.getId(), STATUS_FAILED, ex.getMessage(), null);
			return new NotificationResponse(STATUS_FAILED, "Booking confirmation notification failed");
		}
	}

	public NotificationResponse sendFailureNotification(User user, String reason) {
		try {
			emailService.sendEmail(user.getEmail(), "Booking Notification Failure", reason);
			saveLog(user.getId(), 0L, STATUS_SUCCESS, "Failure notification sent", Instant.now());
			return new NotificationResponse(STATUS_SUCCESS, "Failure notification sent");
		} catch (Exception ex) {
			logger.error("Failed to send failure notification for userId={}", user.getId(), ex);
			saveLog(user.getId(), 0L, STATUS_FAILED, ex.getMessage(), null);
			return new NotificationResponse(STATUS_FAILED, "Failure notification could not be sent");
		}
	}

	public boolean retryFailedNotification(NotificationLog log) {
		try {
			if (log == null || log.getUserId() == null || log.getBookingId() == null || log.getBookingId() <= 0) {
				return false;
			}

			User user = userRepository.findById(log.getUserId()).orElse(null);
			Booking booking = bookingRepository.findById(log.getBookingId()).orElse(null);
			if (user == null || booking == null) {
				return false;
			}

			String subject = "Booking Confirmation - " + booking.getBookingNumber();
			String body = templateService.buildBookingConfirmation(user, booking);
			emailService.sendEmail(user.getEmail(), subject, body);
			return true;
		} catch (Exception ex) {
			logger.error("Retry failed for notificationLogId={}", log != null ? log.getId() : null, ex);
			return false;
		}
	}

	private void saveLog(Long userId, Long bookingId, String status, String message, Instant sentAt) {
		try {
			NotificationLog log = new NotificationLog();
			log.setUserId(userId);
			log.setBookingId(bookingId);
			log.setType(TYPE_EMAIL);
			log.setStatus(status);
			log.setMessage(message == null || message.isBlank() ? "N/A" : message);
			log.setRetryCount(0);
			log.setSentAt(sentAt);
			notificationLogRepository.save(log);
		} catch (Exception logEx) {
			logger.error("Failed to persist notification log for userId={} bookingId={}", userId, bookingId, logEx);
		}
	}

}
