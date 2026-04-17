package com.ashish.ticketing.modules.admin.service;

import com.ashish.ticketing.common.exception.NotFoundException;
import com.ashish.ticketing.modules.admin.dto.response.BookingStatsResponse;
import com.ashish.ticketing.modules.admin.dto.response.DashboardResponse;
import com.ashish.ticketing.modules.admin.dto.response.EventStatsResponse;
import com.ashish.ticketing.modules.booking.enums.BookingStatus;
import com.ashish.ticketing.modules.booking.repository.BookingRepository;
import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import com.ashish.ticketing.modules.event.repository.EventRepository;
import com.ashish.ticketing.modules.user.repository.UserRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminService {

	private final BookingRepository bookingRepository;
	private final EventRepository eventRepository;
	private final UserRepository userRepository;

	public AdminService(
			BookingRepository bookingRepository,
			EventRepository eventRepository,
			UserRepository userRepository
	) {
		this.bookingRepository = bookingRepository;
		this.eventRepository = eventRepository;
		this.userRepository = userRepository;
	}

	public DashboardResponse getDashboardSummary() {
		long totalUsers = userRepository.count();
		long totalEvents = eventRepository.count();
		long totalBookings = bookingRepository.count();
		BigDecimal totalRevenue = nullSafe(bookingRepository.sumAmountByStatus(BookingStatus.CONFIRMED));
		long activeEvents = eventRepository.countByStatus(EventStatus.PUBLISHED);
		long soldOutEvents = eventRepository.countSoldOutByStatus(EventStatus.PUBLISHED);

		Instant startOfToday = LocalDate.now(ZoneId.systemDefault())
			.atStartOfDay(ZoneId.systemDefault())
			.toInstant();
		long todayBookings = bookingRepository.countByCreatedAtGreaterThanEqual(startOfToday);
		BigDecimal todayRevenue = nullSafe(bookingRepository.sumAmountByStatusSince(BookingStatus.CONFIRMED, startOfToday));

		return new DashboardResponse(
			totalUsers,
			totalEvents,
			totalBookings,
			totalRevenue,
			activeEvents,
			soldOutEvents,
			todayBookings,
			todayRevenue
		);
	}

	public List<EventStatsResponse> getEventStats() {
		List<Event> events = eventRepository.findAll();
		if (events.isEmpty()) {
			return Collections.emptyList();
		}

		Map<Long, BookingRepository.EventBookingAggregation> statsByEventId = bookingRepository
			.aggregateByEventForStatus(BookingStatus.CONFIRMED)
			.stream()
			.collect(Collectors.toMap(BookingRepository.EventBookingAggregation::getEventId, Function.identity()));

		return events.stream()
			.map(event -> {
				BookingRepository.EventBookingAggregation agg = statsByEventId.get(event.getId());
				long bookingCount = agg == null || agg.getBookingCount() == null ? 0L : agg.getBookingCount();
				BigDecimal revenue = agg == null ? BigDecimal.ZERO : nullSafe(agg.getTotalRevenue());

				int totalTickets = event.getTotalTickets() == null ? 0 : event.getTotalTickets();
				int availableTickets = event.getAvailableTickets() == null ? 0 : event.getAvailableTickets();
				int soldTickets = Math.max(totalTickets - availableTickets, 0);

				return new EventStatsResponse(
					event.getId(),
					event.getTitle(),
					totalTickets,
					availableTickets,
					soldTickets,
					revenue,
					bookingCount
				);
			})
			.toList();
	}

	public BookingStatsResponse getBookingStats() {
		long totalBookings = bookingRepository.count();
		long confirmedBookings = bookingRepository.countByStatus(BookingStatus.CONFIRMED);
		long cancelledBookings = bookingRepository.countByStatus(BookingStatus.CANCELLED);
		long failedBookings = bookingRepository.countByStatus(BookingStatus.FAILED);
		BigDecimal totalRevenue = nullSafe(bookingRepository.sumAmountByStatus(BookingStatus.CONFIRMED));

		return new BookingStatsResponse(
			totalBookings,
			confirmedBookings,
			cancelledBookings,
			failedBookings,
			totalRevenue
		);
	}

	public EventStatsResponse getEventStatsById(Long eventId) {
		Event event = eventRepository.findById(eventId)
			.orElseThrow(() -> new NotFoundException("Event not found"));

		long bookingCount = bookingRepository.countByEventIdAndStatus(eventId, BookingStatus.CONFIRMED);
		BigDecimal revenue = nullSafe(bookingRepository.sumAmountByEventIdAndStatus(eventId, BookingStatus.CONFIRMED));

		int totalTickets = event.getTotalTickets() == null ? 0 : event.getTotalTickets();
		int availableTickets = event.getAvailableTickets() == null ? 0 : event.getAvailableTickets();
		int soldTickets = Math.max(totalTickets - availableTickets, 0);

		return new EventStatsResponse(
			event.getId(),
			event.getTitle(),
			totalTickets,
			availableTickets,
			soldTickets,
			revenue,
			bookingCount
		);
	}

	private BigDecimal nullSafe(BigDecimal value) {
		return value == null ? BigDecimal.ZERO : value;
	}

}
