package com.ashish.ticketing.modules.admin.dto.response;

import java.math.BigDecimal;

public class BookingStatsResponse {

    private long totalBookings;
    private long confirmedBookings;
    private long cancelledBookings;
    private long failedBookings;
    private BigDecimal totalRevenue;

    public BookingStatsResponse() {
    }

    public BookingStatsResponse(
            long totalBookings,
            long confirmedBookings,
            long cancelledBookings,
            long failedBookings,
            BigDecimal totalRevenue
    ) {
        this.totalBookings = totalBookings;
        this.confirmedBookings = confirmedBookings;
        this.cancelledBookings = cancelledBookings;
        this.failedBookings = failedBookings;
        this.totalRevenue = totalRevenue;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public long getConfirmedBookings() {
        return confirmedBookings;
    }

    public void setConfirmedBookings(long confirmedBookings) {
        this.confirmedBookings = confirmedBookings;
    }

    public long getCancelledBookings() {
        return cancelledBookings;
    }

    public void setCancelledBookings(long cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }

    public long getFailedBookings() {
        return failedBookings;
    }

    public void setFailedBookings(long failedBookings) {
        this.failedBookings = failedBookings;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
