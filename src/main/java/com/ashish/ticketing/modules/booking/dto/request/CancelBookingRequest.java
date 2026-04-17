package com.ashish.ticketing.modules.booking.dto.request;

import jakarta.validation.constraints.NotNull;

public class CancelBookingRequest {

    @NotNull(message = "Booking id is required")
    private Long bookingId;

    public CancelBookingRequest() {
    }

    public CancelBookingRequest(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

}
