package com.ashish.ticketing.modules.event.dto.request;

import com.ashish.ticketing.modules.event.enums.EventCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import java.math.BigDecimal;

public class CreateEventRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private EventCategory category;

    @NotBlank
    private String city;

    @NotBlank
    private String venue;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    @NotNull
    private Instant bookingStartTime;

    @NotNull
    private Instant bookingEndTime;

    @NotNull
    @DecimalMin(value = "0.01")
    private BigDecimal ticketPrice;

    @NotNull
    @Positive
    private Integer totalTickets;

    public CreateEventRequest() {
    }

    public CreateEventRequest(String title, String description, EventCategory category, String city, String venue,
                              Instant startTime, Instant endTime, Instant bookingStartTime, Instant bookingEndTime,
                              BigDecimal ticketPrice, Integer totalTickets) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.city = city;
        this.venue = venue;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bookingStartTime = bookingStartTime;
        this.bookingEndTime = bookingEndTime;
        this.ticketPrice = ticketPrice;
        this.totalTickets = totalTickets;
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

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }
}

