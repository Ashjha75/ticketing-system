package com.ashish.ticketing.common.constants;

public final class RedisKeys {
    public static final String EVENT_DETAILS = "event:details:%s";
    public static final String EVENT_LIST = "event:list";
    public static final String BOOKING_RATE_LIMIT = "booking:rate_limit:%s";

    private RedisKeys() {
    }

    public static String eventDetailsKey(Long eventId) {
        return String.format(EVENT_DETAILS, eventId);
    }

    public static String bookingRateLimitKey(String userId) {
        return String.format(BOOKING_RATE_LIMIT, userId);
    }
}

