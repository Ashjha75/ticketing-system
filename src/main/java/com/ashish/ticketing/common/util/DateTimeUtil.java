package com.ashish.ticketing.common.util;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public final class DateTimeUtil {
    private static final DateTimeFormatter ISO_INSTANT = DateTimeFormatter.ISO_INSTANT;

    private DateTimeUtil() {
    }

    public static Instant nowUtc() {
        return Instant.now();
    }

    public static String formatIso(Instant instant) {
        return instant == null ? null : ISO_INSTANT.format(instant);
    }

    public static Instant parseIso(String value) {
        return value == null || value.isBlank() ? null : Instant.parse(value);
    }
}

