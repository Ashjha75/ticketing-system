package com.ashish.ticketing.modules.admin.dto.response;

public class DashboardResponse {

    private long totalUsers;
    private long totalEvents;
    private long totalBookings;
    private double totalRevenue;

    public DashboardResponse() {
    }

    public DashboardResponse(long totalUsers, long totalEvents, long totalBookings, double totalRevenue) {
        this.totalUsers = totalUsers;
        this.totalEvents = totalEvents;
        this.totalBookings = totalBookings;
        this.totalRevenue = totalRevenue;
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

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
