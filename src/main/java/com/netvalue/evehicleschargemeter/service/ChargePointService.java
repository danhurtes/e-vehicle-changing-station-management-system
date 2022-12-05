package com.netvalue.evehicleschargemeter.service;

import com.netvalue.evehicleschargemeter.dao.entity.ChargePoint;
import com.netvalue.evehicleschargemeter.dao.entity.Connector;
import com.netvalue.evehicleschargemeter.dao.repository.ChargePointRepository;
import com.netvalue.evehicleschargemeter.exception.NoChargePointFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChargePointService {
    private final ChargePointRepository chargePointRepository;

    public ChargePoint findByUniqueSerialNumber(String uniqueSerialNumber) {
        return chargePointRepository.findByUniqueSerialNumber(uniqueSerialNumber).orElseThrow(() -> {
            throw new NoChargePointFoundException(uniqueSerialNumber);
        });
    }

    public void save(ChargePoint chargePoint) {
        chargePointRepository.save(chargePoint);
    }
}
