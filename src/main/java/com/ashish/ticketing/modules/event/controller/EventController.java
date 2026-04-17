package com.ashish.ticketing.modules.event.controller;

import com.ashish.ticketing.common.response.ApiResponse;
import com.ashish.ticketing.modules.event.dto.request.EventSearchRequest;
import com.ashish.ticketing.modules.event.dto.response.EventDetailsResponse;
import com.ashish.ticketing.modules.event.dto.response.EventListResponse;
import com.ashish.ticketing.modules.event.dto.response.EventResponse;
import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.mapper.EventMapper;
import com.ashish.ticketing.modules.event.service.EventService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

	private final EventService eventService;
	private final EventMapper eventMapper;

	public EventController(EventService eventService, EventMapper eventMapper) {
		this.eventService = eventService;
		this.eventMapper = eventMapper;
	}

	@GetMapping
	public ResponseEntity<ApiResponse<EventListResponse>> listEvents(@ModelAttribute EventSearchRequest request) {
		Page<Event> page = eventService.searchEvents(request);
		List<EventResponse> content = page.getContent().stream().map(eventMapper::toResponse).toList();
		EventListResponse payload = new EventListResponse(
			content,
			page.getNumber(),
			page.getSize(),
			page.getTotalElements(),
			page.getTotalPages(),
			page.isLast()
		);
		return ResponseEntity.ok(ApiResponse.success(payload, "Events fetched successfully"));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<EventDetailsResponse>> getEventDetails(@PathVariable Long id) {
		Event event = eventService.getPublishedEventById(id);
		return ResponseEntity.ok(ApiResponse.success(eventMapper.toDetailsResponse(event), "Event details fetched successfully"));
	}
}

