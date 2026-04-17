package com.ashish.ticketing.modules.event.mapper;

import com.ashish.ticketing.modules.event.dto.request.CreateEventRequest;
import com.ashish.ticketing.modules.event.dto.request.UpdateEventRequest;
import com.ashish.ticketing.modules.event.dto.response.EventDetailsResponse;
import com.ashish.ticketing.modules.event.dto.response.EventResponse;
import com.ashish.ticketing.modules.event.entity.Event;

public class EventMapper {

    public Event toEntity(CreateEventRequest request) {
        if (request == null) {
            return null;
        }
        Event event = new Event();
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setCategory(request.getCategory());
        event.setStatus(request.getStatus());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setVenue(request.getVenue());
        event.setCapacity(request.getCapacity());
        return event;
    }

    public void updateEntity(Event event, UpdateEventRequest request) {
        if (event == null || request == null) {
            return;
        }
        if (request.getName() != null) {
            event.setName(request.getName());
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getCategory() != null) {
            event.setCategory(request.getCategory());
        }
        if (request.getStatus() != null) {
            event.setStatus(request.getStatus());
        }
        if (request.getStartTime() != null) {
            event.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            event.setEndTime(request.getEndTime());
        }
        if (request.getVenue() != null) {
            event.setVenue(request.getVenue());
        }
        if (request.getCapacity() != null) {
            event.setCapacity(request.getCapacity());
        }
    }

    public EventResponse toResponse(Event event) {
        if (event == null) {
            return null;
        }
        return new EventResponse(
            event.getId(),
            event.getName(),
            event.getCategory(),
            event.getStatus(),
            event.getStartTime(),
            event.getEndTime()
        );
    }

    public EventDetailsResponse toDetailsResponse(Event event) {
        if (event == null) {
            return null;
        }
        return new EventDetailsResponse(
            event.getId(),
            event.getName(),
            event.getDescription(),
            event.getCategory(),
            event.getStatus(),
            event.getStartTime(),
            event.getEndTime(),
            event.getVenue(),
            event.getCapacity(),
            event.getCreatedAt(),
            event.getUpdatedAt()
        );
    }
}

