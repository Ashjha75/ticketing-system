package com.ashish.ticketing.modules.event.dto.response;

import com.ashish.ticketing.modules.event.enums.EventCategory;
import com.ashish.ticketing.modules.event.enums.EventStatus;
import java.time.Instant;
import java.math.BigDecimal;

public class EventDetailsResponse {

    private Long id;
    private String title;
    private String description;
    private EventCategory category;
    private String city;
    private String venue;
    private Instant bookingStartTime;
    private Instant bookingEndTime;
    private BigDecimal ticketPrice;
    private Integer totalTickets;
    private Integer availableTickets;
    private EventStatus status;
    private Instant startTime;
    private Instant endTime;

    public EventDetailsResponse() {
    }

    public EventDetailsResponse(Long id, String title, String description, EventCategory category, String city,
                                String venue, Instant bookingStartTime, Instant bookingEndTime,
                                BigDecimal ticketPrice, Integer totalTickets, Integer availableTickets,
                                EventStatus status, Instant startTime, Instant endTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.city = city;
        this.venue = venue;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.ticketPrice = ticketPrice;
        this.totalTickets = totalTickets;
        this.availableTickets = availableTickets;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Instant getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(Instant bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public Instant getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(Instant bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(Integer availableTickets) {
        this.availableTickets = availableTickets;
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

