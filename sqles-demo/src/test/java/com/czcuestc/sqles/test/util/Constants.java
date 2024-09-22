package com.czcuestc.sqles.test.util;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

/**
 * Copyright [2023-2024] [czcuestc]
 * All rights reserved
 */
public class Constants {
    public static Date DATE = new Date();

    public static LocalTime LOCAL_TIME = LocalTime.now();

    public static Time TIME = Time.valueOf(LOCAL_TIME);

    public static BigDecimal DECIMAL = new BigDecimal(1000);
}
