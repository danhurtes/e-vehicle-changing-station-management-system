package com.netvalue.evehicleschargemeter.annotation.validation;

import com.netvalue.evehicleschargemeter.web.request.VehicleChargeRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class VehicleChargeRequestValidator implements ConstraintValidator<IsVehicleChargeRequestValid, VehicleChargeRequest> {
    @Value("${connector.valid-numbers}")
    private List<Integer> connectors;
    @Value("${registration-plate.format}")
    private String registrationPlateFormat;

    @Override
    public boolean isValid(VehicleChargeRequest vehicleChargeRequest, ConstraintValidatorContext context) {
        return connectors.contains(vehicleChargeRequest.getConnectorNumber())
                && StringUtils.isNotBlank(vehicleChargeRequest.getRegistrationPlate())
                && vehicleChargeRequest.getRegistrationPlate().matches(registrationPlateFormat);
    }
}
