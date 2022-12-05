package com.netvalue.evehicleschargemeter.utils;

import lombok.experimental.UtilityClass;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

@UtilityClass
public class CustomerUtils {
    private final String DATE_PATTERN_1 = "yyyy/MM/dd";
    private final String DATE_PATTERN_2 = "dd/MM/yyyy";

    public long getAvailableSeconds(Long leftMeterValue) {
        return leftMeterValue * 10;
    }

    public Long computeMeterValue(LocalDateTime startChargingTime, LocalDateTime endChargingSession) {
        long computedValue = Duration.between(startChargingTime, endChargingSession).getSeconds() / 10;
        return computedValue > 0 ? computedValue : 1;
    }

    public LocalDateTime getDateTimeFromString(String stringDate) {
        DateTimeFormatter parser = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ISO_LOCAL_DATE)
                .optionalStart()
                .appendLiteral(' ')
                .appendOptional(DateTimeFormatter.ISO_LOCAL_TIME)
                .optionalEnd()
                .appendOptional(DateTimeFormatter.ofPattern(DATE_PATTERN_1))
                .optionalStart()
                .appendLiteral(' ')
                .appendOptional(DateTimeFormatter.ISO_LOCAL_TIME)
                .optionalEnd()
                .appendOptional(DateTimeFormatter.ofPattern(DATE_PATTERN_2))
                .optionalStart()
                .appendLiteral(' ')
                .appendOptional(DateTimeFormatter.ISO_LOCAL_TIME)
                .optionalEnd()
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                .toFormatter();
        return LocalDateTime.parse(stringDate, parser);
    }

    public boolean isAvailableBalanceSpent(Long availableMeterValue, LocalDateTime startChargingTime) {
        return startChargingTime.plusSeconds(getAvailableSeconds(availableMeterValue)).isBefore(LocalDateTime.now());
    }
}
