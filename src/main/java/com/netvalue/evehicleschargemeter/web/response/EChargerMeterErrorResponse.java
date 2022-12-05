package com.netvalue.evehicleschargemeter.web.response;

import com.netvalue.evehicleschargemeter.enums.ErrorType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class EChargerMeterErrorResponse {
    private ErrorType errorType;
    private String message;
    private Map<String, List<String>> errors = new HashMap<>();

    public EChargerMeterErrorResponse(ErrorType errorType, String message) {
        this.errorType = errorType;
        this.message = message;
    }
}
