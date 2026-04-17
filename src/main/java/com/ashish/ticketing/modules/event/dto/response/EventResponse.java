package com.ashish.ticketing.modules.event.dto.response;

import com.ashish.ticketing.modules.event.enums.EventCategory;
import java.time.Instant;
import java.math.BigDecimal;

public class EventResponse {

    private Long id;
    private String title;
    private String city;
    private EventCategory category;
    private Instant startTime;
    private BigDecimal ticketPrice;
    private Integer availableTickets;

    public EventResponse() {
    }

    public EventResponse(Long id, String title, String city, EventCategory category, Instant startTime,
                         BigDecimal ticketPrice, Integer availableTickets) {
        this.id = id;
        this.title = title;
        this.city = city;
        this.category = category;
        this.startTime = startTime;
        this.ticketPrice = ticketPrice;
        this.availableTickets = availableTickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public EventCategory getCategory() {
        return category;
    }

    public void setCategory(EventCategory category) {
        this.category = category;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(Integer availableTickets) {
        this.availableTickets = availableTickets;
    }
}

