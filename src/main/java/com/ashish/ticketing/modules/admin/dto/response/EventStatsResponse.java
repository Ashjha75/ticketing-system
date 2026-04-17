package com.ashish.ticketing.modules.admin.dto.response;

import java.math.BigDecimal;

public class EventStatsResponse {

    private Long eventId;
    private String eventTitle;
    private int totalTickets;
    private int availableTickets;
    private int soldTickets;
    private BigDecimal totalRevenue;
    private long bookingCount;

    public EventStatsResponse() {
    }

    public EventStatsResponse(
            Long eventId,
            String eventTitle,
            int totalTickets,
            int availableTickets,
            int soldTickets,
            BigDecimal totalRevenue,
            long bookingCount
    ) {
        this.eventId = eventId;
        this.eventTitle = eventTitle;
        this.totalTickets = totalTickets;
        this.availableTickets = availableTickets;
        this.soldTickets = soldTickets;
        this.totalRevenue = totalRevenue;
        this.bookingCount = bookingCount;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    public int getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(int soldTickets) {
        this.soldTickets = soldTickets;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getBookingCount() {
        return bookingCount;
    }

    public void setBookingCount(long bookingCount) {
        this.bookingCount = bookingCount;
    }
}
