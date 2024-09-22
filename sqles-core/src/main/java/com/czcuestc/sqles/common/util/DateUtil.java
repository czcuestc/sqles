package com.czcuestc.sqles.common.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class DateUtil {
    public static String format(Timestamp date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }

    public static String format(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String formattedDate = simpleDateFormat.format(date);
        return formattedDate;
    }

    public static String format(LocalDateTime date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    public static String format(Instant date, String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        String formattedDate = date.atZone(ZoneOffset.UTC).format(formatter);
        return formattedDate;
    }

    public static long epoch(Time date) {
        return date.getTime();
    }

    public static long epoch(Date date) {
        return date.getTime();
    }

    public static long epoch(java.sql.Date date) {
        return date.getTime();
    }

    public static long epoch(Timestamp date) {
        return date.getTime();
    }

    public static long epoch(LocalDate date) {
        return date.toEpochDay();
    }

    public static long epoch(LocalTime date) {
        return date.toNanoOfDay();
    }

    public static long epoch(LocalDateTime date) {
        return date.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    public static long epoch(Instant date) {
        return date.toEpochMilli();
    }

    public static Time toTime(Long epoch) {
        return new Time(epoch);
    }

    public static java.sql.Date toDate(Long epoch) {
        return new java.sql.Date(epoch);
    }

    public static Date toDateTime(Long epoch) {
        return new Date(epoch);
    }

    public static Timestamp toTimestamp(Long epoch) {
        return new Timestamp(epoch);
    }

    public static LocalTime toLocalTime(Long epoch) {
        return LocalTime.ofNanoOfDay(epoch);
    }

    public static LocalDate toLocalDate(Long epoch) {
        return LocalDate.ofEpochDay(epoch);
    }

    public static LocalDateTime toLocalDateTime(Long epoch) {
        return toInstant(epoch).atZone(ZoneOffset.UTC).toLocalDateTime();
    }

    public static Instant toInstant(Long epoch) {
        return Instant.ofEpochMilli(epoch);
    }
}
