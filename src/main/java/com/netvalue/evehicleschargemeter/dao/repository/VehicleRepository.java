package com.netvalue.evehicleschargemeter.dao.repository;

import com.netvalue.evehicleschargemeter.dao.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByRegistrationPlate(String registrationPlate);
}
