package com.czcuestc.sqles.date;

import com.czcuestc.sqles.common.util.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class LocalDatetimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            gen.writeNumber(DateUtil.epoch(value));
        }
    }
}
