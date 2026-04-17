package com.ashish.ticketing.common.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class IdGenerator {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.BASIC_ISO_DATE;

    private IdGenerator() {
    }

    public static String generate(String prefix) {
        String safePrefix = prefix == null || prefix.isBlank() ? "ID" : prefix.trim().toUpperCase();
        String date = LocalDate.now().format(DATE_FORMAT);
        String random = randomAlphaNumeric(4);
        return safePrefix + "-" + date + "-" + random;
    }

    private static String randomAlphaNumeric(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(alphabet.length());
            builder.append(alphabet.charAt(index));
        }
        return builder.toString();
    }
}

