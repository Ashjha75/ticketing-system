package com.ashish.ticketing.modules.booking.dto.response;

public class BookingSummaryResponse {

    private Long id;
    private String eventName;
    private Double totalPrice;
    private String status;

    public BookingSummaryResponse() {
    }

    public BookingSummaryResponse(Long id, String eventName, Double totalPrice, String status) {
        this.id = id;
        this.eventName = eventName;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
