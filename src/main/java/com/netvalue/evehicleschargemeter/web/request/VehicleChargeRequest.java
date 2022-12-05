package com.netvalue.evehicleschargemeter.web.request;

import lombok.Data;

@Data
public class VehicleChargeRequest {
    private String registrationPlate;
    private Integer connectorNumber;
}
