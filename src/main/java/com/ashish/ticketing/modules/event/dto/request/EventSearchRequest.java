package com.ashish.ticketing.modules.event.dto.request;

import com.ashish.ticketing.modules.event.enums.EventCategory;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import java.time.Instant;

public class EventSearchRequest {

    private String keyword;
    private EventCategory category;
    private EventStatus status;
    private Instant startTimeFrom;
    private Instant startTimeTo;
    private Integer page;
    private Integer size;

    public EventSearchRequest() {
    }

    public EventSearchRequest(String keyword, EventCategory category, EventStatus status, Instant startTimeFrom, Instant startTimeTo, Integer page, Integer size) {
        this.keyword = keyword;
        this.category = category;
        this.status = status;
        this.startTimeFrom = startTimeFrom;
        this.startTimeTo = startTimeTo;
        this.page = page;
        this.size = size;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public Instant getStartTimeFrom() {
        return startTimeFrom;
    }

    public void setStartTimeFrom(Instant startTimeFrom) {
        this.startTimeFrom = startTimeFrom;
    }

    public Instant getStartTimeTo() {
        return startTimeTo;
    }

    public void setStartTimeTo(Instant startTimeTo) {
        this.startTimeTo = startTimeTo;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}

