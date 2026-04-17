package com.ashish.ticketing.modules.booking.service;

import com.ashish.ticketing.common.exception.BusinessException;
import com.ashish.ticketing.common.exception.UnauthorizedException;
import com.ashish.ticketing.common.util.IdGenerator;
import com.ashish.ticketing.modules.booking.dto.request.CreateBookingRequest;
import com.ashish.ticketing.modules.booking.dto.response.BookingDetailsResponse;
import com.ashish.ticketing.modules.booking.dto.response.BookingResponse;
import com.ashish.ticketing.modules.booking.dto.response.BookingSummaryResponse;
import com.ashish.ticketing.modules.booking.entity.Booking;
import com.ashish.ticketing.modules.booking.enums.BookingStatus;
import com.ashish.ticketing.modules.booking.mapper.BookingMapper;
import com.ashish.ticketing.modules.booking.repository.BookingRepository;
import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.service.EventQueryService;
import com.ashish.ticketing.modules.inventory.service.InventoryService;
import com.ashish.ticketing.modules.notification.service.NotificationService;
import com.ashish.ticketing.modules.user.entity.User;
import com.ashish.ticketing.modules.user.service.UserService;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookingService {

	private final UserService userService;
	private final EventQueryService eventQueryService;
	private final InventoryService inventoryService;
	private final BookingRepository bookingRepository;
	private final BookingValidationService bookingValidationService;
	private final BookingLifecycleService bookingLifecycleService;
	private final BookingQueryService bookingQueryService;
	private final BookingMapper bookingMapper;
	private final NotificationService notificationService;

	public BookingService(
			UserService userService,
			EventQueryService eventQueryService,
			InventoryService inventoryService,
			BookingRepository bookingRepository,
			BookingValidationService bookingValidationService,
			BookingLifecycleService bookingLifecycleService,
			BookingQueryService bookingQueryService,
			BookingMapper bookingMapper,
			NotificationService notificationService
	) {
		this.userService = userService;
		this.eventQueryService = eventQueryService;
		this.inventoryService = inventoryService;
		this.bookingRepository = bookingRepository;
		this.bookingValidationService = bookingValidationService;
		this.bookingLifecycleService = bookingLifecycleService;
		this.bookingQueryService = bookingQueryService;
		this.bookingMapper = bookingMapper;
		this.notificationService = notificationService;
	}

	@Transactional
	public BookingResponse createBooking(Long userId, CreateBookingRequest request) {
		User user = userService.getActiveUserById(userId);
		Event event = eventQueryService.getPublishedEventById(request.getEventId());

		bookingValidationService.validateBooking(user, event, request.getQuantity());
		inventoryService.reserveTickets(event.getId(), request.getQuantity());

		BigDecimal amount = event.getTicketPrice().multiply(BigDecimal.valueOf(request.getQuantity()));
		String bookingNumber = IdGenerator.generate("BK");
		Booking booking = bookingMapper.toEntity(user.getId(), event.getId(), request.getQuantity(), amount, bookingNumber);
		bookingLifecycleService.confirmBooking(booking);

		Booking savedBooking = bookingRepository.save(booking);
		notificationService.sendBookingConfirmation(user, savedBooking);
		return bookingMapper.toResponse(savedBooking);
	}

	@Transactional
	public void cancelBooking(Long userId, Long bookingId) {
		Booking booking = bookingQueryService.getBookingById(bookingId);

		if (!booking.getUserId().equals(userId)) {
			throw new UnauthorizedException("You are not authorized to cancel this booking");
		}

		if (booking.getStatus() != BookingStatus.CONFIRMED) {
			throw new BusinessException("Only confirmed bookings can be cancelled", "INVALID_BOOKING_STATUS");
		}

		inventoryService.releaseTickets(booking.getEventId(), booking.getQuantity());
		bookingLifecycleService.cancelBooking(booking);
		bookingRepository.save(booking);
	}

	@Transactional(readOnly = true)
	public List<BookingSummaryResponse> getUserBookings(Long userId) {
		return bookingQueryService.getUserBookings(userId)
			.stream()
			.map(bookingMapper::toSummaryResponse)
			.toList();
	}

	@Transactional(readOnly = true)
	public BookingDetailsResponse getBookingDetails(Long userId, Long bookingId) {
		Booking booking = bookingQueryService.getBookingById(bookingId);
		if (!booking.getUserId().equals(userId)) {
			throw new UnauthorizedException("You are not authorized to access this booking");
		}
		return bookingMapper.toDetailsResponse(booking);
	}

}
