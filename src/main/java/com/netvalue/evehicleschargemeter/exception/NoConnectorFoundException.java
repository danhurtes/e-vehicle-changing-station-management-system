package com.netvalue.evehicleschargemeter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoConnectorFoundException extends RuntimeException {
    private final Integer connectorNumber;
}
