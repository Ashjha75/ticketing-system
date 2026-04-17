package com.ashish.ticketing.modules.event.dto.request;

import com.ashish.ticketing.modules.event.enums.EventCategory;
import java.time.Instant;

public class EventSearchRequest {

    private String city;
    private String keyword;
    private EventCategory category;
    private Instant startDate;
    private Instant endDate;
    private Integer page;
    private Integer size;
    private String sort;

    public EventSearchRequest() {
    }

    public EventSearchRequest(String city, String keyword, EventCategory category, Instant startDate, Instant endDate,
                              Integer page, Integer size, String sort) {
        this.city = city;
        this.keyword = keyword;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}

