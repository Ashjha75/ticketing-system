package com.ashish.ticketing.modules.booking.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public class BookingDetailsResponse {

    private String bookingNumber;
    private Long eventId;
    private Long userId;
    private Integer quantity;
    private BigDecimal amount;
    private String status;
    private Instant createdAt;

    public BookingDetailsResponse() {
    }

    public BookingDetailsResponse(String bookingNumber, Long eventId, Long userId, Integer quantity, BigDecimal amount, String status, Instant createdAt) {
        this.bookingNumber = bookingNumber;
        this.eventId = eventId;
        this.userId = userId;
        this.quantity = quantity;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
