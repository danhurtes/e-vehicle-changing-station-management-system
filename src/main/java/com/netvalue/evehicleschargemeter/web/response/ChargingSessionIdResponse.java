package com.netvalue.evehicleschargemeter.web.response;

import lombok.Getter;

@Getter
public class ChargingSessionIdResponse extends EChargeMeterResponse {
    private final Long chargingSessionId;

    public ChargingSessionIdResponse(String serviceResult, Long chargingSessionId) {
        super(serviceResult);
        this.chargingSessionId = chargingSessionId;
    }
}
