package com.netvalue.evehicleschargemeter.validation;

import com.netvalue.evehicleschargemeter.config.properties.RfidTagValidatedList;
import com.netvalue.evehicleschargemeter.dao.entity.ChargePoint;
import com.netvalue.evehicleschargemeter.dao.entity.RfidTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EChargeMeterValidator {
    private final RfidTagValidatedList rfidTagValidatedList;

    public boolean checkIfRfidTagIsValid(RfidTag rfidTag, ChargePoint chargePoint) {
        var rfidTagNumbers = rfidTagValidatedList.getChargePoints().get(chargePoint.getName());
        return rfidTagNumbers.contains(rfidTag.getNumber());
    }
}
