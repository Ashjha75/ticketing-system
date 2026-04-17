package com.ashish.ticketing.modules.event.service;

import com.ashish.ticketing.modules.event.dto.request.CreateEventRequest;
import com.ashish.ticketing.modules.event.dto.request.EventSearchRequest;
import com.ashish.ticketing.modules.event.dto.request.UpdateEventRequest;
import com.ashish.ticketing.modules.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class EventService {

	private final EventCommandService eventCommandService;
	private final EventQueryService eventQueryService;

	public EventService(EventCommandService eventCommandService, EventQueryService eventQueryService) {
		this.eventCommandService = eventCommandService;
		this.eventQueryService = eventQueryService;
	}

	public Event createEvent(CreateEventRequest request) {
		return eventCommandService.createEvent(request);
	}

	public Event updateEvent(Long eventId, UpdateEventRequest request) {
		return eventCommandService.updateEvent(eventId, request);
	}

	public void publishEvent(Long eventId) {
		eventCommandService.publishEvent(eventId);
	}

	public void cancelEvent(Long eventId) {
		eventCommandService.cancelEvent(eventId);
	}

	public Event getEventById(Long id) {
		return eventQueryService.getEventById(id);
	}

	public Event getPublishedEventById(Long id) {
		return eventQueryService.getPublishedEventById(id);
	}

	public Page<Event> searchEvents(EventSearchRequest request) {
		return eventQueryService.searchEvents(request);
	}
}

