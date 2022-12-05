package com.netvalue.evehicleschargemeter.web.controller;

import com.netvalue.evehicleschargemeter.annotation.EChargeMeterController;
import com.netvalue.evehicleschargemeter.annotation.validation.IsVehicleChargeRequestValid;
import com.netvalue.evehicleschargemeter.dao.entity.ChargePoint;
import com.netvalue.evehicleschargemeter.dao.entity.Customer;
import com.netvalue.evehicleschargemeter.dao.entity.RfidTag;
import com.netvalue.evehicleschargemeter.service.EChargeMeterService;
import com.netvalue.evehicleschargemeter.validation.EChargeMeterValidator;
import com.netvalue.evehicleschargemeter.web.request.VehicleChargeRequest;
import com.netvalue.evehicleschargemeter.web.response.ChargingSessionIdResponse;
import com.netvalue.evehicleschargemeter.web.response.EChargeMeterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@EChargeMeterController
@RequiredArgsConstructor
@Validated
@PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
public class CustomerController {
    private final EChargeMeterService eChargeMeterService;
    private final EChargeMeterValidator validator;

    @PostMapping(value = "/startChangingSession")
    public ResponseEntity<EChargeMeterResponse> startChargingSession(@RequestBody @IsVehicleChargeRequestValid
                                                           VehicleChargeRequest request) {
        RfidTag rfidTag = eChargeMeterService.getRfidTag(request.getRegistrationPlate());
        ChargePoint chargingPoint = eChargeMeterService.getChargingPoint(rfidTag, request.getConnectorNumber());
        if (validator.checkIfRfidTagIsValid(rfidTag, chargingPoint)) {
            Customer customer = eChargeMeterService.getCustomerLeftMeterValue(rfidTag.getNumber());
            long chargingSessionId = eChargeMeterService.addNewChargingSession(chargingPoint, customer);
            return ResponseEntity.ok(new ChargingSessionIdResponse("New Charging session successfully started",
                    chargingSessionId));
        }
        return ResponseEntity.badRequest()
                .body(new EChargeMeterResponse("Your rfidTag wasn't successfully validated. Please contact support"));
    }

    @PostMapping(value = "/endChangingSession/{changingSessionId}")
    public ResponseEntity<EChargeMeterResponse> endChargingSession(@PathVariable Long changingSessionId) {
        return eChargeMeterService.endChargingSession(changingSessionId);
    }
}
