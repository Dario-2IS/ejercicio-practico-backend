package com.demo.backend.infrastructure.utils;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public final class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String getCurrentDate() {
        return LocalDate.now().format(FORMATTER);
    }

    public static String getCurrentTimeWithOffset() {
        return OffsetTime.now(ZoneOffset.UTC).toString();
    }
}
