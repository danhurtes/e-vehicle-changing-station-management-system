package com.netvalue.evehicleschargemeter.service;

import com.netvalue.evehicleschargemeter.dao.entity.RfidTag;
import com.netvalue.evehicleschargemeter.dao.repository.VehicleRepository;
import com.netvalue.evehicleschargemeter.exception.NoVehicleFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public RfidTag findRfidTagByRegistrationPlate(String registrationPlate) {
        return vehicleRepository.findByRegistrationPlate(registrationPlate).orElseThrow(() -> {
            throw new NoVehicleFoundException(registrationPlate);
        }).getRfidTag();
    }
}
