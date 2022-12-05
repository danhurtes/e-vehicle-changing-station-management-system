package com.netvalue.evehicleschargemeter.web.response;

import lombok.Getter;

@Getter
public class EChargeMeterErrorResponse extends EChargeMeterResponse {
    private final String errorMsg;

    public EChargeMeterErrorResponse(String serviceResult, String errorMsg) {
        super(serviceResult);
        this.errorMsg = errorMsg;
    }
}
