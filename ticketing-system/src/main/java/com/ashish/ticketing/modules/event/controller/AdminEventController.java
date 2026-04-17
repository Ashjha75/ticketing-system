package com.ashish.ticketing.modules.event.controller;

import com.ashish.ticketing.common.response.ApiResponse;
import com.ashish.ticketing.modules.event.dto.request.CreateEventRequest;
import com.ashish.ticketing.modules.event.dto.request.UpdateEventRequest;
import com.ashish.ticketing.modules.event.dto.response.EventDetailsResponse;
import com.ashish.ticketing.modules.event.entity.Event;
import com.ashish.ticketing.modules.event.mapper.EventMapper;
import com.ashish.ticketing.modules.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Admin Events", description = "Admin-only event management APIs")
@SecurityRequirement(name = "bearerAuth")
public class AdminEventController {

	private final EventService eventService;
	private final EventMapper eventMapper;

	public AdminEventController(EventService eventService, EventMapper eventMapper) {
		this.eventService = eventService;
		this.eventMapper = eventMapper;
	}

	@PostMapping
	@Operation(summary = "Create event", description = "Creates an event in DRAFT state")
	public ResponseEntity<ApiResponse<EventDetailsResponse>> createEvent(@Valid @RequestBody CreateEventRequest request) {
		Event created = eventService.createEvent(request);
		return ResponseEntity.ok(ApiResponse.success(eventMapper.toDetailsResponse(created), "Event created successfully"));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update event", description = "Partially updates event details with validation")
	public ResponseEntity<ApiResponse<EventDetailsResponse>> updateEvent(
			@PathVariable Long id,
			@Valid @RequestBody UpdateEventRequest request
	) {
		Event updated = eventService.updateEvent(id, request);
		return ResponseEntity.ok(ApiResponse.success(eventMapper.toDetailsResponse(updated), "Event updated successfully"));
	}

	@PatchMapping("/{id}/publish")
	@Operation(summary = "Publish event", description = "Publishes a valid event for user visibility and booking")
	public ResponseEntity<ApiResponse<Void>> publishEvent(@PathVariable Long id) {
		eventService.publishEvent(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Event published successfully"));
	}

	@PatchMapping("/{id}/cancel")
	@Operation(summary = "Cancel event", description = "Cancels an event and prevents further booking")
	public ResponseEntity<ApiResponse<Void>> cancelEvent(@PathVariable Long id) {
		eventService.cancelEvent(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Event cancelled successfully"));
	}
}

