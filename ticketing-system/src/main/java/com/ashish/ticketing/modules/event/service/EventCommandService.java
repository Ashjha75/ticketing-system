package com.ashish.ticketing.modules.event.service;

import com.ashish.ticketing.common.exception.BusinessException;
import com.ashish.ticketing.common.exception.ConflictException;
import com.ashish.ticketing.common.exception.NotFoundException;
import com.ashish.ticketing.modules.event.dto.request.CreateEventRequest;
import com.ashish.ticketing.modules.event.dto.request.UpdateEventRequest;
import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import com.ashish.ticketing.modules.event.mapper.EventMapper;
import com.ashish.ticketing.modules.event.repository.EventRepository;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventCommandService {

	private final EventRepository eventRepository;
	private final EventMapper eventMapper;

	public EventCommandService(EventRepository eventRepository, EventMapper eventMapper) {
		this.eventRepository = eventRepository;
		this.eventMapper = eventMapper;
	}

	public Event createEvent(CreateEventRequest request) {
		Event event = eventMapper.toEntity(request);
		event.setAvailableTickets(event.getTotalTickets());
		event.setStatus(EventStatus.DRAFT);

		validateEvent(event);
		return eventRepository.save(event);
	}

	public Event updateEvent(Long eventId, UpdateEventRequest request) {
		Event event = getEventOrThrow(eventId);

		int soldTickets = event.getTotalTickets() - event.getAvailableTickets();
		if (request.getTotalTickets() != null && request.getTotalTickets() < soldTickets) {
			throw new ConflictException("Total tickets cannot be less than already sold tickets");
		}

		eventMapper.updateEntity(event, request);

		if (request.getTotalTickets() != null) {
			event.setAvailableTickets(request.getTotalTickets() - soldTickets);
		}

		validateEvent(event);
		return eventRepository.save(event);
	}

	public void publishEvent(Long eventId) {
		Event event = getEventOrThrow(eventId);
		validateEvent(event);

		if (event.getStatus() == EventStatus.CANCELLED || event.getStatus() == EventStatus.COMPLETED) {
			throw new BusinessException("Only draft or published events can be published", "INVALID_EVENT_STATUS");
		}

		event.setStatus(EventStatus.PUBLISHED);
		eventRepository.save(event);
	}

	public void cancelEvent(Long eventId) {
		Event event = getEventOrThrow(eventId);
		event.setStatus(EventStatus.CANCELLED);
		eventRepository.save(event);
	}

	private Event getEventOrThrow(Long eventId) {
		return eventRepository.findById(eventId)
			.orElseThrow(() -> new NotFoundException("Event not found"));
	}

	private void validateEvent(Event event) {
		if (event.getBookingStartTime() == null || event.getBookingEndTime() == null
				|| !event.getBookingStartTime().isBefore(event.getBookingEndTime())) {
			throw new BusinessException("Booking start time must be before booking end time", "INVALID_BOOKING_WINDOW");
		}

		if (event.getStartTime() == null || event.getEndTime() == null
				|| !event.getStartTime().isBefore(event.getEndTime())) {
			throw new BusinessException("Event start time must be before event end time", "INVALID_EVENT_WINDOW");
		}

		if (!event.getStartTime().isAfter(event.getBookingEndTime())) {
			throw new BusinessException("Event start time must be after booking end time", "INVALID_BOOKING_FLOW");
		}

		if (event.getTotalTickets() == null || event.getTotalTickets() <= 0) {
			throw new BusinessException("Total tickets must be greater than zero", "INVALID_TOTAL_TICKETS");
		}

		if (event.getAvailableTickets() == null || event.getAvailableTickets() < 0) {
			throw new BusinessException("Available tickets cannot be negative", "INVALID_AVAILABLE_TICKETS");
		}

		if (event.getAvailableTickets() > event.getTotalTickets()) {
			throw new BusinessException("Available tickets cannot exceed total tickets", "INVALID_TICKET_INVENTORY");
		}

		if (event.getTicketPrice() == null || event.getTicketPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new BusinessException("Ticket price must be greater than zero", "INVALID_TICKET_PRICE");
		}
	}
}

