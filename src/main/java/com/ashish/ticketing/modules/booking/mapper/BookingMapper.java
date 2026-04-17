package com.ashish.ticketing.modules.booking.mapper;

import com.ashish.ticketing.modules.booking.dto.response.BookingDetailsResponse;
import com.ashish.ticketing.modules.booking.dto.response.BookingResponse;
import com.ashish.ticketing.modules.booking.dto.response.BookingSummaryResponse;
import com.ashish.ticketing.modules.booking.entity.Booking;
import com.ashish.ticketing.modules.booking.enums.BookingStatus;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class BookingMapper {

	public Booking toEntity(Long userId, Long eventId, int quantity, BigDecimal amount, String bookingNumber) {
		Booking booking = new Booking();
		booking.setUserId(userId);
		booking.setEventId(eventId);
		booking.setQuantity(quantity);
		booking.setAmount(amount);
		booking.setBookingNumber(bookingNumber);
		booking.setStatus(BookingStatus.PENDING);
		return booking;
	}

	public BookingResponse toResponse(Booking booking) {
		return new BookingResponse(
			booking.getBookingNumber(),
			booking.getStatus().name(),
			booking.getAmount()
		);
	}

	public BookingDetailsResponse toDetailsResponse(Booking booking) {
		return new BookingDetailsResponse(
			booking.getBookingNumber(),
			booking.getEventId(),
			booking.getUserId(),
			booking.getQuantity(),
			booking.getAmount(),
			booking.getStatus().name(),
			booking.getCreatedAt()
		);
	}

	public BookingSummaryResponse toSummaryResponse(Booking booking) {
		return new BookingSummaryResponse(
			booking.getBookingNumber(),
			booking.getEventId(),
			booking.getStatus().name(),
			booking.getAmount()
		);
	}

}
