package com.netvalue.evehicleschargemeter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoChargingSessionFoundException extends RuntimeException {
    private final Long chargingSessionId;
}
