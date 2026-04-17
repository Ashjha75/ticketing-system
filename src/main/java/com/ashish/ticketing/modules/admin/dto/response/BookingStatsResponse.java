package com.ashish.ticketing.modules.admin.dto.response;

public class BookingStatsResponse {

    private long totalBookings;
    private long successfulBookings;
    private long failedBookings;
    private long cancelledBookings;

    public BookingStatsResponse() {
    }

    public BookingStatsResponse(long totalBookings, long successfulBookings, long failedBookings, long cancelledBookings) {
        this.totalBookings = totalBookings;
        this.successfulBookings = successfulBookings;
        this.failedBookings = failedBookings;
        this.cancelledBookings = cancelledBookings;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public long getSuccessfulBookings() {
        return successfulBookings;
    }

    public void setSuccessfulBookings(long successfulBookings) {
        this.successfulBookings = successfulBookings;
    }

    public long getFailedBookings() {
        return failedBookings;
    }

    public void setFailedBookings(long failedBookings) {
        this.failedBookings = failedBookings;
    }

    public long getCancelledBookings() {
        return cancelledBookings;
    }

    public void setCancelledBookings(long cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }
}
