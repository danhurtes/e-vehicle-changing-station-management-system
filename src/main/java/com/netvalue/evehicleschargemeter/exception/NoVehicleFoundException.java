package com.netvalue.evehicleschargemeter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoVehicleFoundException extends RuntimeException {
    private final String registrationPlate;
}
