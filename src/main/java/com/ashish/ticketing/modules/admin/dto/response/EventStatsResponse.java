package com.ashish.ticketing.modules.admin.dto.response;

public class EventStatsResponse {

    private long totalEvents;
    private long activeEvents;
    private long completedEvents;

    public EventStatsResponse() {
    }

    public EventStatsResponse(long totalEvents, long activeEvents, long completedEvents) {
        this.totalEvents = totalEvents;
        this.activeEvents = activeEvents;
        this.completedEvents = completedEvents;
    }

    public long getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(long totalEvents) {
        this.totalEvents = totalEvents;
    }

    public long getActiveEvents() {
        return activeEvents;
    }

    public void setActiveEvents(long activeEvents) {
        this.activeEvents = activeEvents;
    }

    public long getCompletedEvents() {
        return completedEvents;
    }

    public void setCompletedEvents(long completedEvents) {
        this.completedEvents = completedEvents;
    }
}
