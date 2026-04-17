package com.ashish.ticketing.modules.event.dto.request;

import com.ashish.ticketing.modules.event.enums.EventCategory;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import java.time.Instant;

public class CreateEventRequest {

    private String name;
    private String description;
    private EventCategory category;
    private EventStatus status;
    private Instant startTime;
    private Instant endTime;
    private String venue;
    private Integer capacity;

    public CreateEventRequest() {
    }

    public CreateEventRequest(String name, String description, EventCategory category, EventStatus status, Instant startTime, Instant endTime, String venue, Integer capacity) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory category) {
        this.category = category;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}

