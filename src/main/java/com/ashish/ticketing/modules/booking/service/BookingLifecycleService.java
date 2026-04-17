package com.ashish.ticketing.modules.booking.service;

import com.ashish.ticketing.modules.booking.entity.Booking;
import com.ashish.ticketing.modules.booking.enums.BookingStatus;
import org.springframework.stereotype.Service;

@Service
public class BookingLifecycleService {

	public void confirmBooking(Booking booking) {
		booking.setStatus(BookingStatus.CONFIRMED);
	}

	public void cancelBooking(Booking booking) {
		booking.setStatus(BookingStatus.CANCELLED);
	}

	public void failBooking(Booking booking) {
		booking.setStatus(BookingStatus.FAILED);
	}

	public void expireBooking(Booking booking) {
		booking.setStatus(BookingStatus.EXPIRED);
	}

}
