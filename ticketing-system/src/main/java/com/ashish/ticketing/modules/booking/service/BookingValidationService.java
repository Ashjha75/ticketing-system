package com.ashish.ticketing.modules.booking.service;

import com.ashish.ticketing.common.exception.BusinessException;
import com.ashish.ticketing.common.exception.ConflictException;
import com.ashish.ticketing.modules.booking.repository.BookingRepository;
import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import com.ashish.ticketing.modules.user.entity.User;
import com.ashish.ticketing.modules.user.enums.UserStatus;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class BookingValidationService {

	private final BookingRepository bookingRepository;

	public BookingValidationService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public void validateBooking(User user, Event event, int quantity) {
		if (quantity <= 0) {
			throw new BusinessException("Quantity must be greater than zero", "INVALID_BOOKING_QUANTITY");
		}

		if (user.getStatus() != UserStatus.ACTIVE) {
			throw new BusinessException("User is not active", "USER_NOT_ACTIVE");
		}

		if (event.getStatus() != EventStatus.PUBLISHED) {
			throw new BusinessException("Event is not available for booking", "EVENT_NOT_PUBLISHED");
		}

		Instant now = Instant.now();
		if (event.getBookingStartTime() == null || event.getBookingEndTime() == null
			|| now.isBefore(event.getBookingStartTime()) || now.isAfter(event.getBookingEndTime())) {
			throw new BusinessException("Booking window is closed", "BOOKING_WINDOW_CLOSED");
		}

		if (bookingRepository.existsByUserIdAndEventId(user.getId(), event.getId())) {
			throw new ConflictException("You already have a booking for this event");
		}
	}

}
