package com.ashish.ticketing.modules.event.mapper;

import com.ashish.ticketing.modules.event.dto.request.CreateEventRequest;
import com.ashish.ticketing.modules.event.dto.request.UpdateEventRequest;
import com.ashish.ticketing.modules.event.dto.response.EventDetailsResponse;
import com.ashish.ticketing.modules.event.dto.response.EventResponse;
import com.ashish.ticketing.modules.event.entity.Event;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EventMapper {

    public Event toEntity(CreateEventRequest request) {
        if (request == null) {
            return null;
        }
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setCategory(request.getCategory());
        event.setCity(request.getCity());
        event.setVenue(request.getVenue());
        event.setStartTime(request.getStartTime());
        event.setEndTime(request.getEndTime());
        event.setBookingStartTime(request.getBookingStartTime());
        event.setBookingEndTime(request.getBookingEndTime());
        event.setTicketPrice(request.getTicketPrice());
        event.setTotalTickets(request.getTotalTickets());
        return event;
    }

    public void updateEntity(Event event, UpdateEventRequest request) {
        if (event == null || request == null) {
            return;
        }
        if (StringUtils.hasText(request.getTitle())) {
            event.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            event.setDescription(request.getDescription());
        }
        if (request.getCategory() != null) {
            event.setCategory(request.getCategory());
        }
        if (StringUtils.hasText(request.getCity())) {
            event.setCity(request.getCity());
        }
        if (StringUtils.hasText(request.getVenue())) {
            event.setVenue(request.getVenue());
        }
        if (request.getStartTime() != null) {
            event.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            event.setEndTime(request.getEndTime());
        }
        if (request.getBookingStartTime() != null) {
            event.setBookingStartTime(request.getBookingStartTime());
        }
        if (request.getBookingEndTime() != null) {
            event.setBookingEndTime(request.getBookingEndTime());
        }
        if (request.getTicketPrice() != null) {
            event.setTicketPrice(request.getTicketPrice());
        }
        if (request.getTotalTickets() != null) {
            event.setTotalTickets(request.getTotalTickets());
        }
    }

    public EventResponse toResponse(Event event) {
        if (event == null) {
            return null;
        }
        return new EventResponse(
            event.getId(),
            event.getTitle(),
            event.getCity(),
            event.getCategory(),
            event.getStartTime(),
            event.getTicketPrice(),
            event.getAvailableTickets()
        );
    }

    public EventDetailsResponse toDetailsResponse(Event event) {
        if (event == null) {
            return null;
        }
        return new EventDetailsResponse(
            event.getId(),
            event.getTitle(),
            event.getDescription(),
            event.getCategory(),
            event.getCity(),
            event.getVenue(),
            event.getBookingStartTime(),
            event.getBookingEndTime(),
            event.getTicketPrice(),
            event.getTotalTickets(),
            event.getAvailableTickets(),
            event.getStatus(),
            event.getStartTime(),
            event.getEndTime()
        );
    }
}

