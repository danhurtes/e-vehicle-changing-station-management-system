package com.netvalue.evehicleschargemeter.annotation.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.PARAMETER)
@Constraint(validatedBy = VehicleChargeRequestValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsVehicleChargeRequestValid {
    String message() default "The VehicleChargeRequest is invalid, check if connector or registration plate is correct";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
