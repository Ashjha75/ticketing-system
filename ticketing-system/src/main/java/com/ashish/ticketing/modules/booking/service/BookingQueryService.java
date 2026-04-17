package com.ashish.ticketing.modules.booking.service;

import com.ashish.ticketing.common.exception.NotFoundException;
import com.ashish.ticketing.modules.booking.entity.Booking;
import com.ashish.ticketing.modules.booking.repository.BookingRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BookingQueryService {

	private final BookingRepository bookingRepository;

	public BookingQueryService(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public Booking getBookingById(Long id) {
		return bookingRepository.findById(id)
			.orElseThrow(() -> new NotFoundException("Booking not found"));
	}

	public List<Booking> getUserBookings(Long userId) {
		return bookingRepository.findByUserId(userId);
	}

	public Booking getBookingByNumber(String bookingNumber) {
		return bookingRepository.findByBookingNumber(bookingNumber)
			.orElseThrow(() -> new NotFoundException("Booking not found"));
	}

}
