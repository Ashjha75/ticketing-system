package com.ashish.ticketing.modules.admin.dto.response;

import java.math.BigDecimal;

public class DashboardResponse {

    private long totalUsers;
    private long totalEvents;
    private long totalBookings;
    private BigDecimal totalRevenue;
    private long activeEvents;
    private long soldOutEvents;
    private long todayBookings;
    private BigDecimal todayRevenue;

    public DashboardResponse() {
    }

    public DashboardResponse(
            long totalUsers,
            long totalEvents,
            long totalBookings,
            BigDecimal totalRevenue,
            long activeEvents,
            long soldOutEvents,
            long todayBookings,
            BigDecimal todayRevenue
    ) {
        this.totalUsers = totalUsers;
        this.totalEvents = totalEvents;
        this.totalBookings = totalBookings;
        this.totalRevenue = totalRevenue;
        this.activeEvents = activeEvents;
        this.soldOutEvents = soldOutEvents;
        this.todayBookings = todayBookings;
        this.todayRevenue = todayRevenue;
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(long totalEvents) {
        this.totalEvents = totalEvents;
    }

    public long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public long getActiveEvents() {
        return activeEvents;
    }

    public void setActiveEvents(long activeEvents) {
        this.activeEvents = activeEvents;
    }

    public long getSoldOutEvents() {
        return soldOutEvents;
    }

    public void setSoldOutEvents(long soldOutEvents) {
        this.soldOutEvents = soldOutEvents;
    }

    public long getTodayBookings() {
        return todayBookings;
    }

    public void setTodayBookings(long todayBookings) {
        this.todayBookings = todayBookings;
    }

    public BigDecimal getTodayRevenue() {
        return todayRevenue;
    }

    public void setTodayRevenue(BigDecimal todayRevenue) {
        this.todayRevenue = todayRevenue;
    }
}
