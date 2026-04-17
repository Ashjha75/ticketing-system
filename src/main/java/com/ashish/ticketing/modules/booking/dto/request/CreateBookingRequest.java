package com.ashish.ticketing.modules.booking.dto.request;

public class CreateBookingRequest {

    private Long eventId;
    private Integer quantity;

    public CreateBookingRequest() {
    }

    public CreateBookingRequest(Long eventId, Integer quantity) {
        this.eventId = eventId;
        this.quantity = quantity;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
