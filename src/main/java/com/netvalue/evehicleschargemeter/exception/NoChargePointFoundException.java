package com.netvalue.evehicleschargemeter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoChargePointFoundException extends RuntimeException {
    private final String uniqueSerialNumber;
}
