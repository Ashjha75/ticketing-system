package com.ashish.ticketing.modules.event.dto.response;

import com.ashish.ticketing.modules.event.enums.EventCategory;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import java.time.Instant;

public class EventResponse {

    private Long id;
    private String name;
    private EventCategory category;
    private EventStatus status;
    private Instant startTime;
    private Instant endTime;

    public EventResponse() {
    }

    public EventResponse(Long id, String name, EventCategory category, EventStatus status, Instant startTime, Instant endTime) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.status = status;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}

