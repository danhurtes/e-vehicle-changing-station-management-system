package com.netvalue.evehicleschargemeter.handler;

import com.netvalue.evehicleschargemeter.enums.ErrorType;
import com.netvalue.evehicleschargemeter.exception.EmptyMeterValueBalanceException;
import com.netvalue.evehicleschargemeter.exception.NoChargePointFoundException;
import com.netvalue.evehicleschargemeter.exception.NoChargingSessionFoundException;
import com.netvalue.evehicleschargemeter.exception.NoConnectorFoundException;
import com.netvalue.evehicleschargemeter.exception.NoVehicleFoundException;
import com.netvalue.evehicleschargemeter.web.response.EChargerMeterErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;

@Slf4j
@ControllerAdvice
public class CustomerExceptionHandler {
    private final static String NO_VEHICLE_FOUND_ERROR_MESSAGE = "The vehicle with the registration plate - '%s', wasn't registered yet";
    private final static String NO_CHARGING_SESSION_FOUND_ERROR_MESSAGE = "No charging session with id - '%s' was found";
    private final static String NO_CONNECTOR_FOUND_ERROR_MESSAGE = "No connector with number - '%s' was found";
    private final static String UNEXPECTED_ERROR_MESSAGE = "Unexpected error has happened. Please contact support";
    private final static String ACCESS_DENIED = "You are not allowed to have access to this resource";
    private final static String EMPTY_METER_VALUE_BALANCE = "You don't have credits to charge your car. Top up your balance please";

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<EChargerMeterErrorResponse> runtimeExceptionHandler(RuntimeException e) {
        log.error("Unexpected error has happened");
        return ResponseEntity.internalServerError()
                .body(new EChargerMeterErrorResponse(ErrorType.UNEXPECTED_ERROR, UNEXPECTED_ERROR_MESSAGE));
    }

    @ExceptionHandler(NoVehicleFoundException.class)
    public ResponseEntity<EChargerMeterErrorResponse> handleVehicleNotFoundException(NoVehicleFoundException e) {
        String errorMsg = String.format(NO_VEHICLE_FOUND_ERROR_MESSAGE, e.getRegistrationPlate());
        log.error(errorMsg);
        return ResponseEntity
                .badRequest()
                .body(new EChargerMeterErrorResponse(ErrorType.NO_VEHICLE_FOUND, errorMsg));
    }

    @ExceptionHandler(NoChargingSessionFoundException.class)
    public ResponseEntity<EChargerMeterErrorResponse> handleChargingSessionNotFoundException(NoChargingSessionFoundException e) {
        String errorMsg = String.format(NO_CHARGING_SESSION_FOUND_ERROR_MESSAGE, e.getChargingSessionId());
        log.error(errorMsg);
        return ResponseEntity
                .badRequest()
                .body(new EChargerMeterErrorResponse(ErrorType.NO_CHARGING_SESSION_FOUND, errorMsg));
    }

    @ExceptionHandler(NoConnectorFoundException.class)
    public ResponseEntity<EChargerMeterErrorResponse> handleConnectorNotFoundException(NoConnectorFoundException e) {
        String errorMsg = String.format(NO_CONNECTOR_FOUND_ERROR_MESSAGE, e.getConnectorNumber());
        log.error(errorMsg);
        return ResponseEntity
                .badRequest()
                .body(new EChargerMeterErrorResponse(ErrorType.NO_CONNECTOR_FOUND, errorMsg));
    }

    @ExceptionHandler(NoChargePointFoundException.class)
    public ResponseEntity<EChargerMeterErrorResponse> handleChargePointNotFoundException(NoChargePointFoundException e) {
        String errorMsg = String.format(NO_CONNECTOR_FOUND_ERROR_MESSAGE, e.getUniqueSerialNumber());
        log.error(errorMsg);
        return ResponseEntity
                .badRequest()
                .body(new EChargerMeterErrorResponse(ErrorType.NO_CHARGE_POINT_FOUND, errorMsg));
    }

    @ExceptionHandler(EmptyMeterValueBalanceException.class)
    public ResponseEntity<EChargerMeterErrorResponse> handleEmptyMeterValueBalanceException(EmptyMeterValueBalanceException e) {
        log.error(EMPTY_METER_VALUE_BALANCE);
        return ResponseEntity
                .badRequest()
                .body(new EChargerMeterErrorResponse(ErrorType.EMPTY_BALANCE, EMPTY_METER_VALUE_BALANCE));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<EChargerMeterErrorResponse> handleValidationException(ValidationException e) {
        log.info("Validation exception: {}", e.getMessage());
        return ResponseEntity.unprocessableEntity()
                .body(new EChargerMeterErrorResponse(ErrorType.VALIDATION_ERROR, e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<EChargerMeterErrorResponse> accessDeniedHandler(AccessDeniedException e) {
        log.info("Access to this user is denied ", e.getCause());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new EChargerMeterErrorResponse(ErrorType.UNAUTHORIZED, ACCESS_DENIED));
    }
}
