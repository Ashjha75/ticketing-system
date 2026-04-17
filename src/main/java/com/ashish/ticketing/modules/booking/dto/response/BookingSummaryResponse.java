package com.ashish.ticketing.modules.booking.dto.response;

import java.math.BigDecimal;

public class BookingSummaryResponse {

    private String bookingNumber;
    private Long eventId;
    private String status;
    private BigDecimal amount;

    public BookingSummaryResponse() {
    }

    public BookingSummaryResponse(String bookingNumber, Long eventId, String status, BigDecimal amount) {
        this.bookingNumber = bookingNumber;
        this.eventId = eventId;
        this.status = status;
        this.amount = amount;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
