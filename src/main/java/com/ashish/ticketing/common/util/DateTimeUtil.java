package com.ashish.ticketing.common.util;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {
    private static final DateTimeFormatter ISO_INSTANT = DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC);

    private DateTimeUtil() {
    }

    public static Instant getCurrentTime() {
        return Instant.now();
    }

    public static boolean isBetween(Instant start, Instant end) {
        return isBetween(getCurrentTime(), start, end);
    }

    public static boolean isBetween(Instant target, Instant start, Instant end) {
        if (target == null || start == null || end == null) {
            return false;
        }
        return !target.isBefore(start) && !target.isAfter(end);
    }

    public static String formatDate(Instant instant) {
        return instant == null ? null : ISO_INSTANT.format(instant);
    }
}

