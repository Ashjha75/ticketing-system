package com.ashish.ticketing.modules.booking.dto.response;

import java.math.BigDecimal;

public class BookingResponse {

    private String bookingNumber;
    private String status;
    private BigDecimal amount;

    public BookingResponse() {
    }

    public BookingResponse(String bookingNumber, String status, BigDecimal amount) {
        this.bookingNumber = bookingNumber;
        this.status = status;
        this.amount = amount;
    }

    public String getBookingNumber() {
        return bookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        this.bookingNumber = bookingNumber;
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
