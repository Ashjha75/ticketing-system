package com.ashish.ticketing.modules.event.controller;

import com.ashish.ticketing.common.response.ApiResponse;
import com.ashish.ticketing.modules.event.dto.request.CreateEventRequest;
import com.ashish.ticketing.modules.event.dto.request.UpdateEventRequest;
import com.ashish.ticketing.modules.event.dto.response.EventDetailsResponse;
import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.mapper.EventMapper;
import com.ashish.ticketing.modules.event.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/events")
@PreAuthorize("hasRole('ADMIN')")
public class AdminEventController {

	private final EventService eventService;
	private final EventMapper eventMapper;

	public AdminEventController(EventService eventService, EventMapper eventMapper) {
		this.eventService = eventService;
		this.eventMapper = eventMapper;
	}

	@PostMapping
	public ResponseEntity<ApiResponse<EventDetailsResponse>> createEvent(@Valid @RequestBody CreateEventRequest request) {
		Event created = eventService.createEvent(request);
		return ResponseEntity.ok(ApiResponse.success(eventMapper.toDetailsResponse(created), "Event created successfully"));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<EventDetailsResponse>> updateEvent(
			@PathVariable Long id,
			@Valid @RequestBody UpdateEventRequest request
	) {
		Event updated = eventService.updateEvent(id, request);
		return ResponseEntity.ok(ApiResponse.success(eventMapper.toDetailsResponse(updated), "Event updated successfully"));
	}

	@PatchMapping("/{id}/publish")
	public ResponseEntity<ApiResponse<Void>> publishEvent(@PathVariable Long id) {
		eventService.publishEvent(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Event published successfully"));
	}

	@PatchMapping("/{id}/cancel")
	public ResponseEntity<ApiResponse<Void>> cancelEvent(@PathVariable Long id) {
		eventService.cancelEvent(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Event cancelled successfully"));
	}
}

