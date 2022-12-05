package com.netvalue.evehicleschargemeter.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoCustomerFoundException extends RuntimeException {
    private final String rfidNumber;
}
