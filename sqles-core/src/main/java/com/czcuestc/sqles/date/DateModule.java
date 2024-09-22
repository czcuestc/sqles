package com.czcuestc.sqles.date;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class DateModule extends SimpleModule {
    public DateModule() {
        addSerializer(Time.class, new TimeSerializer());
        addSerializer(LocalTime.class, new LocalTimeSerializer());
        addSerializer(Date.class, new DatetimeSerializer());
        addSerializer(java.sql.Date.class, new DateSerializer());
        addSerializer(Timestamp.class, new TimestampSerializer());
        addSerializer(LocalDateTime.class, new LocalDatetimeSerializer());
        addSerializer(Instant.class, new InstantSerializer());

        addDeserializer(Time.class, new TimeDeserializer());
        addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        addDeserializer(Date.class, new DatetimeDeserializer());
        addDeserializer(java.sql.Date.class, new DateDeserializer());
        addDeserializer(Timestamp.class, new TimestampDeserializer());
        addDeserializer(LocalDateTime.class, new LocalDatetimeDeserializer());
        addDeserializer(Instant.class, new InstantDeserializer());
    }
}
