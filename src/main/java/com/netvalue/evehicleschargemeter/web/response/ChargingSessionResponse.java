package com.netvalue.evehicleschargemeter.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ChargingSessionResponse {
    private Long chargingSessionId;
    private String customerName;
    private Long startAvailableMeterValue;
    private Long meterValueSpent;
    private LocalDateTime startChargingTime;
    private LocalDateTime endChargingTime;
    private String errorMessage;
}
